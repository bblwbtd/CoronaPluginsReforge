package xyz.ldgame.corona.common.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor

operator fun ChatColor.plus(s: String): String {
    return this.toString() + s
}

fun info(message: String) {
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + message)
}

fun warn(message: String) {
    Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + message)
}

fun error(message: String) {
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + message)
}
