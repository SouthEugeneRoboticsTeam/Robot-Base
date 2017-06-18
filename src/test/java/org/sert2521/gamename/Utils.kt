package org.sert2521.gamename

import org.fest.assertions.Assertions.assertThat
import org.strongback.components.Motor
import org.strongback.components.ui.ContinuousRange

/**
 * Created by AMD on 6/17/17.
 */

fun assertMotorSpeeds(speed: Double, motors: List<Motor>, squareSpeed: Boolean = false) {
    motors.forEach {
        var expectedSpeed: Double = speed
        if (squareSpeed) expectedSpeed = Math.pow(expectedSpeed, 2.0)

        assertThat(it.speed).isEqualTo(expectedSpeed)
    }
}

class ContinuousRangeTester() {
    var value: Double = 0.0
    var continuous: ContinuousRange = ContinuousRange({ value })
}
