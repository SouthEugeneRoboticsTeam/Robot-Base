package org.sert2521.gamename.driving

import org.sert2521.gamename.util.FRONT_LEFT_MOTOR
import org.sert2521.gamename.util.FRONT_RIGHT_MOTOR
import org.sert2521.gamename.util.REAR_LEFT_MOTOR
import org.sert2521.gamename.util.REAR_RIGHT_MOTOR
import org.sert2521.gamename.util.leftJoystick
import org.sert2521.gamename.util.rightJoystick
import org.strongback.Strongback
import org.strongback.components.Motor
import org.strongback.hardware.Hardware
import java.util.function.Supplier
import org.strongback.drive.TankDrive as Drive

val drive = Drive(
        Motor.compose(
                Hardware.Motors.talonSRX(FRONT_LEFT_MOTOR),
                Hardware.Motors.talonSRX(FRONT_RIGHT_MOTOR)
        ),
        Motor.compose(
                Hardware.Motors.talonSRX(REAR_LEFT_MOTOR),
                Hardware.Motors.talonSRX(REAR_RIGHT_MOTOR)
        )
)

fun initDrivetrain() {
    Strongback.submit(ArcadeDrive(drive, leftJoystick.pitch, leftJoystick.roll))
    Strongback.switchReactor().apply {
        onTriggeredSubmit(leftJoystick.thumb, Supplier {
            ArcadeDrive(drive, leftJoystick.pitch, leftJoystick.roll)
        })
        onTriggeredSubmit(rightJoystick.thumb, Supplier {
            TankDrive(drive, leftJoystick.pitch, rightJoystick.pitch)
        })
    }
}
