package org.sert2521.gamename.drivetrain

import edu.wpi.first.wpilibj.command.Subsystem
import org.sert2521.gamename.FRONT_LEFT_MOTOR
import org.sert2521.gamename.FRONT_RIGHT_MOTOR
import org.sert2521.gamename.OI
import org.sert2521.gamename.REAR_LEFT_MOTOR
import org.sert2521.gamename.REAR_RIGHT_MOTOR
import org.sert2521.gamename.drivetrain.commands.ArcadeDrive
import org.strongback.Strongback
import org.strongback.components.Motor
import org.strongback.drive.TankDrive
import org.strongback.hardware.Hardware

/**
 * This is the Drivetrain subsystem, which is in charge of controlling the speeds of the individual
 * talons and converting joystick input to movement speeds.
 */
object Drivetrain : Subsystem() {
    private val frontLeft = Hardware.Motors.talon(FRONT_LEFT_MOTOR)
    private val frontRight = Hardware.Motors.talon(FRONT_RIGHT_MOTOR)
    private val rearLeft = Hardware.Motors.talon(REAR_LEFT_MOTOR)
    private val rearRight = Hardware.Motors.talon(REAR_RIGHT_MOTOR)

    private val left = Motor.compose(frontLeft, rearLeft)
    private val right = Motor.compose(frontRight, rearRight)

    private val drive: TankDrive

    init {
        drive = TankDrive(left, right)
    }

    override fun initDefaultCommand() {
        Strongback.submit(ArcadeDrive(drive, OI.left.pitch, OI.left.roll))
    }
}
