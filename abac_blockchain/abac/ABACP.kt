package abac_blockchain.abac

import abac_blockchain.attributes.AttributeEnvironment
import abac_blockchain.attributes.AttributeObject
import abac_blockchain.attributes.AttributePermission
import abac_blockchain.attributes.AttributeSubject

data class ABACP(
    private val _AttributeSubject: AttributeSubject?,
    private val _AttributeObject: AttributeObject?,
    private val _AttributePermission: AttributePermission?,
    private val _AttributeEnvironment: AttributeEnvironment?
){
    private var attributeSubject:AttributeSubject
    private var attributeObject:AttributeObject
    private var attributeEnvironment: AttributeEnvironment
    private var attributePermission: AttributePermission
    init {
        ABACAlgorithm.checkPolicy(_AttributeSubject,_AttributeObject,_AttributePermission,_AttributeEnvironment)
            .let {
                it.apply {
                    attributeSubject = _AttributeSubject!!
                    attributeObject = _AttributeObject!!
                    attributePermission = _AttributePermission!!
                    attributeEnvironment = _AttributeEnvironment!!
                }
            }
    }

    fun getAS():AttributeSubject = attributeSubject

    fun getAO():AttributeObject = attributeObject

    fun getAP():AttributePermission = attributePermission

    fun getAE():AttributeEnvironment = attributeEnvironment
}
