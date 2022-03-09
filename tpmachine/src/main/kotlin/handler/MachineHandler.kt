package handler

import Main
import i18n.color
import i18n.locale
import i18n.send
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
            Bukkit.getEntity(UUID.fromString(lastEntityUUID))?.apply {
                if (!this.isDead) {
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

            var timeout = Main.plugin.config.getInt("timeout")
            customName = timeout.toString()
            Bukkit.getScheduler().runTaskTimer(Main.plugin, { task ->
                if (timeout > 0) {
                    timeout -= 1
                } else {
                    remove()
                    task.cancel()
                }
                customName = timeout.toString()
            }, 0, 20L)
        }
    }

    fun mountMachine(machine: Chicken) {
        machine.run {
            val location = getString(Main.plugin, "target")!!.split(",").run {
                Location(Bukkit.getWorld(get(3)), get(0).toDouble(), get(1).toDouble(), get(2).toDouble())
            }
            addPassenger(player)
            if (machine.passengers.size == 1) {
                var countDown = Main.plugin.config.getInt("delay")
                Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plugin, { task ->
                    passengers.forEach { entity ->
                        "Teleporting in ".locale(player).plus(countDown).color(ChatColor.RED).send(entity)
                    }
                    if (countDown > 0) {
                        countDown -= 1
                    } else {
                        task.cancel()
                        teleport(location)
//                        passengers.forEach { entity -> entity.teleport(location) }
                    }
                }, 0, 20L)
            }
        }
    }
}