package commands

import GameListener
import command.MagicCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveCommand(sender: CommandSender?) : MagicCommand(sender, help = "Give you a kyaru's skull.") {
    override fun run() {
        val player = checkSenderType<Player>()

        player.inventory.addItem(GameListener.kyaruSkull)
    }

}