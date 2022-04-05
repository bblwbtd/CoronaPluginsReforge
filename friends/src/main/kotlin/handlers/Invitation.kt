package handlers

import org.bukkit.entity.Player

data class Invitation(val from: Player, val onAccept: () -> Unit = {}, val onDeclined: () -> Unit = {})