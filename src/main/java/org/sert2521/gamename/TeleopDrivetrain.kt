package org.sert2521.gamename

import edu.wpi.first.wpilibj.command.Command


class TeleopDrivetrain : Command() {
    init {
        requires(Drivetrain)
    }

    override fun execute() = Drivetrain.teleoperatedDrive()

    override fun isFinished() = false
}
