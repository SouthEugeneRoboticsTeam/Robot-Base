package org.sert2521.gamename.driving

import edu.wpi.first.wpilibj.Preferences
import org.strongback.command.Command
import org.strongback.components.ui.ContinuousRange
import org.strongback.drive.TankDrive

/**
 * This command allows for arcade drive of the robot.
 */
class ArcadeDrive(
        private val drive: TankDrive,
        private val pitch: ContinuousRange,
        private val roll: ContinuousRange
) : Command(drive) {
    override fun execute(): Boolean {
        val scaledSpeed = Preferences.getInstance().getDouble("scaled_speed", 1.0)
        drive.arcade(pitch.read() * scaledSpeed, roll.read() * scaledSpeed)
        return false
    }

    override fun end() = drive.stop()
}
