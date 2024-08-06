package xyz.ldgame.bot.commands

import command.MagicCommand

class ListCommand: MagicCommand(help = "ListCommandHelp") {
    override fun run() {
        println("List command")
    }
}