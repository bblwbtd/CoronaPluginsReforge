package handlers

import org.bukkit.entity.Player

data class FriendMessage(val from: Player, val onAccept: () -> Unit = {}, val onDeclined: () -> Unit = {})