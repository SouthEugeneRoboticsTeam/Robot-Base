package org.sert2521.gamename.drivetrain

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.I2C
import org.sert2521.gamename.Characteristics
import org.sert2521.gamename.Talons
import org.sert2521.gamename.util.Logger
import org.sert2521.gamename.util.Telemetry
import org.team2471.frc.lib.actuators.TalonSRX
import org.team2471.frc.lib.framework.Subsystem
import org.team2471.frc.lib.math.deadband
import kotlin.math.abs
import kotlin.math.max

/**
 * The robot's drive system.
 */
object Drivetrain : Subsystem("Drivetrain", { teleopDrive() }) {
    val leftDrive = TalonSRX(Talons.DRIVE_LEFT_FRONT, Talons.DRIVE_LEFT_REAR).config {
        feedbackCoefficient = feetToTicks(1.0)
        brakeMode()
        closedLoopRamp(0.1)
        sensorPhase(true)
        pid(0) {
            p(DISTANCE_P)
            d(DISTANCE_D)
        }
    }

    val rightDrive = TalonSRX(Talons.DRIVE_RIGHT_FRONT, Talons.DRIVE_RIGHT_REAR).config {
        feedbackCoefficient = feetToTicks(1.0)
        brakeMode()
        inverted(true)
        closedLoopRamp(0.1)
        sensorPhase(true)
        pid(0) {
            p(DISTANCE_P)
            d(DISTANCE_D)
        }
    }

    val telemetry = Telemetry(this)
    val logger = Logger(this)

    val ahrs = AHRS(I2C.Port.kMXP)

    val speed: Double get() = (leftDrive.velocity + rightDrive.velocity) / 2.0

    val leftSpeed: Double get() = leftDrive.velocity

    val rightSpeed: Double get() = rightDrive.velocity

    val position: Double get() = (leftDrive.position + rightDrive.position) / 2.0

    val leftDistance get() = leftDrive.position

    val rightDistance get() = rightDrive.position

    val distance get() = (leftDistance + rightDistance) / 2.0

    init {
        telemetry.add("Gyro") { ahrs.angle }

        logger.addNumberTopic("Angle", "deg") { ahrs.angle }
        logger.addNumberTopic("Left Output") { leftDrive.output }
        logger.addNumberTopic("Right Output") { rightDrive.output }

        zeroEncoders()
        zeroGyro()
    }

    fun zeroEncoders() {
        leftDrive.position = 0.0
        rightDrive.position = 0.0
    }

    fun zeroGyro() {
        ahrs.reset()
    }

    fun ticksToFeet(ticks: Int) =
        ticks.toDouble() / Characteristics.ENCODER_TICKS_PER_REVOLUTION * Characteristics.WHEEL_DIAMETER * Math.PI / 12.0

    fun feetToTicks(feet: Double) =
        feet * 12.0 / Math.PI / Characteristics.WHEEL_DIAMETER * Characteristics.ENCODER_TICKS_PER_REVOLUTION

    fun drive(throttle: Double, turn: Double) {
        val scaledThrottle = abs(throttle).coerceAtMost(1.0).deadband(0.02)
        val scaledTurn = abs(throttle).coerceAtMost(1.0).deadband(0.02)

        val squaredThrottle = Math.copySign(scaledThrottle * scaledThrottle, throttle)
        val squaredTurn = Math.copySign(scaledTurn * scaledTurn, turn)

        val maxPower = Math.copySign(max(abs(squaredThrottle), abs(squaredTurn)), throttle)

        val leftPower: Double
        val rightPower: Double

        if (squaredThrottle >= 0.0) {
            if (squaredTurn >= 0.0) {
                leftPower = maxPower
                rightPower = squaredThrottle - squaredTurn
            } else {
                leftPower = squaredThrottle + squaredTurn
                rightPower = maxPower
            }
        } else {
            if (squaredTurn >= 0.0) {
                leftPower = squaredThrottle + squaredTurn
                rightPower = maxPower
            } else {
                leftPower = maxPower
                rightPower = squaredThrottle - squaredTurn
            }
        }

        leftDrive.setPercentOutput(leftPower.coerceAtMost(1.0))
        rightDrive.setPercentOutput(rightPower.coerceAtMost(1.0) * -1)
    }

    fun drivePosition(
        leftPosition: Double,
        rightPosition: Double,
        leftFeedForward: Double = 0.0,
        rightFeedForward: Double = 0.0
    ) {
        leftDrive.setPositionSetpoint(leftPosition, leftFeedForward)
        rightDrive.setPositionSetpoint(rightPosition, rightFeedForward)
    }

    fun coast() {
        leftDrive.config {
            coastMode()
        }

        rightDrive.config {
            coastMode()
        }
    }

    fun brake() {
        leftDrive.config {
            brakeMode()
        }

        rightDrive.config {
            brakeMode()
        }
    }

    fun stop() {
        leftDrive.stop()
        rightDrive.stop()
    }
}
