package handler

import Main
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Chicken
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import utils.getString
import utils.removeValue
import utils.setString
import java.util.*

class TeleportationHandler(private val player: Player) {
    companion object {
        val machineKey = "last_machine"
    }


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

            getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 80.0
            getAttribute(Attribute.GENERIC_FLYING_SPEED)?.baseValue = 1.0
            getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = 1.0
            addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5))
            health = 80.0

            customName = "TP Machine".locale(player).color(ChatColor.GREEN)
            player.setString(Main.plugin, machineKey, machine.uniqueId.toString())

            setString(Main.plugin, "machine", "true")

            mountMachine(this, target)
        }

        "Teleportation machine has been created.".locale(player).color(ChatColor.GREEN).send(player)
    }

    private fun disappearCountdown(machine: Chicken) {
        var timeout = Main.plugin.config.getInt("timeout")
        machine.apply {
            Bukkit.getScheduler().runTaskTimer(Main.plugin, { task ->
                if (timeout > 0) {
                    timeout -= 1
                } else {
                    task.cancel()
                    player.removeValue(Main.plugin, machineKey)
                    customName = null
                    if (!isDead) {
                        location.world?.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0f)
                    }
                    teleport(location.add(0.0, -999.9, 0.0))
                    remove()
                }
            }, 0, 20L)
        }
    }

    private fun mountMachine(machine: Chicken, target: Location) {
        machine.run {
            addPassenger(player)
            var countDown = Main.plugin.config.getInt("delay")
            Bukkit.getScheduler().runTaskTimer(Main.plugin, { task ->
                passengers.forEach { entity ->
                    "Teleporting in ".locale(player).plus(countDown).color(ChatColor.RED).send(entity)
                }
                if (countDown > 0) {
                    countDown -= 1
                } else {
                    task.cancel()
                    val clonedPassages = passengers.slice(0 until passengers.size)
                    clonedPassages.forEach { entity ->
                        entity.teleport(target)
                        entity.location.world?.playSound(entity, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 5f)
                    }
                    teleport(target)
                    Bukkit.getScheduler().runTaskLater(Main.plugin, { _ ->
                        clonedPassages.forEach {
                            "You have been teleported.".locale(it).color(ChatColor.GREEN).send(it)
                            addPassenger(it)
                        }
                    }, 10L)

                    disappearCountdown(this)
                }
            }, 0, 20L)

        }
    }
}