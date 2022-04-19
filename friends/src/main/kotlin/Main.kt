import command.CommandCompleter
import commands.Executor

class Main : CommonMain() {
    override fun onEnable() {
        super.onEnable()

        getCommand("friend")!!.apply {
            Executor().let {
                setExecutor(it)
                tabCompleter = CommandCompleter(it.getCommand())
            }
        }

        registerListeners(RelationListener())

    }

}