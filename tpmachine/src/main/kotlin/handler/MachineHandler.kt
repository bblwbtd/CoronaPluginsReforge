package handler

import Main
import i18n.color
import i18n.locale
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Chicken
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import utils.getString
import utils.setString
import java.util.*

class MachineHandler(private val player: Player) {

    fun spawnMachine(target: Location) {

        val lastEntityUUID = player.getString(Main.plugin, "last machine")
        if (lastEntityUUID != null) {
            Bukkit.getEntity(UUID.fromString(lastEntityUUID)).apply {
                if (this != null) {
                    "Your teleport machine is cooling down. Please try later.".locale(player).color(ChatColor.RED)
                    return
                }
            }
        }

        val machine = player.location.world?.spawnEntity(player.location, EntityType.CHICKEN) as Chicken?
        machine?.apply {
            customName = "TP Machine".locale(player).color(ChatColor.RED)
            setString(Main.plugin, "target", "${target.x},${target.y},${target.z},${target.world}")
            player.setString(Main.plugin, "last machine", machine.uniqueId.toString())

            Bukkit.getScheduler().runTaskLater(Main.plugin, Runnable {
                machine.remove()
            }, 30 * 20L)
        }
    }

    fun mountMachine(machine: Chicken) {
        machine.run {
            getString(Main.plugin, "target")!!.split(",")
        }
    }

}