package xyz.ldgame.corona.bot.commands

import xyz.ldgame.corona.common.command.MagicCommand

class AddCommand: MagicCommand(help = "AddCommandHelp") {
    override fun run() {
        println("Add command")
    }
}