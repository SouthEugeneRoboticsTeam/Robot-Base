package org.sert2521.gamename.autonomous

import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.sert2521.gamename.autonomous.AutoMode.CROSS_BASELINE
import org.sert2521.gamename.autonomous.AutoMode.Constraint
import org.sert2521.gamename.autonomous.AutoMode.Objective
import org.sert2521.gamename.autonomous.AutoMode.Start
import org.sert2521.gamename.util.Logger
import org.sertain.util.SendableChooser
import org.team2471.frc.lib.motion_profiling.Autonomi
import org.team2471.frc.lib.util.measureTimeFPGA
import java.io.File

internal lateinit var autonomi: Autonomi

enum class AutoMode {
    CROSS_BASELINE;

    enum class Start {
        LEFT, MIDDLE, RIGHT
    }

    enum class Objective {
        BASELINE,
    }

    enum class Constraint {
        NONE, NO_FIELD_TRAVERSE, NO_FAR_LANE
    }

    companion object {
        val start get() = startChooser.selected ?: Start.MIDDLE
        val objective get() = objectiveChooser.selected ?: Objective.BASELINE
        val constraint get() = constraintChooser.selected ?: Constraint.NONE

        val startChooser = SendableChooser(
                "Middle" to Start.MIDDLE,
                "Left" to Start.LEFT,
                "Right" to Start.RIGHT
        )

        val objectiveChooser = SendableChooser(
                "Baseline" to Objective.BASELINE
        )

        val constraintChooser = SendableChooser(
                "None" to Constraint.NONE,
                "No Traverse" to Constraint.NO_FIELD_TRAVERSE,
                "No Far Lane" to Constraint.NO_FAR_LANE
        )
    }
}

object AutoChooser {
    private val cacheFile = File("/home/lvuser/autonomi.json")
    private val logger = Logger("Autonomous")

    init {
        SmartDashboard.putData("Auto Start Position", AutoMode.startChooser)
        SmartDashboard.putData("Auto Objective", AutoMode.objectiveChooser)
        SmartDashboard.putData("Auto Constraint", AutoMode.constraintChooser)

        logger.addSubscriber("Auto Start Position")
        logger.addSubscriber("Auto Objective")
        logger.addSubscriber("Auto Constraint")
        logger.addSubscriber("Calculated Auto Mode")

        try {
            autonomi = Autonomi.fromJsonString(cacheFile.readText())
            println("Autonomi cache loaded.")
        } catch (_: Exception) {
            DriverStation.reportError("Autonomi cache could not be found", false)
            autonomi = Autonomi()
        }

        val handler = { event: EntryNotification ->
            val json = event.value.string

            println("Received new autonomi JSON")

            if (!json.isEmpty()) {
                val t = measureTimeFPGA {
                    autonomi = Autonomi.fromJsonString(json)
                }

                println("Loaded autonomi in $t seconds")

                cacheFile.writeText(json)
                println("New autonomi written to cache")
            } else {
                autonomi = Autonomi()
                DriverStation.reportWarning("Empty autonomi received from NetworkTables", false)
            }
        }

        val flags = EntryListenerFlags.kImmediate or
                EntryListenerFlags.kNew or
                EntryListenerFlags.kUpdate

        NetworkTableInstance.getDefault()
                .getTable("PathVisualizer")
                .getEntry("Autonomi")
                .addListener(handler, flags)
    }

    private fun calculateAuto() = when (AutoMode.start) {
        Start.LEFT -> if (AutoMode.constraint == Constraint.NO_FAR_LANE) {
            AutoMode.CROSS_BASELINE
        } else {
            AutoMode.CROSS_BASELINE
        }
        Start.MIDDLE -> if (AutoMode.objective == Objective.BASELINE) {
            AutoMode.CROSS_BASELINE
        } else {
            AutoMode.CROSS_BASELINE
        }
        Start.RIGHT -> if (AutoMode.constraint == Constraint.NONE) {
            AutoMode.CROSS_BASELINE
        } else {
            CROSS_BASELINE
        }
    }

    suspend fun runAuto() {
        val auto = calculateAuto()

        logger.publish("Calculated Auto Mode", auto.name)

        when (auto) {
            CROSS_BASELINE -> testStraightAuto()
        }
    }
}
