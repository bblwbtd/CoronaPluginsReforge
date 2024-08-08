package xyz.ldgame.corona.auth.listener

import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send
import org.bukkit.Bukkit
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
import xyz.ldgame.corona.auth.Main
import xyz.ldgame.corona.common.utils.*
import xyz.ldgame.corona.auth.exceptions.DuplicatedRegisterException
import xyz.ldgame.corona.auth.exceptions.InvalidPasswordException
import xyz.ldgame.corona.auth.exceptions.NoUserException
import xyz.ldgame.corona.auth.handler.*
import xyz.ldgame.corona.auth.utils.isAuthenticated

class PlayerListener : Listener {

    private val authHandler = AuthHandler()
    private val loginBook = ItemStack(Material.WRITABLE_BOOK, 1)

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.run {
            val playerList = Bukkit.getOnlinePlayers().filter {
                it.name != name
            }
            for (player in playerList) {
                player.hidePlayer(Main.plugin, this)
                hidePlayer(Main.plugin, player)
            }
            setString(Main.plugin, "join_message", event.joinMessage ?: "")
            event.joinMessage = ""
            var countDown = Main.plugin.config.getInt("timeout")
            Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plugin, { task ->
                if (countDown > 0) {
                    countDown -= 1
                } else {
                    task.cancel()
                    if (!isAuthenticated()) {
                        Bukkit.getScheduler().runTask(Main.plugin, Runnable {
                            kickPlayer("Login Timeout.".locale(this))
                        })
                    }
                }
            }, 0, 20L)


            PlayerState.UNAUTHENTICATED.setState(this)
            Bukkit.getScheduler().runTask(Main.plugin) { _ ->
                if (loadLocation("original") == null) {
                    saveLocation("original")
                }
                vehicle?.eject()
                safeRandomTP(7.0)
                saveLocation("current")
                if (getStorageFile(name).readText().isEmpty()) {
                    saveInventory(this)
                }
                inventory.clear()
                inventory.addItem(loginBook)
            }

            "You need to login or register at first!".locale(this).color(ChatColor.YELLOW).send(this)
            "You can login/register by inputting your password to a book!".locale(this)
                .color(ChatColor.AQUA).send(this)
            "You can also use /auth r YOUR_PASSWORD to register.".locale(this).send(this)
            "Use /auth l YOUR_PASSWORD to login.".locale(this).send(this)
        }
    }

    @EventHandler
    fun onPlayerIsKicked(event: PlayerKickEvent) {
        if (!event.player.isAuthenticated()) {
            event.leaveMessage = ""
        }
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        if (!event.player.isAuthenticated()) {
            event.quitMessage = ""
        }
    }

    @EventHandler
    fun onPlayerAuth(event: PlayerAuthEvent) {
        event.player.apply {
            Bukkit.getScheduler().runTask(Main.plugin, Runnable {
                val location = retrieveLocation("original")
                teleport(location!!)
                loadInventory(this)
                PlayerState.AUTHENTICATED.setState(this)
                "Login successfully!".locale(this).color(ChatColor.GREEN).send(this)
                retrieveLocation("current")
                retrieveLocation("original")

                val playerList = Bukkit.getOnlinePlayers().filter {
                    it.name != name
                }
                for (player in playerList) {
                    player.showPlayer(Main.plugin, this)
                    showPlayer(Main.plugin, player)
                }
                Bukkit.broadcastMessage(getString(Main.plugin, "join_message") ?: "")
            })
        }
    }

    @EventHandler
    fun preventPlayerMove(event: PlayerMoveEvent) {
        event.player.run {
            if (isAuthenticated()) return
            val location = loadLocation("current")
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
            if (!isAuthenticated()) event.isCancelled = true
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
    fun playerFinishInput(event: PlayerEditBookEvent) {
        if (event.player.isAuthenticated()) return
        val password = event.newBookMeta.pages.joinToString("")
        if (authHandler.hasRegistered(event.player.name)) {
            try {
                authHandler.login(event.player.name, password)
                Bukkit.getPluginManager().callEvent(PlayerAuthEvent(event.player))
            } catch (e: NoUserException) {
                "You need to register first.".locale(event.player).color(ChatColor.RED).send(event.player)
            } catch (e: InvalidPasswordException) {
                "Wrong password".locale(event.player).color(ChatColor.RED).send(event.player)
            }
        } else {
            try {
                authHandler.register(event.player.name, password)
                Bukkit.getPluginManager().callEvent(PlayerAuthEvent(event.player))
            } catch (e: DuplicatedRegisterException) {
                "You have already registered.".locale(event.player).color(ChatColor.RED).send(event.player)
            } catch (e: InvalidPasswordException) {
                "Invalid password".locale(event.player).color(ChatColor.RED).send(event.player)
            }
        }
    }

    @EventHandler
    fun preventLoginFromAnotherLocation(event: AsyncPlayerPreLoginEvent) {
        val player = Bukkit.getServer().getPlayer(event.name) ?: return
        if (!player.isOnline || !player.isAuthenticated()) return

        event.disallow(
            AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
            "Your are already online. If you think this is a mistake, please contact the server administrator".locale(
                player
            )
        )
    }
}
