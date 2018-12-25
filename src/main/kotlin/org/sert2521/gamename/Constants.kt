package org.sert2521.gamename

// Joysticks
const val PRIMARY_STICK_PORT = 0
const val SECONDARY_STICK_PORT = 1

// Talon IDs
const val LEFT_FRONT_MOTOR = 11
const val LEFT_REAR_MOTOR = 12
const val RIGHT_FRONT_MOTOR = 13
const val RIGHT_REAR_MOTOR = 14

// Auto
const val WHEEL_DIAMETER = 4
const val WHEELBASE_WIDTH = 0.7
const val ENCODER_TICKS_PER_REVOLUTION = 4096
const val ENCODER_TICKS_PER_METER = ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)
