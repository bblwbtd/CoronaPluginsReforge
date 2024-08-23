package xyz.ldgame.corona.bot

import com.fasterxml.jackson.core.type.TypeReference
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.utils.mapper
import java.io.FileNotFoundException
import java.util.*

data class BotRecord(val bots: MutableList<Bot> = LinkedList())

val recordCache: MutableMap<String, BotRecord> by lazy {
    val file = CommonMain.plugin.dataFolder.resolve("bots.json")
    try {
        mapper.readValue(file, object : TypeReference<MutableMap<String, BotRecord>>() {})
    } catch (e: FileNotFoundException) {
        file.createNewFile().also {
            mapper.writeValue(file, mutableMapOf<String, BotRecord>())
        }
        mutableMapOf()
    }
}

fun saveRecord() {
    Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin, Runnable {
        val file = CommonMain.plugin.dataFolder.resolve("bots.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        mapper.writeValue(file, recordCache)
    })
}

fun addBot(player: Player, botName: String) {
    for ((key, value) in recordCache.entries) {
        if (value.bots.any { it.name == botName }) {
            throw IllegalArgumentException(key)
        }
    }

    val record = recordCache[player.name] ?: BotRecord().also { recordCache[player.name] = it }
    record.bots.add(Bot(botName))

    saveRecord()
}

fun removeBot(player: Player, botName: String) {
    val record = recordCache[player.name] ?: return
    if (!record.bots.removeIf { it.name == botName }) {
        throw IllegalArgumentException()
    }

    Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin, Runnable {
        val uid = UUID.nameUUIDFromBytes("OfflinePlayer:$botName".toByteArray())
        Bukkit.getPlayer(botName)?.kickPlayer("Bye")
        Bukkit.getWorlds().forEach {
            it.worldFolder.resolve("playerdata").resolve("$uid.dat").delete()
        }
    })

    saveRecord()
}

fun listBot(player: Player): List<Bot> {
    val record = recordCache[player.name] ?: return emptyList()
    return record.bots
}

fun getOnlineBot(player: Player): List<Bot> {
    return listBot(player).filter {
        Bukkit.getOnlinePlayers().find { p -> p.name == it.name } != null
    }
}
