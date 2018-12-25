package org.sert2521.gamename

object Talons {
    // Drivetrain
    const val DRIVE_LEFT_FRONT = 11
    const val DRIVE_LEFT_REAR = 12
    const val DRIVE_RIGHT_FRONT = 13
    const val DRIVE_RIGHT_REAR = 14
}

object Operator {
    const val PRIMARY_STICK = 0
    const val SECONDARY_STICK = 1
}

object Characteristics {
    const val WHEEL_DIAMETER = 4
    const val WHEELBASE_WIDTH = 0.7
    const val ENCODER_TICKS_PER_REVOLUTION = 4096
    const val ENCODER_TICKS_PER_METER = ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)
}
