package database

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.ktorm.database.asIterable
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConnectionKtTest {

    @Test
    fun testConnectDB() {
        val plugin =
            mock<Plugin> { on { dataFolder } doReturn createTempDirectory().toFile(); on { config } doReturn YamlConfiguration() }
        val db = plugin.connectDB()

        db.useConnection { connection ->
            connection.prepareStatement("select 1 + 1").use {
                it.executeQuery().asIterable().first().getInt(1).apply {
                    assertEquals(2, this)
                }
            }
        }

    }
}