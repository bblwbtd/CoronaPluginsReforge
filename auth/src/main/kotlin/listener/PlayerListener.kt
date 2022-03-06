package listener

import Main
import handler.AuthHandler
import handler.PlayerState
import i18n.color
import i18n.getText
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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
import utils.getDouble
import utils.isAuthenticated
import utils.setDouble
import viewRender.setName
import viewRender.setString

class PlayerListener : Listener {

    private val authHandler = AuthHandler()

    private val loginEntry = ItemStack(Material.EMERALD_BLOCK).apply {
        setString("type", "login")
    }

    private val registryEntry = ItemStack(Material.REDSTONE_BLOCK).apply {
        setString("type", "register")
    }

    private fun savePlayerLocation(player: Player) {
        player.apply {
            setDouble(Main.plugin, "x", location.x)
            setDouble(Main.plugin, "y", location.y)
            setDouble(Main.plugin, "z", location.z)
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.run {
            saveData()
            PlayerState.UNAUTHENTICATED.setState(this)
            savePlayerLocation(this)

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
                loadData()
            }
        }
    }

    @EventHandler
    fun preventPlayerMove(event: PlayerMoveEvent) {
        event.player.run {
            if (isAuthenticated()) return

            if (getDouble(Main.plugin, "x") != event.to?.x ||
                getDouble(Main.plugin, "y") != event.to?.y ||
                getDouble(Main.plugin, "z") != event.to?.z
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

            when (inventory.itemInMainHand) {
                loginEntry -> showLoginPage(this)
                registryEntry -> showRegisterPage(this)
            }

            event.isCancelled = true
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