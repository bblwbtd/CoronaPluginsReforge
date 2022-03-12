package utils


import org.bukkit.block.Block
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
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
