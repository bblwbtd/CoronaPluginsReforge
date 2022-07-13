package handler

import Main
import entities.User
import exceptions.DuplicatedUserException
import exceptions.InvalidPasswordException
import exceptions.NoUserException
import utils.mapper
import utils.md5
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
            throw DuplicatedUserException()
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



