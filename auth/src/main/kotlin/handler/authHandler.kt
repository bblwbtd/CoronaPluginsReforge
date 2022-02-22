package handler

import Main
import database.db
import database.entities.User
import database.users
import i18n.getText
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import utils.getString
import utils.setString
import java.security.MessageDigest


fun login(username: String, password: String) {
    Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Runnable {
        if (password.isEmpty()) return@Runnable
        val player = Bukkit.getPlayer(username) ?: return@Runnable
        val user = db.users.find { it.username eq username } ?: return@Runnable
        val hash = digestPassword(password)

        if (user.password == hash) {
            player.loadData()
            player.setString(Main.plugin, "state", PlayerState.AUTHENTICATED.name)
        } else {
            player.sendMessage(getText("Password doesn't match", player.locale))
        }
    })
}

fun register(username: String, password: String) {
    if (password.length < 6) {
        throw InvalidPasswordException()
    }

    Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Runnable {
        db.users.add(User {
            this.username = username
            this.password = digestPassword(password)
        })
    })
}

fun digestPassword(password: String): String {
    return MessageDigest.getInstance("MD5").run {
        val salt = Main.plugin.config.getString("salt", "CoronaAuth")
        update(password.plus(salt).toByteArray())
        digest().toString()
    }
}

fun hasRegistered(username: String): Boolean {
    return db.users.find { it.username eq username } != null
}

fun Player.isAuthenticated(): Boolean {
    return getString(Main.plugin, "state") == PlayerState.AUTHENTICATED.name
}