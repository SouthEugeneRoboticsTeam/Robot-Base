package org.sert2521.gamename.drivetrain

import org.sertain.command.Command

/**
 * This command allows for arcade drive of the robot.
 */
class ArcadeDrive : Command() {
    override fun execute(): Boolean {
        Drivetrain.arcade()
        return false
    }

    override fun onDestroy() {
        Drivetrain.stop()
    }
}
