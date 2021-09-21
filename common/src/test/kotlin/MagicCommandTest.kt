import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.mockito.kotlin.doReturn

import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertTrue


class MagicCommandTest {

    class TestCommand : MagicCommand(help = "233") {

        val name by option().required()

        override fun run() {
            println("${sender?.name}:${name}")
        }
    }

    class TestSubCommand : MagicCommand(help = "666") {
        override fun run() {
            println("test1")
        }
    }


    @Test
    fun testCommand() {
        val command = TestCommand().subcommands(TestSubCommand())

        val mockSender = mock<CommandSender> { on { name } doReturn "233"; }
        val mockCommand = mock<Command> {}

        assertTrue {
            command.onCommand(mockSender, mockCommand, "test", arrayOf("--help"))
        }
    }


}