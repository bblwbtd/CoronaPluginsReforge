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
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack
import pages.LoginPage
import utils.getDouble
import utils.getString
import utils.isAuthenticated
import utils.setDouble
import viewRender.MagicViewOptions
import viewRender.setName
import viewRender.setString

class PlayerListener : Listener {

    val authHandler = AuthHandler()

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
    fun preventPlayerMove(event: PlayerMoveEvent) {
        event.player.run {
            if (getString(Main.plugin, "state")?.equals(PlayerState.AUTHENTICATED.name) == true) return

            if (getDouble(Main.plugin, "x") != location.x ||
                getDouble(Main.plugin, "y") != location.y ||
                getDouble(Main.plugin, "z") != location.z
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
            if (!isAuthenticated()) return

            when (inventory.itemInMainHand) {
                loginEntry -> LoginPage(MagicViewOptions(name = getText("Login Page", this.locale)))
                registryEntry -> TODO()
            }

            event.isCancelled = true
        }
    }
}