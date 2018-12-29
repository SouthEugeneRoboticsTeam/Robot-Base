package org.sert2521.gamename

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
    }

    override suspend fun autonomous() {
        println("Entering autonomous...")
        AutoChooser.runAuto()
    }
}

fun main() {
    initializeWpilib()

    runRobotProgram(Robot)
}
