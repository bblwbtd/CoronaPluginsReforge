import org.bukkit.entity.Chicken
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import utils.getString

class EventListener : Listener {

    @EventHandler
    fun preventDrop(event: EntityDeathEvent) {
        if (event.entity.getString(Main.plugin, "machine") != null) {
            event.drops.clear()
            event.droppedExp = 0
        }

    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity.getString(Main.plugin, "machine") != null) {
            val chicken = event.entity as Chicken
            chicken.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 10, 10))
        }
    }
}