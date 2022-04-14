package commands

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor
import org.bukkit.command.CommandSender

class Executor : MagicCommandExecutor() {
    override fun getCommand(sender: CommandSender?): MagicCommand {
        return FriendCommand(sender).subcommands(
            AddCommand(sender),
            FriendCommand(sender),
            RemoveCommand(sender),
            ListCommand(sender),
            TPCommand(sender),
            RequestCommand(sender)
        )
    }
}