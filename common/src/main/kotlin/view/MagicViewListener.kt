package xyz.ldgame.corona.common.view

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.ldgame.corona.common.view.MagicView

class MagicViewListener : Listener {

    @EventHandler
    fun handleClick(event: InventoryClickEvent) {
        val holder = event.inventory.holder
        if (holder !is MagicView) return

        holder.click(event)
    }
}