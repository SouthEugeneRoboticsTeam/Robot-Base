package org.sert2521.gamename.driving

import org.strongback.command.Command
import org.strongback.components.ui.ContinuousRange
import org.strongback.drive.TankDrive

/**
 * This command allows for tank drive of the robot.
 */
class TankDrive(private val drive: TankDrive,
                private val left: ContinuousRange,
                private val right: ContinuousRange) : Command() {
    override fun execute(): Boolean {
        // Input values are squared by default, making driving smoother
        drive.tank(left.read(), right.read())
        return false
    }

    override fun interrupted() = end()

    override fun end() = drive.stop()
}
