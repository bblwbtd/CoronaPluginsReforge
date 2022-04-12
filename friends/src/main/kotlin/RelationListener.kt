import handlers.RelationHandler
import handlers.isLoving
import handlers.spawnSuccessParticle
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class RelationListener : Listener {

    @EventHandler
    fun onMakeFriend(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player || event.damager !is Player) return
        val player1 = event.entity as Player
        val player2 = event.damager as Player
        if (!isLoving(player1) || !isLoving(player2)) return

        RelationHandler(player1).addFriend(player2)
        RelationHandler(player2).addFriend(player1)

        spawnSuccessParticle(player1)
        spawnSuccessParticle(player2)

        "${player2.name} ${"became your friend".locale(player1)}!".color(ChatColor.GREEN).send(player1)
        "${player1.name} ${"became your friend".locale(player2)}!".color(ChatColor.GREEN).send(player2)
    }
}