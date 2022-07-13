import command.CommandCompleter
import commands.Executor
import listener.KeyListListener

class Main: CommonMain() {

    override fun onEnable() {
        super.onEnable()
        getCommand("box")!!.apply {
            Executor().let {
                setExecutor(it)
                tabCompleter = CommandCompleter(it.getCommand())
            }
        }

        registerListeners()
    }

    private fun registerListeners() {
        server.pluginManager.run {
            registerEvents(KeyListListener(), plugin)
        }
    }


}