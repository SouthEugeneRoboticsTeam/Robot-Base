package org.sert2521.gamename

import edu.wpi.first.wpilibj.Preferences

/** Checks if a log is loggable with the provided log level from SmartDashboard. */
private fun isLoggable(level: LogLevel) =
        level >= when (Preferences.getInstance().getString("log_level", "info")) {
            "verbose" -> LogLevel.VERBOSE
            "debug" -> LogLevel.DEBUG
            "warn" -> LogLevel.WARN
            "error" -> LogLevel.ERROR
            else -> LogLevel.INFO
        }

enum class LogLevel { VERBOSE, DEBUG, INFO, WARN, ERROR }

private val autoTag: String get() {
    Thread.currentThread().stackTrace
            .filter { it.fileName != "Thread.java" && it.fileName != "Log.kt" }
            .forEach { return it.fileName.split(".")[0] }
    log(LogLevel.ERROR, "Log", "Couldn't find tag")
    return "CRITICAL LOG FAILURE"
}

fun verbose(message: Any) = log(LogLevel.VERBOSE, message = message)
fun debug(message: Any) = log(LogLevel.DEBUG, message = message)
fun info(message: Any) = log(LogLevel.INFO, message = message)
fun warn(message: Any) = log(LogLevel.WARN, message = message)
fun error(message: Any) = log(LogLevel.ERROR, message = message)

private fun log(level: LogLevel, tag: String = autoTag, message: Any) {
    if (isLoggable(level)) println("$level/$tag: $message")
}
