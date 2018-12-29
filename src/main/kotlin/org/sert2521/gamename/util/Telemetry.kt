package org.sert2521.gamename.util

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.team2471.frc.lib.coroutines.periodic
import org.team2471.frc.lib.framework.Subsystem
import kotlin.coroutines.CoroutineContext

object TelemetryScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}

class Telemetry {
    private data class Binding(val name: String, val body: () -> Any)

    val table: NetworkTable

    private val bindings = mutableListOf<Binding>()

    constructor(name: String) {
        table = NetworkTableInstance.getDefault().getTable(name)!!
    }

    constructor(subsystem: Subsystem) {
        table = NetworkTableInstance.getDefault().getTable(subsystem.name)!!
    }

    private fun tick() = bindings.toList().forEach { put(it.name, it.body()) }

    fun add(name: String, body: () -> Any) = bindings.add(Binding(name, body))

    fun remove(name: String) = bindings.removeIf { it.name == name }

    fun put(name: String, value: Any) = table.getEntry(name).setValue(value)

    init {
        TelemetryScope.launch {
            periodic(0.1, false) {
                tick()
            }
        }
    }
}

val GlobalTelemetry = Telemetry("Global")
