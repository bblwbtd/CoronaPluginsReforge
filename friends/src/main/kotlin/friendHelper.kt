package xyz.ldgame.corona.friends

import CommonMain
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.utils.getString
import xyz.ldgame.corona.common.utils.setString

fun toggleState(player: Player) {
    if (!isLoving(player)) {
        var countdown = CommonMain.plugin.config.getInt("love_timout")
        Bukkit.getScheduler().runTaskTimer(CommonMain.plugin, { task ->
            if (!isLoving(player) || countdown <= 0) {
                task.cancel()
                "Stop making friends.".locale(player).color(ChatColor.YELLOW).send(player)
                player.setString(CommonMain.plugin, "loving", "false")
                return@runTaskTimer
            }

            countdown -= 1
            player.world.spawnParticle(Particle.HEART, player.location.add(0.0, 2.0, 0.0), 1)
        }, 0, 20L)
        "You can make friends with other players by clicking on them.".locale(player).color(ChatColor.GREEN)
            .send(player)
        player.setString(CommonMain.plugin, "loving", "true")
    } else {
        player.setString(CommonMain.plugin, "loving", "false")
    }
}

fun spawnSuccessParticle(player: Player) {
    var count = 0
    Bukkit.getScheduler().runTaskTimer(CommonMain.plugin, { task ->
        if (count >= 4) task.cancel()
        player.world.spawnParticle(Particle.HEART, player.location.add(0.0, 0.5, 0.0), 20, 0.5, 0.5, 0.5)
        count += 1
    }, 0, 20L)
}

fun isLoving(player: Player): Boolean {
    return player.getString(CommonMain.plugin, "loving") == "true"
}