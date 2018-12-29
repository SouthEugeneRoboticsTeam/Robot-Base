package org.sert2521.gamename.util

import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.NetworkTableInstance

object Vision {
    var found: Boolean? = false
    var time: Long? = 0
    var xOffset: Double? = null
    var yOffset: Double? = null

    init {
        val flags = EntryListenerFlags.kImmediate or
                EntryListenerFlags.kNew or
                EntryListenerFlags.kUpdate

        val visionTable = NetworkTableInstance.getDefault().getTable("Vision")

        visionTable.addEntryListener({ _, key, _, value, _ ->
            when (key) {
                "found" -> found = value.boolean
                "time" -> time = value.double.toLong()
                "xOffset" -> xOffset = value.double
                "yOffset" -> yOffset = value.double
            }
        }, flags)
    }
}
