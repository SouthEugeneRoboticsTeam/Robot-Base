package org.sert2521.gamename.util

import badlog.lib.BadLog
import badlog.lib.DataInferMode
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.RobotController
import org.team2471.frc.lib.framework.Subsystem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.function.Supplier

private val logger = BadLog.init("/home/lvuser/logs/${System.currentTimeMillis()}.bag")!!

class Logger {
    private val subsystemName: String

    constructor(name: String) {
        subsystemName = name
    }

    constructor(subsystem: Subsystem) {
        subsystemName = subsystem.name
    }

    fun addNumber(name: String, unit: String = BadLog.UNITLESS, body: () -> Number) =
        BadLog.createTopic("$subsystemName/$name", unit, Supplier { body().toDouble() })

    fun addString(name: String, unit: String = BadLog.UNITLESS, body: () -> String) =
            BadLog.createTopicStr("$subsystemName/$name", unit, Supplier { body() })

    fun addValue(name: String, value: String) = BadLog.createValue("$subsystemName/$name", value)
}

val SystemLogger = Logger("System")

fun log() {
    BadLog.publish("Time", System.nanoTime().toDouble())

    logger.updateTopics()
    logger.log()
}

fun initLogs() {
    val dateFormat = SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US)

    BadLog.createValue("Start Time", dateFormat.format(Date()))
    BadLog.createValue("Event Name", DriverStation.getInstance().eventName)
    BadLog.createValue("Match Type", DriverStation.getInstance().matchType.toString())
    BadLog.createValue("Match Number", DriverStation.getInstance().matchNumber.toString())
    BadLog.createValue("Alliance", DriverStation.getInstance().alliance.toString())
    BadLog.createValue("Location", DriverStation.getInstance().location.toString())

    BadLog.createTopic("Match Time", "s", Supplier { DriverStation.getInstance().matchTime })

    SystemLogger.addNumber("Battery Voltage", "V") { RobotController.getBatteryVoltage() }
    SystemLogger.addString("Browned Out", "bool") {
        if (RobotController.isBrownedOut()) "1" else "0"
    }
    SystemLogger.addString("FPGA Active", "bool") {
        if (RobotController.isSysActive()) "1" else "0"
    }

    BadLog.createTopicSubscriber("Time", "s", DataInferMode.DEFAULT, "hide", "delta", "xaxis")

    logger.finishInitialization()
}
