import handler.MachineHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Chicken
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.ChunkUnloadEvent
import utils.getString
import utils.removeValue
import java.util.*

class EventListener : Listener {
    private val random = Random(System.currentTimeMillis())

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        if (event.entity.getString(Main.plugin, "machine") != null) {
            event.entity.customName = null
            event.drops.clear()
            event.droppedExp = 0
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return
        if (event.entity.getString(Main.plugin, "machine") != null) {
            val chicken = event.entity as Chicken
            if (chicken.health - event.finalDamage <= 1.0 && random.nextFloat() <= 0.01) {
                chicken.location.world?.createExplosion(chicken.location, 10f, true, true)
            }
        }
    }

    @EventHandler
    fun onChuckUnload(event: ChunkUnloadEvent) {
        event.chunk.entities.forEach {
            if (it.getString(Main.plugin, "machine") != null) {
                Bukkit.getScheduler().runTask(Main.plugin, Runnable {
                    it.teleport(it.location.add(0.0, -999.9, 0.0))
                    it.remove()
                })
            }
        }
    }

    private fun removeLastMachine(player: Entity) {
        player.apply {
            getString(Main.plugin, MachineHandler.machineKey)?.let {
                Bukkit.getEntity(UUID.fromString(it))?.apply {
                    removePassenger(player)
                    teleport(location.add(0.0, -999.9, 0.0))
                    remove()
                }
                removeValue(Main.plugin, MachineHandler.machineKey)
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        removeLastMachine(event.player)
    }

    @EventHandler
    fun onPlayerLogout(event: PlayerQuitEvent) {
        removeLastMachine(event.player)
    }

}