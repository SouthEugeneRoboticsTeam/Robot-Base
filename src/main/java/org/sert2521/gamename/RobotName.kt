package org.sert2521.gamename

import edu.wpi.first.wpilibj.CameraServer
import org.sert2521.gamename.drivetrain.Drivetrain
import org.sert2521.gamename.util.Lights
import org.sert2521.gamename.util.UDPServer
import org.sert2521.gamename.util.initPreferences
import org.sert2521.gamename.util.logTelemetry
import org.sertain.Robot

class RobotName : Robot() {
    override fun onCreate() {
        Drivetrain
        Lights

        UDPServer.start()
        CameraServer.getInstance().startAutomaticCapture()

        initPreferences()
        logTelemetry()
    }
}
