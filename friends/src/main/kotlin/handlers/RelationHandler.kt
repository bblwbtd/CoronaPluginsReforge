package handlers

import CommonMain
import Friend
import i18n.color
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
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
        return Paths.get(recordDir.pathString, "${playerName}.yml").toFile().apply {
            if (!exists()) createNewFile()
        }
    }

    private fun getRecord(playerName: String): YamlConfiguration {
        return YamlConfiguration().apply {
            load(getFile(playerName))
        }
    }

    private fun save() {
        Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin) { _ ->
            record.save(getFile(player.name))
        }
    }

    fun getFriends(): MutableList<Friend> {
        return record.getMapList("friends").map {
            Friend(it["name"] as String, it["createAt"] as Long)
        }.toMutableList()
    }

    fun addFriend(player: Player) {
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
}