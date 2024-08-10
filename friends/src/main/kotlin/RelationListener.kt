package xyz.ldgame.corona.friends

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate

class RelationListener : Listener {

    @EventHandler
    fun onMakeFriend(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player || event.damager !is Player) return
        val player1 = event.entity as Player
        val player2 = event.damager as Player
        if (!isLoving(player1) || !isLoving(player2)) return

        if (RelationHandler(player1).addFriend(player2) &&
            RelationHandler(player2).addFriend(player1)
        ) {
            spawnSuccessParticle(player1)
            spawnSuccessParticle(player2)

            "${player2.name} ${"became your friend".translate(player1)}!".color(ChatColor.GREEN).send(player1)
            "${player1.name} ${"became your friend".translate(player2)}!".color(ChatColor.GREEN).send(player2)
        }
    }
}