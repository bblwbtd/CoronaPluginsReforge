package xyz.ldgame.corona.tpmachine

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Chicken
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.common.utils.getString
import xyz.ldgame.corona.common.utils.removeValue
import xyz.ldgame.corona.common.utils.setString
import java.util.*

class MachineHandler(private val player: Player, private val plugin: JavaPlugin = Main.plugin) {

    companion object {
        const val machineKey = "lastMachine"
    }

    fun teleport(target: Location, tpDelay: Int = 5, disappearDelay: Int = 10) {
        val machine = spawnMachine() ?: return
        mountMachine(machine, target, tpDelay, disappearDelay)
    }

    private fun spawnMachine(): Chicken? {
        val lastEntityUUID = player.getString(plugin, machineKey)
        if (lastEntityUUID != null) {
            Bukkit.getEntity(UUID.fromString(lastEntityUUID))?.apply {
                if (!this.isDead) {
                    "Your can not summon two teleportation machine.".translate(player).color(ChatColor.RED).send(player)
                    return null
                }
            }
        }

        val machine = player.location.world?.spawnEntity(player.location, EntityType.CHICKEN) as Chicken?
        machine?.apply {

            getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 20.0
            getAttribute(Attribute.GENERIC_FLYING_SPEED)?.baseValue = 1.0
            getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = 1.0
            addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5))
            health = 20.0

            customName = "TP Machine".translate(player)
            player.setString(plugin, machineKey, machine.uniqueId.toString())

            setString(plugin, "machine", "true")
        }

        "Teleportation machine has been created.".translate(player).color(ChatColor.GREEN).send(player)
        return machine
    }

    private fun disappearCountdown(machine: Chicken, disappearDelay: Int) {
        machine.apply {
            var count = disappearDelay
            Bukkit.getScheduler().runTaskTimer(plugin, { task ->
                if (count > 0) {
                    count -= 1
                } else {
                    task.cancel()
                    player.removeValue(plugin, machineKey)
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

    private fun mountMachine(machine: Chicken, target: Location, tpDelay: Int, disappearDelay: Int) {
        machine.run {
            addPassenger(player)
            var countDown = tpDelay
            Bukkit.getScheduler().runTaskTimer(plugin, { task ->
                passengers.forEach { entity ->
                    "Teleporting in ".translate(player).plus(countDown).color(ChatColor.RED).send(entity)
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
                    clonedPassages.forEach {
                        "You have been teleported.".translate(it).color(ChatColor.GREEN).send(it)
                    }
                    disappearCountdown(this, disappearDelay)

                }
            }, 0, 20L)

        }
    }
}