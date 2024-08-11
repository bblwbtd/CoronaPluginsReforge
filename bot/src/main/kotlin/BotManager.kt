package xyz.ldgame.corona.bot

import com.fasterxml.jackson.core.type.TypeReference
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.i18n.MagicString
import xyz.ldgame.corona.common.utils.mapper
import java.nio.file.NoSuchFileException
import java.util.*

data class BotRecord(val bots: MutableList<Bot> = LinkedList())

val recordCache: MutableMap<String, BotRecord> by lazy {
    try {
        val file = CommonMain.plugin.dataFolder.resolve("bots.json")
        mapper.readValue(file, object : TypeReference<MutableMap<String, BotRecord>>() {})
    } catch (e: NoSuchFileException) {
        mutableMapOf()
    }
}

fun saveRecord() {
    Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin, Runnable {
        val file = CommonMain.plugin.dataFolder.resolve("bots.json")
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
        MagicString("BotNotFound").send(player)
    }

    Bukkit.getScheduler().runTaskAsynchronously(CommonMain.plugin, Runnable {
        val uid = UUID.fromString("OfflinePlayer:$botName").toString()
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

