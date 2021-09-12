import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class BaseTest {
    var server: ServerMock? = null
    var plugin: MagicViewPlugin? = null

    @BeforeTest
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(MagicViewPlugin::class.java)
    }

    @AfterTest
    fun shutDown() {
        MockBukkit.unmock()
    }
}