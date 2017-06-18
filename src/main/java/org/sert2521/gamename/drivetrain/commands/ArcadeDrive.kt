package org.sert2521.gamename.drivetrain.commands

import org.strongback.command.Command
import org.strongback.components.ui.ContinuousRange
import org.strongback.drive.TankDrive

/**
 * This command allows for arcade drive of the robot.
 */
class ArcadeDrive(private val drive: TankDrive, private val pitch: ContinuousRange, private val roll: ContinuousRange) : Command() {
    override fun execute(): Boolean {
        // Input values are squared by default, making driving smoother
        drive.arcade(pitch.read(), roll.read())
        return false
    }

    override fun interrupted() = end()

    override fun end() = drive.stop()
}
