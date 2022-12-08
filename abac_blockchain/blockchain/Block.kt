package abac_blockchain.blockchain

import abac_blockchain.abac.ABACP
import abac_blockchain.blockchain.SHA256Helper.generateHash
import abac_blockchain.database.DataBase
import java.util.*
import javax.xml.bind.DatatypeConverter

class Block(
    val blockId: Int,
    val transaction: String,
    ) {

    lateinit var data:String
    var nonce = 0
        private set
    var timeStamp: Long = Date().time
        private set
    var hash: String = ""
        private set
    lateinit var signature:ByteArray
        private set
    var previousHash: String =
        if(DataBase.countRows()==0)
            Constants.GENESIS_PREV_HASH
        else
            DataBase.lastHashValue()!!
    private val keyPair = DigitalSignature.generateRSAKeyPair().apply {
        println("Private Key \n${this.private}\nPublic Key\n${this.public}")
    }

    init {
        if (hash.isBlank())
            generateHash()
    }
    fun generateHash() {
        val dataToHash = blockId.toString() + previousHash + timeStamp.toString() + nonce.toString() + transaction
        val hashValue = generateHash(dataToHash)
        hash = hashValue
    }
    fun incrementNonce() {
        nonce++
    }
    override fun toString(): String {
        return "$blockId-$transaction-$hash-$previousHash"
    }
    fun setData(_data:ABACP){
        signature = DigitalSignature
            .createDigitalSignature(
                _data.toString().toByteArray(),
                keyPair.private
            )
        this.data = DatatypeConverter
            .printHexBinary(
                signature
            ).takeIf { DigitalSignature
                .verifyDigitalSignature(
                    _data.toString().toByteArray(),
                    signature,
                    keyPair.public
            )} ?: throw Error("Invalid Data | Corrupted Data")
    }
    constructor(
        blockId: Int,
        transaction: String,
        previousHash: String,
        data: String,
        signature: ByteArray,
        nonce:Int,
        timestamp: Long,
        hash:String
    ) : this(blockId, transaction){
        this.nonce = nonce
        this.timeStamp = timestamp
        this.hash = hash
        this.previousHash=previousHash
        this.data=data
        this.signature=signature
    }
}