package utils

fun getKeyList(): List<String> {
    return Main.plugin.config.getStringList("keys")
}
