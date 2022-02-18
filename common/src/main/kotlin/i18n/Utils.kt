import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.*

fun getText(rawText: String, local: String? = "en_us"): String {
    return rawText + local
}

fun getText(rawText: String, sender: CommandSender? = null): String {
    if (sender is Player) {
        return getText(rawText, sender.locale)
    }

    if (sender is ConsoleCommandSender) {
        val currentLocal = Locale.getDefault()
        return getText(rawText, "${currentLocal.language}_${currentLocal.country}")
    }

    return getText(rawText, local = null)
}