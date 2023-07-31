import command.CommandCompleter
import commands.Executor

class Main: CommonMain() {

    override fun onEnable() {
        super.onEnable()

        getCommand("kyaru")!!.apply {
            Executor().let {
                setExecutor(it)
                tabCompleter = CommandCompleter(it.getCommand())
            }
        }

        server.pluginManager.run {
            registerEvents(GameListener(), plugin)
        }
    }
}