package org.sert2521.gamename.util

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.Preferences
import org.sert2521.gamename.PRIMARY_STICK_PORT
import org.sert2521.gamename.SECONDARY_STICK_PORT
import org.team2471.frc.lib.framework.createMappings
import edu.wpi.first.wpilibj.DriverStation

private val logger = Logger("Input")

val primaryJoystick by lazy { Joystick(PRIMARY_STICK_PORT) }
val secondaryJoystick by lazy { Joystick(SECONDARY_STICK_PORT) }

val driveSpeedScalar get() = Preferences.getInstance().getDouble("drive_speed_scalar", 1.0)

fun initControls() {
    primaryJoystick.createMappings {
        // Primary joystick mappings
    }

    secondaryJoystick.createMappings {
        // Secondary joystick mappings
    }

    for (i in 0 until DriverStation.kJoystickPorts) {
        logger.addValue("Controller $i Type",
                        DriverStation.getInstance().getJoystickName(i) ?: "Unknown")
    }
}

fun initPreferences() {
    Preferences.getInstance().putDouble("drive_speed_scalar", driveSpeedScalar)
}
