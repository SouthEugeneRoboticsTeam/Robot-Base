package org.sert2521.gamename.util

import org.strongback.SwitchReactor
import org.strongback.command.Command
import org.strongback.command.Requirable
import org.strongback.components.Switch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier

fun SwitchReactor.whileTriggeredSubmit(switch: Switch, supplier: Supplier<Command>) {
    val cancelRequirable = object : Requirable {}

    onTriggeredSubmit(switch, object : Supplier<Command> {
        private val requirements: Set<Requirable> =
                supplier.get().requirements.toMutableSet().apply {
                    add(cancelRequirable)
                }

        override fun get(): Command = CommandWrapper(
                supplier.get(),
                overrideRequirements = requirements
        )
    })
    onUntriggeredSubmit(switch, Supplier {
        Command.cancel(cancelRequirable)
    })
}

fun SwitchReactor.onTriggeredLifecycleSubmit(
        switch: Switch,
        supplier: Supplier<Command>
) = onTriggeredSubmit(switch, object : Supplier<Command> {
    private val cancelRequirable = object : Requirable {}
    private val isRunning = AtomicBoolean()
    private val requirements: Set<Requirable> =
            supplier.get().requirements.toMutableSet().apply {
                add(cancelRequirable)
            }

    override fun get(): Command = if (isRunning.getAndSet(!isRunning.get())) {
        Command.cancel(cancelRequirable)
    } else {
        CommandWrapper(supplier.get(), overrideRequirements = requirements)
    }
})

private class CommandWrapper(
        private val original: Command,
        overrideTimeoutInSeconds: Double = original.timeoutInSeconds,
        overrideRequirements: Collection<Requirable> = original.requirements
) : Command(overrideTimeoutInSeconds, overrideRequirements) {
    override fun initialize() = original.initialize()

    override fun execute() = original.execute()

    override fun interrupted() = original.interrupted()

    override fun end() = original.end()
}
