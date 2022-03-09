package pages

import handler.LocationHandler
import viewRender.MagicLayout
import viewRender.MagicView

class ListDetailPage : MagicView() {
    override fun getLayout(): MagicLayout {
        val player = inventory.viewers.first()
        val book = LocationHandler(player).getUserAddressBook()

        return MagicLayout()
    }
}