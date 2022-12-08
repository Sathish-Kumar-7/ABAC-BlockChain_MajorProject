package abac_blockchain

import sun.security.provider.DSAPublicKeyImpl
import java.io.Serializable
import java.security.InvalidKeyException
import java.security.Signature
import java.security.SignatureException
import java.util.*


class Block : Serializable {
    var prevHash: ByteArray
    lateinit var currHash: ByteArray
    var timeStamp: String? = null
    lateinit var minedBy: ByteArray
    var ledgerId = 1
    var miningPoints = 0
    var luck = 0.0
    constructor(
        prevHash: ByteArray, currHash: ByteArray, timeStamp: String?, minedBy: ByteArray, ledgerId: Int,
        miningPoints: Int, luck: Double
    ) {
        this.prevHash = prevHash
        this.currHash = currHash
        this.timeStamp = timeStamp
        this.minedBy = minedBy
        this.ledgerId = ledgerId
        this.miningPoints = miningPoints
        this.luck = luck
    }
    constructor(currentBlockChain: LinkedList<Block>) {
        val lastBlock = currentBlockChain.last
        prevHash = lastBlock.currHash
        ledgerId = lastBlock.ledgerId + 1
        luck = Math.random() * 1000000
    }
    constructor() {
        prevHash = byteArrayOf(0)
    }

    @Throws(InvalidKeyException::class, SignatureException::class)
    fun isVerified(signing: Signature): Boolean {
        signing.initVerify(DSAPublicKeyImpl(minedBy))
        signing.update(this.toString().toByteArray())
        return signing.verify(currHash)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Block) return false
        return Arrays.equals(prevHash, o.prevHash)
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(prevHash)
    }

    override fun toString(): String {
        return "Block{" +
                "prevHash=" + Arrays.toString(prevHash) +
                ", timeStamp='" + timeStamp + '\'' +
                ", minedBy=" + Arrays.toString(minedBy) +
                ", ledgerId=" + ledgerId +
                ", miningPoints=" + miningPoints +
                ", luck=" + luck +
                '}'
    }
}