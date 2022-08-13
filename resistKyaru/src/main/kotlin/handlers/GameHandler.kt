package handlers

import CommonMain
import GameListener
import i18n.broadcast
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

var isPlaying = false

var effectTimer: BukkitTask? = null
var finishTimer: BukkitTask? = null

fun startGame(sender: CommandSender?) {
    if (isPlaying) {
        "Don't start the game again!".locale(sender).color(ChatColor.RED).send(sender)
    }

    isPlaying = true

    effectTimer = Bukkit.getScheduler().runTaskTimer(CommonMain.plugin, Runnable {
        if (!isPlaying) {
            effectTimer?.cancel()
            return@Runnable
        }

        Bukkit.getOnlinePlayers().forEach {
            if (it.inventory.helmet != GameListener.kyaruSkull) return@forEach
            it.addPotionEffects(
                listOf(
                    PotionEffect(PotionEffectType.SPEED, 200, 0),
                    PotionEffect(PotionEffectType.HUNGER, 200, 1, true, true)
                )
            )
        }
    }, 0, 200L)

    finishTimer = Bukkit.getScheduler().runTaskTimer(CommonMain.plugin, Runnable {
        if (!isPlaying) {
            finishTimer?.cancel()
            return@Runnable
        }

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.inventory.helmet != GameListener.kyaruSkull) return@Runnable
        }

        Bukkit.getOnlinePlayers().forEach {
            "Each player has a kyaru skull now!".locale(it).color(ChatColor.GREEN).send(it)
        }

        finishTimer?.cancel()
    }, 0, 60L)


    "Kyaru's game is started.".locale().color(ChatColor.GREEN).broadcast()
}

fun endGame() {
    isPlaying = false
    "Kyaru's game is stopped.".locale().color(ChatColor.RED).broadcast()
}