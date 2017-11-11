package org.sert2521.gamename.util

import edu.wpi.first.wpilibj.Preferences
import org.strongback.components.ui.ContinuousRange
import org.strongback.components.ui.FlightStick
import org.strongback.hardware.Hardware

val leftJoystick: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(LEFT_STICK_PORT)
val rightJoystick: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(RIGHT_STICK_PORT)

val FlightStick.scaledThrottle: ContinuousRange
    get() = throttle.invert().map { (it + 1) * .5 }
val FlightStick.scaledPitch: ContinuousRange
    get() = pitch.scale(Preferences.getInstance().getDouble("scaled_speed", 1.0))
val FlightStick.scaledRoll: ContinuousRange
    get() = roll.scale(Preferences.getInstance().getDouble("scaled_speed", 1.0))
