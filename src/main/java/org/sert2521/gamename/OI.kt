package org.sert2521.gamename

import org.strongback.hardware.Hardware

/**
 * This object defines the operator interface controls, such as joysticks, buttons and switches.
 */
object OI {
    val left = Hardware.HumanInterfaceDevices.logitechAttack3D(LEFT_STICK_PORT)
    val right = Hardware.HumanInterfaceDevices.logitechAttack3D(RIGHT_STICK_PORT)
}
