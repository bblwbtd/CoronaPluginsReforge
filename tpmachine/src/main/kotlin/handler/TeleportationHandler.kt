package handler

import Main
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Chicken
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import utils.getString
import utils.setString
import java.util.*

class TeleportationHandler(private val player: Player) {
    private val machineKey = "last_machine"

    fun spawnMachine(target: Location) {

        val lastEntityUUID = player.getString(Main.plugin, machineKey)
        if (lastEntityUUID != null) {
            Bukkit.getEntity(UUID.fromString(lastEntityUUID))?.apply {
                if (!this.isDead) {
                    "Your can not summon two teleportation machine.".locale(player).color(ChatColor.RED).send(player)
                    return
                }
            }
        }

        val machine = player.location.world?.spawnEntity(player.location, EntityType.CHICKEN) as Chicken?
        machine?.apply {
            customName = "TP Machine".locale(player).color(ChatColor.RED)
            setString(Main.plugin, "target", "${target.x},${target.y},${target.z},${target.world}")
            player.setString(Main.plugin, machineKey, machine.uniqueId.toString())

            var timeout = Main.plugin.config.getInt("timeout")
            customName = timeout.toString()
            isCustomNameVisible = true
            Bukkit.getScheduler().runTaskTimer(Main.plugin, { task ->
                if (timeout > 0) {
                    timeout -= 1
                } else {
                    remove()
                    task.cancel()
                    location.world?.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0f)
                }
                customName = timeout.toString()
            }, 0, 20L)
        }

        "Teleportation machine has been created.".locale(player).color(ChatColor.GREEN).send(player)
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