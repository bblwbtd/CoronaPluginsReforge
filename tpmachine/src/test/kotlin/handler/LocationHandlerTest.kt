package handler

import org.junit.jupiter.api.Test
import kotlin.io.path.createTempDirectory
import kotlin.io.path.pathString

internal class LocationHandlerTest {

    val handler = LocationHandler(createTempDirectory().pathString)

    @Test
    fun getUserAddressBook() {

    }

    @Test
    fun savePlayerLocation() {
    }
}