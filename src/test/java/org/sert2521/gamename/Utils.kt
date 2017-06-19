package org.sert2521.gamename

import org.fest.assertions.Assertions.assertThat
import org.strongback.components.Motor
import org.strongback.components.ui.ContinuousRange

fun assertMotorSpeeds(speed: Double, motors: List<Motor>, squareSpeed: Boolean = false) = motors.forEach {
    val expectedSpeed = if (squareSpeed) Math.pow(speed, 2.0) else speed
    assertThat(it.speed).isEqualTo(expectedSpeed)
}

class ContinuousRangeTester {
    var value = 0.0
    var continuous = ContinuousRange({ value })
}
