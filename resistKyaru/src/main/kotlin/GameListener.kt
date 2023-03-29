import handlers.isPlaying
import i18n.color
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import utils.setName
import java.net.URL
import java.util.*

class GameListener : Listener {

    companion object {
        val kyaruSkull = ItemStack(Material.PLAYER_HEAD).apply {
            setName("Kyaru's Head")
            val meta = itemMeta as SkullMeta
            val profile = Bukkit.createPlayerProfile(UUID.fromString("9280cfd4-7d2b-4a77-8685-735e20ac2af0")).apply {
                textures.skin = URL(CommonMain.plugin.config.getString("skin_url"))
            }
            meta.ownerProfile = profile
            itemMeta = meta

        }
    }

    @EventHandler
    fun transform(event: EntityDamageByEntityEvent) {
        if (!isPlaying) return

        if (event.entity !is Player || event.damager !is Player) {
            return
        }

        val damager = event.damager as Player
        val victim = event.entity as Player

        if (damager.inventory.helmet != kyaruSkull || victim.inventory.helmet == kyaruSkull) {
            return
        }

        if (event.finalDamage > victim.health) {
            event.isCancelled = true

            victim.apply {
                health = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
                inventory.helmet?.let {
                    location.world?.dropItem(location, inventory.helmet!!)
                }
                inventory.helmet = kyaruSkull
                "You just got a kyaru's head!".color(ChatColor.YELLOW).send(victim)
            }

            damager.foodLevel += 4
        }
    }

    @EventHandler
    fun preventRemoveSkull(event: InventoryClickEvent) {
        if (!isPlaying) return

        if (isPlaying && event.currentItem == kyaruSkull && event.clickedInventory?.type == InventoryType.PLAYER) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventRemoveEffect(event: PlayerItemConsumeEvent) {
        if (!isPlaying) return

        val player = event.player
        if (event.item.type == Material.MILK_BUCKET && player.inventory.helmet == kyaruSkull) {
            event.isCancelled = true
        }
    }
}