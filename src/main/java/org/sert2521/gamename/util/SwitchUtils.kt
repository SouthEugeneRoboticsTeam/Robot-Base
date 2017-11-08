package org.sert2521.gamename.util

import org.strongback.Strongback
import org.strongback.SwitchReactor
import org.strongback.command.Command
import org.strongback.command.Requirable
import org.strongback.components.Switch
import java.util.function.Supplier

fun SwitchReactor.whileTriggeredSubmit(switch: Switch, supplier: Supplier<Command>) {
    val cancelRequireable = object : Requirable {}

    onTriggeredSubmit(switch, object : Supplier<Command> {
        private val requirements: Set<Requirable> =
                supplier.get().requirements.toMutableSet().apply {
                    add(cancelRequireable)
                }

        override fun get(): Command = CommandWrapper(
                supplier.get(),
                overrideRequirements = requirements
        )
    })
    onUntriggeredSubmit(switch, Supplier {
        Command.create(Runnable {
            Strongback.submit(Command.cancel(cancelRequireable))
        })
    })
}

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
