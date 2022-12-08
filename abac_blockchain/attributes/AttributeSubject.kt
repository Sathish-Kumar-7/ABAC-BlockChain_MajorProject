package abac_blockchain.attributes

import abac_blockchain.encryption.SHA256

data class AttributeSubject(
    private val _userId:Int?,
    private val _role:String?,
    private val _group:String?
    ){

    private val sha256: SHA256 = SHA256()

    private val userId:String? = sha256.encrypt(_userId)

    private val role:String? = sha256.encrypt(_role)

    private val group:String? = sha256.encrypt(_group)

    fun getUserId():String? = userId

    fun getRole():String? = role

    fun getGroup():String? = group

    override fun toString(): String {
        return "AttributeSubject(_userId=$_userId, _role=$_role, _group=$_group)"
    }

}