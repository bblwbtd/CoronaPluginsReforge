package commands

import command.MagicCommand
import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import utils.getKey
import utils.getKeyList

class CopyCommand(sender: CommandSender?) :
    MagicCommand(sender, help = "Copy the key in your main hand to the item in off hand.") {

    override fun run() {
        val player = checkSenderType<Player>()
        val mainHandItem = player.inventory.itemInMainHand
        val offHandItem = player.inventory.itemInOffHand

        if (mainHandItem.type.toString() !in getKeyList() || getKey(mainHandItem) == null) {
            "You have no key in main hand".locale(player).color(ChatColor.RED).send(player)
            return
        }

        if (offHandItem.type.toString() !in getKeyList()) {
            "The item in your off hand isn't a valid key material.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        DepositBoxHandler(player).copyKey(mainHandItem, offHandItem)
    }
}