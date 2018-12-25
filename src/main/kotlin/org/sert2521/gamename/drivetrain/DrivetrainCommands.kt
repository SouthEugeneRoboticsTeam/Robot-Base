package org.sert2521.gamename.drivetrain

import org.sert2521.gamename.util.driveSpeedScalar
import org.sert2521.gamename.util.primaryJoystick
import org.team2471.frc.lib.coroutines.periodic
import org.team2471.frc.lib.framework.use
import org.team2471.frc.lib.motion_profiling.Path2D

/**
 * Allows for teleoperated driveRaw of the robot.
 */
suspend fun teleopDrive() = use(Drivetrain) {
    periodic {
        Drivetrain.drive(driveSpeedScalar * primaryJoystick.x, -driveSpeedScalar * primaryJoystick.y)
    }
}

suspend fun followPath(path: Path2D, extraTime: Double = 0.0) = use(Drivetrain) {
    Drivetrain.driveAlongPath(path, extraTime)
}
