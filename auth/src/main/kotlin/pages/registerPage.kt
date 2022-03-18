package pages

import Main
import handler.AuthHandler
import handler.DuplicatedUserException
import handler.InvalidPasswordException
import i18n.locale
import listener.PlayerAuthEvent
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun showRegisterPage(p: Player) {
    AnvilGUI.Builder().onComplete { player, text ->
        try {
            AuthHandler().register(p.name, text.trim())
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
            AnvilGUI.Response.close()
        } catch (e: DuplicatedUserException) {
            AnvilGUI.Response.text("You have already registered.".locale(player))
        } catch (e: InvalidPasswordException) {
            AnvilGUI.Response.text("Wrong password".locale(player))
        }
    }.title("Register Page".locale(p))
        .itemLeft(ItemStack(Material.BOOK))
        .text(" ")
        .plugin(Main.plugin).open(p)
}
