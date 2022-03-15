package listener

import Main
import handler.AuthHandler
import handler.PlayerState
import handler.loadInventory
import handler.saveInventory
import i18n.color
import i18n.getText
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack
import org.spigotmc.event.entity.EntityMountEvent
import pages.showLoginPage
import pages.showRegisterPage
import utils.*

class PlayerListener : Listener {

    private val authHandler = AuthHandler()

    private val loginEntry = ItemStack(Material.EMERALD_BLOCK).apply {
        setString("type", "login")
    }

    private val registryEntry = ItemStack(Material.REDSTONE_BLOCK).apply {
        setString("type", "register")
    }

    private fun savePlayerLocation(prefix: String, player: Player) {
        player.apply {
            setDouble(Main.plugin, "${prefix}_x", location.x)
            setDouble(Main.plugin, "${prefix}_y", location.y)
            setDouble(Main.plugin, "${prefix}_z", location.z)
            setString(Main.plugin, "${prefix}_world", location.world!!.name)
        }
    }

    private fun loadSavedPlayerLocation(prefix: String, player: Player): Location? {
        return player.run {
            val world = getString(Main.plugin, "${prefix}_world") ?: return null
            val x = getDouble(Main.plugin, "${prefix}_x") ?: return null
            val y = getDouble(Main.plugin, "${prefix}_y") ?: return null
            val z = getDouble(Main.plugin, "${prefix}_z") ?: return null

            Location(
                Bukkit.getWorld(world),
                x, y, z
            )
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.run {
            PlayerState.UNAUTHENTICATED.setState(this)

            savePlayerLocation("original", this)

            saveInventory(this)
            inventory.clear()
            inventory.addItem(if (authHandler.hasRegistered(name)) loginEntry.apply {
                setName(getText("Login Entrance", locale))
            } else registryEntry.apply {
                setName(getText("Register Entrance", locale))
            })

            sendMessage(
                getText(
                    "You need to login or register at first! Right click with the block or Use /auth command.",
                    locale
                ).color(ChatColor.RED)
            )
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.player.run {
            if (!isAuthenticated()) {
                loadInventory(this)
            }
        }
    }

    @EventHandler
    fun preventPlayerMove(event: PlayerMoveEvent) {
        event.player.run {
            if (isAuthenticated()) return
            val location = loadSavedPlayerLocation("original", event.player)
            if (location?.x != event.to?.x ||
                location?.y != event.to?.y ||
                location?.z != event.to?.z
            ) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun preventBlockBreak(event: BlockBreakEvent) {
        if (!event.player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventDropItem(event: PlayerDropItemEvent) {
        if (!event.player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventDamage(event: EntityDamageEvent) {
        if (event.entityType == EntityType.PLAYER) {
            val player = event.entity as Player
            if (!player.isAuthenticated()) event.isCancelled = true
        }
    }

    @EventHandler
    fun preventAttack(event: EntityDamageByEntityEvent) {
        event.damager.let {
            if (it !is Player) return
            if (!it.isAuthenticated()) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun preventDrop(event: PlayerDropItemEvent) {
        if (!event.player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventReceive(event: EntityPickupItemEvent) {
        if (event.entityType != EntityType.PLAYER) return
        val player = event.entity as Player
        if (!player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventTalk(event: AsyncPlayerChatEvent) {
        if (!event.player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun openLoginPage(event: PlayerInteractEvent) {
        event.player.run {
            if (isAuthenticated()) return
            event.isCancelled = true
            if (event.action === Action.RIGHT_CLICK_BLOCK || event.action === Action.RIGHT_CLICK_AIR) {
                when (inventory.itemInMainHand) {
                    loginEntry -> showLoginPage(this)
                    registryEntry -> showRegisterPage(this)
                }
            }
        }
    }

    @EventHandler
    fun preventPlaceBlock(event: BlockPlaceEvent) {
        if (!event.player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventCommand(event: PlayerCommandPreprocessEvent) {
        if (!event.player.isAuthenticated() && event.message.split(" ").first() != "/auth") {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventBeingTargeted(event: EntityTargetLivingEntityEvent) {
        if (event.target !is Player) return

        val player = event.target as Player
        if (!player.isAuthenticated()) event.isCancelled = true
    }

    @EventHandler
    fun preventPlayerMount(event: EntityMountEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player
        if (!player.isAuthenticated()) event.isCancelled = true
    }
}
