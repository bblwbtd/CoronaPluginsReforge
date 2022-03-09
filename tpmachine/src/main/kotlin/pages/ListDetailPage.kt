package pages

import handler.LocationHandler
import org.bukkit.entity.Player
import viewRender.MagicLayout
import viewRender.MagicView

class ListDetailPage : MagicView() {
    override fun getLayout(): MagicLayout {
        val player = inventory.viewers.first() as Player
        val book = LocationHandler(player).getUserAddressBook()

        return MagicLayout()
    }
}