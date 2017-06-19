package org.sert2521.gamename

import org.strongback.components.ui.FlightStick
import org.strongback.hardware.Hardware

/**
 * This object defines the operator interface controls, such as joysticks, buttons and switches.
 */
object OI {
    val left: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(LEFT_STICK_PORT)
    val right: FlightStick = Hardware.HumanInterfaceDevices.logitechAttack3D(RIGHT_STICK_PORT)
}
