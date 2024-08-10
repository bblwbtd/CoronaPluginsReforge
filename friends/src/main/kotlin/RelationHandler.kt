package xyz.ldgame.corona.friends

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.common.utils.mapper
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString


class RelationHandler(
    private val player: Player,
    private val recordDir: Path = Paths.get(CommonMain.plugin.dataFolder.path, "records")
) {

    private val record = getRecord(player.name)

    private fun getFile(playerName: String): File {
        recordDir.toFile().mkdirs()
        return Paths.get(recordDir.pathString, "${playerName}.json").toFile()
    }

    private fun getRecord(playerName: String): FriendRecord {
        val file = getFile(playerName)
        if (!file.exists()) {
            return FriendRecord()
        }

        return mapper.readValue(getFile(playerName), FriendRecord::class.java)
    }

    private fun save() {
        Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin) { _ ->
            mapper.writeValue(getFile(player.name), record)
        }
    }

    fun getFriends(): MutableList<Friend> {
        return record.friends
    }

    fun addFriend(friend: Player): Boolean {
        if (friend.name == this.player.name) {
            "You can not make friend with yourself".color(ChatColor.RED).send(friend)
            return false
        }
        val friends = getFriends()
        if (friends.find { f -> f.name == friend.name } != null) {
            "You are already friend.".send(friend)
            return false
        }
        val newFriend = Friend(friend.name, System.currentTimeMillis())
        record.friends.add(newFriend)
        save()

        spawnSuccessParticle(player)
        "You get a new friend:".translate(player).plus(" ${friend.name}").color(ChatColor.GREEN).send(player)
        return true
    }

    fun removeFriend(player: Player) {
        val friends = getFriends()
        friends.removeIf { friend -> friend.name == player.name }
        save()
    }
}