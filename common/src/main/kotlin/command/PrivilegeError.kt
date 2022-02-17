package command

import com.github.ajalt.clikt.core.PrintMessage
import getText
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PrivilegeError(sender: CommandSender? = null) :
    PrintMessage(getText("Privilege Error", if (sender is Player) sender.locale else null))