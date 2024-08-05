package xyz.ldgame.coronaauth.handler

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.ldgame.coronaauth.Main
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.notExists

fun getStorageFile(name: String): File {
    val path = Paths.get(Main.plugin.dataFolder.path, "storage", "${name}.yaml")
    path.parent.createDirectories()
    if (path.notExists()) {
        path.createFile()
    }
    return path.toFile()
}

fun saveInventory(player: Player) {
    val config = YamlConfiguration()
    config.set("items", player.inventory.contents)
    config.save(getStorageFile(player.name))
}

fun loadInventory(player: Player) {
    val f = getStorageFile(player.name)

    @Suppress("UNCHECKED_CAST")
    val contents = YamlConfiguration.loadConfiguration(f).getList("items") as List<ItemStack>
    player.inventory.contents = contents.toTypedArray()
    player.updateInventory()
    f.delete()
}