package handler

import exceptions.DuplicatedRegisterException
import exceptions.InvalidPasswordException
import exceptions.NoUserException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.io.path.createTempDirectory

internal class AuthHandlerTest {

    fun getHandler(): AuthHandler {
        return AuthHandler(createTempDirectory().toAbsolutePath().toString())
    }

    @Test
    fun fetchUser_existed() {
        val handler = getHandler()

        handler.register("test1", "1234567")
        assertNotNull(handler.fetchUser("test1"))
    }

    @Test
    fun fetchUser_not_existed() {
        val handler = getHandler()
        assertNull(handler.fetchUser("test2"))
    }

    @Test
    fun login() {
        val handler = getHandler()
        assertThrows<NoUserException> {
            handler.login("test3", "123456")
        }

        assertThrows<InvalidPasswordException> { handler.login("test3", "2213123123") }

        assertDoesNotThrow {
            handler.login("test3", "1234567")
        }
    }

    @Test
    fun register() {
        val handler = getHandler()
        assertThrows<InvalidPasswordException> {
            handler.register("test4", "12345")
        }

        handler.register("test4", "1234567")

        assertThrows<DuplicatedRegisterException> { handler.register("test4", "1234567") }

        assertTrue(handler.fetchUserFile("test4").exists())
    }

    @Test
    fun hasRegistered() {
        val handler = getHandler()
        assertFalse(handler.hasRegistered("test5"))
        handler.register("test5", "1234523424")
        assertTrue(handler.hasRegistered("test5"))
    }
}