package utils


import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.persistence.PersistentDataType
import java.io.ByteArrayOutputStream
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher
import java.util.Base64

fun getKeyList(): List<String> {
    return Main.plugin.config.getStringList("keys")
}

fun entropyByPublicKey(x : Int, y : Int, z : Int, publicKey: PublicKey) : String {
    val ENCRYPT_MAX_SIZE=117
    val input = "$x,$y,$z"
    val byteArray = input.toByteArray()
    var temp: ByteArray?
    var offset = 0
    val cipher= Cipher.getInstance("RSA")
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    val os= ByteArrayOutputStream()

    while(byteArray.size-offset>0)
    {
        if(byteArray.size-offset>= ENCRYPT_MAX_SIZE)
        {
            temp= cipher.doFinal(byteArray,offset, ENCRYPT_MAX_SIZE)
            offset+= ENCRYPT_MAX_SIZE
        }
        else
        {
            temp= cipher.doFinal(byteArray,offset, byteArray.size-offset)
            offset=byteArray.size
        }
        os.write(temp)
    }

    os.close()

    return Base64.getEncoder().encodeToString(os.toByteArray())
}

fun decodeByPrivateKey(ciphertext: String, privateKey: PrivateKey): String{
    val DECRYPT_MAX_SIZE=256
    val byteArray = Base64.getDecoder().decode(ciphertext)
    var temp:ByteArray?
    var offset = 0
    val cipher= Cipher.getInstance("RSA")
    cipher.init(Cipher.DECRYPT_MODE, privateKey)
    val os= ByteArrayOutputStream()

    while (byteArray.size - offset > 0) {

        if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
            temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
            offset += DECRYPT_MAX_SIZE

        } else {
            temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
            offset = byteArray.size
        }

        os.write(temp)
    }

    os.close()


    return String(os.toByteArray())

}

fun setUUID(targetBlock: Block?, uuid: String) : Boolean{
    if(targetBlock?.state is TileState){
        val tileState = targetBlock.state as TileState
        val container = tileState.persistentDataContainer

        return if (container.has(NamespacedKey(Main.plugin,"ChestUUID"), PersistentDataType.STRING)){
            false
        }else {
            container.set(NamespacedKey(Main.plugin,"ChestUUID"), PersistentDataType.STRING, uuid)
            tileState.update(true)
            true
        }

    }else {
        return false
    }
}

fun getUUID(targetBlock: Block?) : String? {
    if(targetBlock?.state is TileState){
        val tileState = targetBlock.state as TileState
        val container = tileState.persistentDataContainer

        if (container.has(NamespacedKey(Main.plugin,"ChestUUID"), PersistentDataType.STRING)){
            return container.get(NamespacedKey(Main.plugin,"ChestUUID"), PersistentDataType.STRING)
        }else {
            return ""
        }

    } else{
        return ""
    }
}

fun setKey(meta: ItemStack?, uuid: String) : Boolean{
    if (meta?.itemMeta is ItemMeta){
        val metaKey = meta.itemMeta as ItemMeta
        val container = metaKey.persistentDataContainer
        if(container.has(NamespacedKey(Main.plugin,"KeyUUID"), PersistentDataType.STRING)){
            return false
        }else{
            container.set(NamespacedKey(Main.plugin,"KeyUUID"), PersistentDataType.STRING, uuid)
            meta.itemMeta = metaKey

            return true
        }
    }else{
        return false
    }

}

fun getKey(meta: ItemStack?) : String? {
    if (meta?.itemMeta is ItemMeta){
        val metaKey = meta.itemMeta as ItemMeta
        val container = metaKey.persistentDataContainer
        if(container.has(NamespacedKey(Main.plugin,"KeyUUID"), PersistentDataType.STRING)){
            return container.get(NamespacedKey(Main.plugin,"KeyUUID"), PersistentDataType.STRING)
        }else{
            return ""
        }
    }else{

        return ""
    }
}

fun keyCheck(chestKey: ItemStack?, targetChest: Block?): Boolean{
    return getKey(chestKey) == getUUID(targetChest)
}

fun clearKeyUUID(meta: ItemStack){
    val metaKey = meta.itemMeta as ItemMeta
    val container = metaKey.persistentDataContainer
    container.remove(NamespacedKey(Main.plugin,"KeyUUID"))
    meta.itemMeta = metaKey

}