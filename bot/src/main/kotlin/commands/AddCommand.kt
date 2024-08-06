package xyz.ldgame.bot.commands

import command.MagicCommand

class AddCommand: MagicCommand(help = "Add a new bot") {
    override fun run() {
        println("Add command")
    }
}