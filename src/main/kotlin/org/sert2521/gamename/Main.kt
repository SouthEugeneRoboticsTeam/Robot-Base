package org.sert2521.gamename

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import kotlinx.coroutines.launch
import org.sert2521.gamename.autonomous.AutoChooser
import org.sert2521.gamename.drivetrain.Drivetrain
import org.sert2521.gamename.util.TelemetryScope
import org.sert2521.gamename.util.Vision
import org.sert2521.gamename.util.initControls
import org.sert2521.gamename.util.initLogs
import org.sert2521.gamename.util.initPreferences
import org.sert2521.gamename.util.log
import org.sert2521.gamename.util.logBuildInfo
import org.sert2521.gamename.util.logger
import org.team2471.frc.lib.coroutines.periodic
import org.team2471.frc.lib.coroutines.suspendUntil
import org.team2471.frc.lib.framework.RobotProgram
import org.team2471.frc.lib.framework.initializeWpilib
import org.team2471.frc.lib.framework.runRobotProgram

object Robot : RobotProgram {
    init {
        logger

        AutoChooser
        Vision

//        Drivetrain

        initControls()
        initPreferences()
        logBuildInfo()
        initLogs()

        println("Hello from my robot!")
    }

    override suspend fun enable() {
        TelemetryScope.launch {
            periodic(0.1) { log() }
        }

        Drivetrain.brake()
    }

    override suspend fun disable() {
        TelemetryScope.launch {
            periodic(0.25) { log() }
        }

        suspendUntil { Math.abs(Drivetrain.speed) < 0.5 }
        Drivetrain.coast()
    }

    override suspend fun teleop() {
        println("Entering teleop...")
        Shuffleboard.selectTab("Driver")
    }

    override suspend fun autonomous() {
        println("Entering autonomous...")
        Shuffleboard.selectTab("Autonomous")

        AutoChooser.runAuto()
    }
}

fun main() {
    initializeWpilib()

    runRobotProgram(Robot)
}
