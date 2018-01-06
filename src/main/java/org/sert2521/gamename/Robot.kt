package org.sert2521.gamename

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import org.sert2521.gamename.drivetrain.initDrivetrain
import org.strongback.Strongback

/**
 * This is the main robot class which calls various methods depending on the current game stage.
 */
class Robot : IterativeRobot() {
    override fun robotInit() {
        Strongback.logger().info("Robot starting...")
        init()
    }

    override fun autonomousInit() {
        Strongback.logger().info("Autonomous starting...")
        init()
    }

    override fun teleopInit() {
        Strongback.logger().info("Teleop starting...")
        init()
    }

    override fun testPeriodic() = LiveWindow.run()

    override fun disabledInit() = Strongback.disable()

    private fun init() {
        Strongback.start()
        initDrivetrain()
    }
}
