package handler

import Main
import com.fasterxml.jackson.module.kotlin.readValue
import entities.User
import entities.mapper
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
            file.mkdirs()
            return null
        }

        if (!file.exists()) {
            return null
        }

        return mapper.readValue<User>(file)
    }

    fun login(username: String, password: String) {
        val user = fetchUser(username) ?: throw NoUserException()
        val passwordHash = md5(password)
        if (user.password != passwordHash) throw InvalidPasswordException()
    }

    fun register(username: String, password: String): User {
        if (password.length < 6) {
            throw InvalidPasswordException()
        }

        if (hasRegistered(username)) {
            throw DuplicatedUserException()
        }

        val user = User(username, md5(password))
        val file = fetchUserFile(username).apply { createNewFile() }

        mapper.writeValue(file, user)
        return user
    }

    fun hasRegistered(username: String): Boolean {
        return fetchUser(username) != null
    }
}



