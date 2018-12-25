package org.sert2521.gamename.util

fun applyDeadband(value: Double, deadband: Double = 0.02) = if (Math.abs(value) > deadband) {
    if (value > 0.0) {
        (value - deadband) / (1.0 - deadband)
    } else {
        (value + deadband) / (1.0 - deadband)
    }
} else {
    0.0
}
