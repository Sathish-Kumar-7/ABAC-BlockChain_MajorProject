package abac_blockchain.attributes

import abac_blockchain.encryption.SHA256

data class AttributeEnvironment(
    private val _createTime:Long?,
    private val _endTime:Long?
    ){

    private val sha256: SHA256 = SHA256()

    private val createTime:String? = sha256.encrypt(_createTime)

    private val endTime:String? = sha256.encrypt(_endTime)

    fun getCreateTime():String? = createTime

    fun getEndTime():String? = endTime
    override fun toString(): String {
        return "AttributeEnvironment(_createTime=$_createTime, _endTime=$_endTime)"
    }

}