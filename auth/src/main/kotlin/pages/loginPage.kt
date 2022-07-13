package pages

import Main
import handler.AuthHandler
import handler.InvalidPasswordException
import handler.NoUserException
import i18n.locale
import listener.PlayerAuthEvent
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


fun showLoginPage(p: Player) {
    AnvilGUI.Builder().onComplete { player, text ->
        try {
            AuthHandler().login(p.name, text.trim())
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
            AnvilGUI.Response.close()
        } catch (e: NoUserException) {
            AnvilGUI.Response.text("You need to register first.".locale(player))
        } catch (e: InvalidPasswordException) {
            AnvilGUI.Response.text("Wrong password".locale(player))
        }
    }.title("Login Page".locale(p))
        .itemLeft(ItemStack(Material.BOOK))
        .text(" ")
        .plugin(Main.plugin).open(p)
}
