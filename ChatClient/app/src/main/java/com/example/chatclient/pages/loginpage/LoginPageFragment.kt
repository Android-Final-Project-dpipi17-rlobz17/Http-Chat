package com.example.chatclient.pages.loginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatclient.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class LoginPageFragment : Fragment(), LoginPageContract.View {

    private lateinit var mContext: Context
    private lateinit var presenter : LoginPageContract.Presenter
    private lateinit var profile_picture_imageView: ImageView
    private lateinit var nickname_textView : EditText
    private lateinit var about_textView : EditText
    private lateinit var login_button : Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment_layout, container, false)
        initFields(view)
        return view
    }

    private fun initFields(view: View){

        activity?.applicationContext?.let {
            presenter = LoginPagePresenterImpl(this, it)
        }
        profile_picture_imageView = view.findViewById(R.id.profile_picture_image_view)
        nickname_textView = view.findViewById(R.id.nickname_text_view)
        about_textView = view.findViewById(R.id.about_text_view)
        login_button = view.findViewById(R.id.login_button)


        profile_picture_imageView.setOnClickListener {
            openGallery()
        }

        login_button.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val nickname = nickname_textView.text.toString()
        val about = about_textView.text.toString()
        val profilePicture = getBase64ForPicture()

        Log.d("login_page_debug", "getBase64ForPicture got photo into -> $profilePicture")

        /* TODO[RL] this is method to convert base64 to Image (Use it wherever You want)
        val imageBytes = Base64.decode(profilePicture, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        profile_picture_imageView.setImageBitmap(decodedImage)
         */

        if(checkTextViews(nickname,about)) {
            changeComponentsState(false)

            GlobalScope.launch {
                presenter.checkLogin(nickname, about, profilePicture)
            }
        }else{
            nickname_textView.text.clear()
            about_textView.text.clear()
        }
    }

    override fun onLoginSuccess() {
        (mContext as Activity).runOnUiThread {
            findNavController().navigate(R.id.action_loginPageFragment_to_historyPageFragment)
        }
    }

    override fun onLoginFailure() {
        (mContext as Activity).runOnUiThread {
            nickname_textView.text.clear()
            about_textView.text.clear()
            changeComponentsState(true)

            Toast.makeText(mContext, getString(R.string.login_failure), Toast.LENGTH_SHORT).let {
                it.setGravity(Gravity.CENTER, 0, 0)
                it.show()
            }
        }
    }

    private fun changeComponentsState(enabled: Boolean){
        nickname_textView.isEnabled = enabled
        about_textView.isEnabled = enabled
        login_button.isEnabled = enabled
        login_button.isClickable = enabled

        if(enabled){
            login_button.text = getString(R.string.login_button_start)
        }else{
            login_button.text = getString(R.string.login_button_wait)
        }
    }

    private fun checkTextViews(nickname:String, about:String) : Boolean{
        if(nickname.isEmpty()){
            Toast.makeText(mContext, getString(R.string.login_nickname_empty), Toast.LENGTH_SHORT).show()
            return false
        }

        if(about.isEmpty()){
            Toast.makeText(mContext, getString(R.string.login_about_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun openGallery(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            Log.d("login_page_debug", "activity result got RESULT_OK and requestCode 100, its data is " + data!!.data.toString())

            profile_picture_imageView.setImageURI(data.data)
            if(profile_picture_imageView.drawable == null){
                profile_picture_imageView.setImageResource(R.drawable.login_default_avatar)
                Toast.makeText(mContext, getString(R.string.login_profile_picture_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBase64ForPicture() : String{

        val byteArrayOutStream = ByteArrayOutputStream()
        val bitmap = (profile_picture_imageView.drawable as BitmapDrawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutStream)
        val imageBytes: ByteArray = byteArrayOutStream.toByteArray()

        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

}