package handlers

import CommonMain
import GameListener
import i18n.broadcast
import i18n.color
import i18n.locale
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

var isPlaying = false

var timer: BukkitTask? = null

fun startGame() {
    isPlaying = true

    timer = Bukkit.getScheduler().runTaskTimer(CommonMain.plugin, Runnable {
        if (!isPlaying) {
            timer?.cancel()
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
    }, 0, 20L)

    "Kyaru's game is started.".locale().color(ChatColor.GREEN).broadcast()
}

fun endGame() {
    isPlaying = false
    "Kyaru's game is stopped.".locale().color(ChatColor.RED).broadcast()
}