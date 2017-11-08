package org.sert2521.gamename.util

import org.strongback.components.ui.ContinuousRange
import org.strongback.components.ui.FlightStick
import org.strongback.hardware.Hardware

val leftJoystick: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(LEFT_STICK_PORT)
val rightJoystick: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(RIGHT_STICK_PORT)

val FlightStick.scaledThrottle
    get() = ContinuousRange {
        // Scale this instead of dividing to avoid NaN exceptions
        (throttle.read() + 1) * .5
    }
