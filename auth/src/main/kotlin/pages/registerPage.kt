package pages

import Main
import handler.AuthHandler
import handler.DuplicatedUserException
import handler.InvalidPasswordException
import handler.PlayerState
import i18n.color
import i18n.locale
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun showRegisterPage(p: Player) {
    AnvilGUI.Builder().onComplete { player, text ->
        try {
            AuthHandler().register(p.name, text.trim())
            player.loadData()
            PlayerState.AUTHENTICATED.setState(player)
            player.sendMessage("Register successfully!".locale(player).color(ChatColor.GREEN))
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