package me.adzuki.activitylogger.core

import java.text.SimpleDateFormat

abstract class Logger {
    abstract fun write()

    protected val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    protected val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    protected val String.csvEscaped get() =
        if (contains(',') || contains('"') || contains('\n'))
            "\"${replace("\"", "\"\"")}\""
        else this
}
