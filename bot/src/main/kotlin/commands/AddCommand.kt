package xyz.ldgame.bot.commands

import command.MagicCommand

class AddCommand: MagicCommand(help = "AddCommandHelp") {
    override fun run() {
        println("Add command")
    }
}