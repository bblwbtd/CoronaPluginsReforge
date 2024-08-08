package xyz.ldgame.corona.friends

import xyz.ldgame.corona.common.command.CommandCompleter
import xyz.ldgame.corona.friends.commands.Executor
import xyz.ldgame.corona.common.CommonMain

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