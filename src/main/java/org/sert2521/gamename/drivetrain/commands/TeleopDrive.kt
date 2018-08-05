package org.sert2521.gamename.drivetrain.commands

import org.sert2521.gamename.drivetrain.Drivetrain
import org.sert2521.gamename.util.driveSpeedScalar
import org.sert2521.gamename.util.rightJoystick
import org.sertain.command.Command

/**
 * Allows for teleoperated drive of the robot.
 */
class TeleopDrive : Command() {
    init {
        requires(Drivetrain)
    }

    override fun execute(): Boolean {
        Drivetrain.arcade(driveSpeedScalar * -rightJoystick.y, driveSpeedScalar * rightJoystick.x)

        return false
    }

    override fun onDestroy() = Drivetrain.stop()
}
