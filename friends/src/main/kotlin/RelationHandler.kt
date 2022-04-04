import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.nio.file.Path
import java.nio.file.Paths


class RelationHandler(
    private val player: Player,
    path: Path = Paths.get(CommonMain.plugin.dataFolder.path, "records", "${player.name}.yml")
) {

    private val recordFile = path.toFile()
    private val record = YamlConfiguration().apply {
        load(recordFile)
    }

    private fun save() {
        Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin) { _ ->
            record.save(recordFile)
        }
    }

    fun getFriends(): MutableList<Friend> {
        return record.getMapList("friends").map {
            Friend(it["name"] as String, it["createAt"] as Long)
        }.toMutableList()
    }

    private fun addFriend(player: Player) {
        if (player.name == this.player.name) {
            "You can not make friend with yourself".color(ChatColor.RED).send(player)
            return
        }
        val friends = getFriends()
        if (friends.find { friend -> friend.name === player.name } != null) {
            "${player.name} is already your friend.".send(player)
            return
        }
        val newFriend = Friend(player.name, System.currentTimeMillis())
        friends.add(newFriend)
        record.set("friends", friends)
        save()
    }

    fun removeFriend(player: Player) {
        val friends = getFriends()
        friends.removeIf { friend -> friend.name == player.name }
        record.set("friends", friends)
        save()
    }

    fun sendInvitation(name: String) {
        val target = Bukkit.getPlayer(name)

        if (target == null) {
            "Can not find the player".locale(player).plus(" $name.").color(ChatColor.RED).send(player)
            return
        }

        sendRequest(player, target)
        "Invitation sent.".locale(player).color(ChatColor.GREEN).send(player)
    }

    fun acceptInvitation(from: Player?) {
        val invitation = from ?: popNextRequest(player)
        if (invitation == null) {
            "No invitation found.".locale(player).color(ChatColor.RED).send(player)
            return
        }
        addFriend(invitation)
    }

    fun declineInvitation(from: Player?) {
        var invitation = from
        if (invitation == null) {
            invitation = popNextRequest(player)
            if (invitation == null) {
                "No invitation found.".locale(player).color(ChatColor.RED).send(player)
            }
            return
        }
        removeRequest(invitation, player)
        "Invitation declined.".locale(player).color(ChatColor.RED).send(player, invitation)
    }
}