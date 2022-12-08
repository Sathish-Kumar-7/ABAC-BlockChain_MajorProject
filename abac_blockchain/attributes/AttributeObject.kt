package abac_blockchain.attributes

import abac_blockchain.encryption.SHA256

data class AttributeObject(
    private val _deviceId:String?
    ){

    private val sha256: SHA256 = SHA256()

    private val deviceId:String? = sha256.encrypt(_deviceId)

    fun getDeviceId():String? = deviceId

    override fun toString(): String {
        return "AttributeObject(_deviceId=$_deviceId)"
    }

}
