package org.sert2521.gamename

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import org.sert2521.gamename.drivetrain.Drivetrain
import org.strongback.Strongback

/**
 * This is the main robot class which calls various methods depending on the current game stage.
 */
class Robot : IterativeRobot() {
    override fun robotInit() {
        Strongback.logger().info("Robot starting...")
        Drivetrain
    }

    override fun autonomousInit() = Strongback.logger().info("Autonomous starting...")

    override fun teleopInit() = Strongback.logger().info("Teleop starting...")

    override fun autonomousPeriodic() = Scheduler.getInstance().run()

    override fun teleopPeriodic() = Scheduler.getInstance().run()

    override fun testPeriodic() = LiveWindow.run()
}
