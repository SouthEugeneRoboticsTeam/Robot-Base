package org.sert2521.gamename

import edu.wpi.first.wpilibj.Joystick

/**
 * This object defines the operator interface controls, such as joysticks, buttons and switches.
 */
object OI {
    val left = Joystick(LEFT_STICK_PORT)
    val right = Joystick(RIGHT_STICK_PORT)
}
