package abac_blockchain.blockchain

import java.security.*

object DigitalSignature {
    private const val SIGNING_ALGORITHM: String = "SHA256withRSA"
    private const val RSA: String = "RSA"

    fun createDigitalSignature(
        input: ByteArray?,
        privateKey: PrivateKey?
    ): ByteArray {
        val signature = Signature.getInstance(
            SIGNING_ALGORITHM
        )
        signature.initSign(privateKey)
        signature.update(input)
        return signature.sign()
    }

    fun generateRSAKeyPair(): KeyPair {
        val secureRandom = SecureRandom()
        val keyPairGenerator = KeyPairGenerator
            .getInstance(RSA)
        keyPairGenerator
            .initialize(
                2048, secureRandom
            )
        return keyPairGenerator
            .generateKeyPair()
    }

    fun verifyDigitalSignature(
        input: ByteArray?,
        signatureToVerify: ByteArray?,
        key: PublicKey?
    ): Boolean {
        val signature = Signature.getInstance(
            SIGNING_ALGORITHM
        )
        signature.initVerify(key)
        signature.update(input)
        return signature
            .verify(signatureToVerify)
    }
}