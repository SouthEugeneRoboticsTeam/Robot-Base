package org.sert2521.gamename.util

import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj.Timer.getMatchTime
import org.sertain.command.Command
import org.sertain.command.Subsystem

object Lights : Subsystem() {
    private val redLEDChannel = DigitalOutput(RED_LED_PORT)
    private val blueLEDChannel = DigitalOutput(BLUE_LED_PORT)

    override val defaultCommand = RunLights()

    fun setLEDs() {
        if (getMatchTime() > 120 && getMatchTime() < 123) {
            redLEDChannel.set(false)
            blueLEDChannel.set(false)
        } else {
            redLEDChannel.set(true)
            blueLEDChannel.set(true)
        }
    }
}

class RunLights : Command() {
    init {
        requires(Lights)
    }

    override fun execute(): Boolean {
        Lights.setLEDs()
        return false
    }
}
