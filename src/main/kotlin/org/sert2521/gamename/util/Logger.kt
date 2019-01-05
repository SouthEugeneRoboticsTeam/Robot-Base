package org.sert2521.gamename.util

import badlog.lib.BadLog
import badlog.lib.DataInferMode
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.RobotController
import org.team2471.frc.lib.framework.Subsystem
import org.team2471.frc.lib.util.Environment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.function.Supplier

private val pathPrefix = if (Environment.isReal) "/home/lvuser/logs" else "."
val logger = BadLog.init("$pathPrefix/${System.currentTimeMillis()}.bag")!!

class Logger {
    private val subsystemName: String

    constructor(name: String) {
        subsystemName = name
    }

    constructor(subsystem: Subsystem) {
        subsystemName = subsystem.name
    }

    /**
     * Creates a named topic that logs a Double. Non-Double Numbers returned from the body will be
     * converted to Double before being logged.
     *
     * @param name name of the topic
     * @param unit unit to assign values in this topic
     * @param body the function to be called to return the logged data
     * @see BadLog.createTopicStr
     */
    fun addNumberTopic(name: String, unit: String = BadLog.UNITLESS, body: () -> Number) =
        BadLog.createTopic("$subsystemName/$name", unit, Supplier { body().toDouble() })

    /**
     * Creates a named topic that logs a Double. Non-Double Numbers returned from the body will be
     * converted to Double before being logged.
     *
     * @param name name of the topic
     * @param unit unit to assign values in this topic
     * @param body the function to be called to return the logged data
     * @param attrs array of topic attributes
     * @see BadLog.createTopicStr
     */
    fun addNumberTopic(
        name: String,
        unit: String = BadLog.UNITLESS,
        body: () -> Number,
        vararg attrs: String
    ) = BadLog.createTopic("$subsystemName/$name", unit, Supplier { body().toDouble() }, *attrs)

    /**
     * Creates a named topic that logs a String. Non-String values returned from the body will be
     * converted to String before being logged.
     *
     * @param name name of the topic
     * @param unit unit to assign values in this topic
     * @param body the function to be called to return the logged data
     * @see BadLog.createTopicStr
     */
    fun addTopic(name: String, unit: String = BadLog.UNITLESS, body: () -> Any) =
        BadLog.createTopicStr("$subsystemName/$name", unit, Supplier { body().toString() })

    /**
     * Creates a named topic that logs a String. Non-String values returned from the body will be
     * converted to String before being logged.
     *
     * @param name name of the topic
     * @param unit unit to assign values in this topic
     * @param body the function to be called to return the logged data
     * @param attrs array of topic attributes
     * @see BadLog.createTopicStr
     */
    fun addTopic(
        name: String,
        unit: String = BadLog.UNITLESS,
        body: () -> Any,
        vararg attrs: String
    ) = BadLog.createTopicStr(
        "$subsystemName/$name",
        unit,
        Supplier { body().toString() },
        *attrs
    )

    /**
     * Creates a named value with a single value.
     *
     * @param name name of the value
     * @param value content to add
     * @see BadLog.createValue
     */
    fun addValue(name: String, value: Any) =
        BadLog.createValue("$subsystemName/$name", value.toString())

    /**
     * Creates a subscriber with the given name.
     *
     * @param name name of the topic
     * @param unit unit to assign values in this topic
     * @param inferMode method to use if data has not been published
     * @param attrs array of topic attributes
     * @see BadLog.createTopicSubscriber
     * @see publish
     */
    fun addSubscriber(
        name: String,
        unit: String = BadLog.UNITLESS,
        inferMode: DataInferMode = DataInferMode.DEFAULT,
        vararg attrs: String
    ) = BadLog.createTopicSubscriber("$subsystemName/$name", unit, inferMode, *attrs)

    /**
     * Publishes a value to the subscriber with the given name.
     *
     * @param name name of the topic
     * @param value new value to assign the topic
     * @see BadLog.publish
     * @see addSubscriber
     */
    fun publish(name: String, value: Any) =
        BadLog.publish("$subsystemName/$name", value.toString())
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

    SystemLogger.addNumberTopic("Battery Voltage", "V") { RobotController.getBatteryVoltage() }
    SystemLogger.addTopic("Browned Out", "bool") {
        if (RobotController.isBrownedOut()) "1" else "0"
    }
    SystemLogger.addTopic("FPGA Active", "bool") {
        if (RobotController.isSysActive()) "1" else "0"
    }

    BadLog.createTopicSubscriber("Time", "s", DataInferMode.DEFAULT, "hide", "delta", "xaxis")

    logger.finishInitialization()
}

fun logBuildInfo() {
    println("\n-------------------- BUILD INFO --------------------")

    "branch.txt".asResource {
        println("Branch: $it")
        GlobalTelemetry.put("Branch", it)
        BadLog.createValue("Branch", it)
    }

    "commit.txt".asResource {
        println("Commit: $it")
        GlobalTelemetry.put("Commit", it)
        BadLog.createValue("Commit", it)
    }

    "changes.txt".asResource {
        println("Changes: [$it]")
        GlobalTelemetry.put("Changes", it)
        BadLog.createValue("Changes", it)
    }

    "buildtime.txt".asResource {
        println("Build Time: $it")
        GlobalTelemetry.put("Build Time", it)
        BadLog.createValue("Build Time", it)
    }

    println("----------------------------------------------------\n")
}

fun String.asResource(body: (String) -> Unit) {
    val content = this.javaClass::class.java.getResource("/$this")?.readText()
    body(content ?: "")
}
