package handler

import Main
import entities.User
import i18n.getText
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import utils.getString
import utils.setString
import java.io.File
import java.nio.file.Paths
import java.security.MessageDigest

fun fetchUser(username: String): User? {
    val userDir = File(Paths.get(Main.plugin.dataFolder.path, "users", "${username}.json").toUri())
    if (!userDir.exists()) {
        userDir.mkdir()
        return null
    }

    return null
}

fun login(username: String, password: String) {

}

fun register(username: String, password: String) {
    if (password.length < 6) {
        throw InvalidPasswordException()
    }


}

fun digestPassword(password: String): String {
    return MessageDigest.getInstance("MD5").run {
        val salt = Main.plugin.config.getString("salt", "CoronaAuth")
        update(password.plus(salt).toByteArray())
        digest().toString()
    }
}

fun hasRegistered(username: String): Boolean {
    return false
}

fun Player.isAuthenticated(): Boolean {
    return getString(Main.plugin, "state") == PlayerState.AUTHENTICATED.name
}
