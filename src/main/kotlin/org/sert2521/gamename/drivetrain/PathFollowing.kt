package org.sert2521.gamename.drivetrain

import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.team2471.frc.lib.coroutines.periodic
import org.team2471.frc.lib.math.windRelativeAngles
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.vector.Vector2

suspend fun Drivetrain.driveAlongPath(path: Path2D, extraTime: Double = 0.0) {
    println("Driving along path ${path.name}, duration: ${path.durationWithSpeed}," +
                    "travel direction: ${path.robotDirection}, mirrored: ${path.isMirrored}")

    path.resetDistances()
    zeroEncoders()
    zeroGyro()

    var prevLeftDistance = 0.0
    var prevRightDistance = 0.0
    var prevTime = 0.0

    val pathAngleEntry = telemetry.table.getEntry("Path Angle")
    val angleErrorEntry = telemetry.table.getEntry("Angle Error")
    val gyroCorrectionEntry = telemetry.table.getEntry("Gyro Correction")

    val leftPositionErrorEntry = telemetry.table.getEntry("Left Position Error")
    val rightPositionErrorEntry = telemetry.table.getEntry("Right Position Error")

    val leftVelocityErrorEntry = telemetry.table.getEntry("Left Velocity Error")
    val rightVelocityErrorEntry = telemetry.table.getEntry("Right Velocity Error")

    val leftPositionEntry = telemetry.table.getEntry("Left Position")
    val rightPositionEntry = telemetry.table.getEntry("Right Position")

    val leftPercentage = telemetry.table.getEntry("Left Percentage")
    val rightPercentage = telemetry.table.getEntry("Right Percentage")

    val lastSet = telemetry.table.getEntry("Time Between")

    val timer = Timer().apply { start() }

    var accumulator = 0.0
    var finished: Boolean

    var lastSetTime = timer.get()

    try {
        periodic(watchOverrun = false) {
            val t = timer.get()
            val dt = t - prevTime

            val gyroAngle = ahrs.angle
            val pathAngle = Math.toDegrees(Vector2.angle(path.getTangent(t)))
            val angleError = pathAngle - windRelativeAngles(pathAngle, gyroAngle)

            accumulator = accumulator * GYRO_CORRECTION_I_DECAY + angleError

            val correction = if (SmartDashboard.getBoolean("Use Gyro", true)) {
                angleError * GYRO_CORRECTION_P + accumulator * GYRO_CORRECTION_I
            } else {
                0.0
            }

            val leftDistance = path.getLeftDistance(t) + correction
            val rightDistance = path.getRightDistance(t) - correction

            val leftVelocity = (leftDistance - prevLeftDistance) / dt
            val rightVelocity = (rightDistance - prevRightDistance) / dt

            val leftVelocityError = leftDrive.velocity - leftVelocity
            val rightVelocityError = rightDrive.velocity - rightVelocity

            val velocityDelta = (leftVelocity - rightVelocity) * TURNING_FEED_FORWARD

            pathAngleEntry.setDouble(pathAngle)
            angleErrorEntry.setDouble(angleError)
            leftPositionErrorEntry.setDouble(leftDrive.closedLoopError)
            rightPositionErrorEntry.setDouble(rightDrive.closedLoopError)
            leftVelocityErrorEntry.setDouble(leftVelocityError)
            rightVelocityErrorEntry.setDouble(rightVelocityError)
            leftPositionEntry.setDouble(leftDistance)
            rightPositionEntry.setDouble(rightDistance)
            gyroCorrectionEntry.setDouble(correction)
            leftPercentage.setDouble(leftDrive.output)
            rightPercentage.setDouble(rightDrive.output)

            val leftFeedForward = leftVelocity * LEFT_FEED_FORWARD_COEFFICIENT +
                    (LEFT_FEED_FORWARD_OFFSET * Math.signum(leftVelocity)) + velocityDelta

            val rightFeedForward = rightVelocity * RIGHT_FEED_FORWARD_COEFFICIENT +
                    (RIGHT_FEED_FORWARD_OFFSET * Math.signum(rightVelocity)) - velocityDelta

            drivePosition(feetToTicks(leftDistance), feetToTicks(rightDistance), leftFeedForward, rightFeedForward)

            lastSet.setDouble(lastSetTime - timer.get())
            lastSetTime = timer.get()

            if (leftDrive.output > 0.95) {
                DriverStation.reportWarning("Left motor is saturated", false)
            }

            if (rightDrive.output > 0.95) {
                DriverStation.reportWarning("Right motor is saturated", false)
            }

            finished = t >= path.durationWithSpeed + extraTime

            prevTime = t
            prevLeftDistance = leftDistance
            prevRightDistance = rightDistance

            if (finished) {
                this.stop()
            }
        }
    } finally {
        stop()
    }
}
