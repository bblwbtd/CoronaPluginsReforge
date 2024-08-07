package xyz.ldgame.corona.auth.handler


import xyz.ldgame.corona.auth.Main
import xyz.ldgame.corona.common.utils.mapper
import xyz.ldgame.corona.common.utils.md5
import xyz.ldgame.corona.auth.entities.User
import xyz.ldgame.corona.auth.exceptions.DuplicatedRegisterException
import xyz.ldgame.corona.auth.exceptions.InvalidPasswordException
import xyz.ldgame.corona.auth.exceptions.NoUserException
import java.io.File
import java.nio.file.Paths


class AuthHandler(private val userDir: String = Paths.get(Main.plugin.dataFolder.path, "users").toString()) {
    fun fetchUserFile(username: String): File {
        return File(Paths.get(userDir, "${username}.json").toUri())
    }

    fun fetchUser(username: String): User? {
        val file = fetchUserFile(username)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
            return null
        }

        if (!file.exists()) {
            return null
        }

        return mapper.readValue(file, User::class.java)
    }

    fun login(username: String, password: String) {
        val user = fetchUser(username) ?: throw NoUserException()
        val passwordHash = md5(password)
        if (user.password != passwordHash) throw InvalidPasswordException()
    }

    fun register(username: String, password: String): User {
        validatePassword(password)

        if (hasRegistered(username)) {
            throw DuplicatedRegisterException()
        }

        val user = User(username, md5(password))
        val file = fetchUserFile(username).apply { createNewFile() }

        mapper.writeValue(file, user)
        return user
    }

    fun update(username: String, password: String) {
        validatePassword(password)

        val file = fetchUserFile(username)

        mapper.writeValue(file, User(username, md5(password)))
    }

    fun validatePassword(password: String) {
        if (password.length < 6) {
            throw InvalidPasswordException()
        }
    }

    fun hasRegistered(username: String): Boolean {
        return fetchUser(username) != null
    }
}



