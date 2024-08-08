package xyz.ldgame.corona.bot.commands

import xyz.ldgame.corona.common.command.MagicCommand

class ListCommand: MagicCommand(help = "ListCommandHelp") {
    override fun run() {
        println("List command")
    }
}