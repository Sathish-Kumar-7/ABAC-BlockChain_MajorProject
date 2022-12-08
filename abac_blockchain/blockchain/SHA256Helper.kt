package abac_blockchain.blockchain

import java.security.MessageDigest


object SHA256Helper {
    fun generateHash(data: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(data.toByteArray(charset("UTF-8")))
            val hexadecimalString = StringBuffer()
            for (i in hash.indices) {
                val hexadecimal = Integer.toHexString(0xff and hash[i].toInt())
                if (hexadecimal.length == 1) hexadecimalString.append('0')
                hexadecimalString.append(hexadecimal)
            }
            hexadecimalString.toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}