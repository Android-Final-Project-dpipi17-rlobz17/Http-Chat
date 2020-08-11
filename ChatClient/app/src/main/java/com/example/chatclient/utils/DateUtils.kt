package com.example.chatclient.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {
        fun formatDate(date: Date): String {
            if (date.addDays(1).before(Date())) {
                return date.formatToViewDateDefaults()
            }

            var differenceInTime = Date().time - date.time

            var differenceInHour = (differenceInTime / (1000 * 60 * 60)) % 24;
            if (differenceInHour > 0) {
                return "${differenceInHour}h"
            }

            var differenceInMinutes = (differenceInTime / (1000 * 60)) % 60;
            if (differenceInMinutes > 0) {
                return "${differenceInMinutes}min"
            }

            var differenceInSeconds = (differenceInTime / 1000) % 60;
            return "${differenceInSeconds}s"
        }


        /**
         * Pattern: dd/mm/yyyy
         */
        fun Date.formatToViewDateDefaults(): String{
            val sdf= SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
            return sdf.format(this)
        }

        /**
         * Add field date to current date
         */
        fun Date.add(field: Int, amount: Int): Date {
            Calendar.getInstance().apply {
                time = this@add
                add(field, amount)
                return time
            }
        }

        fun Date.addDays(days: Int): Date{
            return add(Calendar.DAY_OF_MONTH, days)
        }

        /**
         * Pattern: yyyy-MM-dd HH:mm:ss
         */
        fun Date.formatToServerDateTimeDefaults(): String{
            val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(this)
        }

    }

}