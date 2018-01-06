package org.sert2521.gamename.drivetrain

import org.sert2521.gamename.util.LEFT_FRONT_MOTOR
import org.sert2521.gamename.util.LEFT_REAR_MOTOR
import org.sert2521.gamename.util.RIGHT_FRONT_MOTOR
import org.sert2521.gamename.util.RIGHT_REAR_MOTOR
import org.sert2521.gamename.util.leftJoystick
import org.sert2521.gamename.util.rightJoystick
import org.sert2521.gamename.util.scaledPitch
import org.sert2521.gamename.util.scaledRoll
import org.strongback.Strongback
import org.strongback.command.Command
import org.strongback.components.Motor
import org.strongback.hardware.Hardware
import java.util.function.Supplier
import org.strongback.drive.TankDrive as Drive

private val drive = Drive(
        Motor.compose(
                Hardware.Motors.talonSRX(LEFT_FRONT_MOTOR),
                Hardware.Motors.talonSRX(LEFT_REAR_MOTOR)
        ),
        Motor.compose(
                Hardware.Motors.talonSRX(RIGHT_FRONT_MOTOR),
                Hardware.Motors.talonSRX(RIGHT_REAR_MOTOR)
        )
)
private val defaultDrive: Command
    get() = ArcadeDrive(drive, leftJoystick.scaledPitch, leftJoystick.scaledRoll)

fun initDrivetrain() {
    Strongback.submit(defaultDrive)
    Strongback.switchReactor().apply {
        onTriggeredSubmit(leftJoystick.thumb, Supplier {
            ArcadeDrive(drive, leftJoystick.scaledPitch, leftJoystick.scaledRoll)
        })
        onTriggeredSubmit(rightJoystick.thumb, Supplier {
            TankDrive(drive, leftJoystick.scaledPitch, rightJoystick.scaledPitch)
        })
    }
}
