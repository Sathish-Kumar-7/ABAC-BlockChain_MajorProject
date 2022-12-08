package abac_blockchain.abac

import abac_blockchain.attributes.AttributeEnvironment
import abac_blockchain.attributes.AttributeObject
import abac_blockchain.attributes.AttributePermission
import abac_blockchain.attributes.AttributeSubject
import abac_blockchain.blockchain.Block
import abac_blockchain.blockchain.Blockchain

object ABACAlgorithm {

    fun checkPolicy(
        _AttributeSubject: AttributeSubject?,
        _AttributeObject: AttributeObject?,
        _AttributePermission: AttributePermission?,
        _AttributeEnvironment: AttributeEnvironment?
    ):Boolean{
            _AttributeSubject?.let {
                if (it.getUserId() == null
                    && it.getRole() == null
                    && it.getGroup() == null
                )
                    return false
            } ?: return false

            _AttributeObject?.let {
                if (it.getDeviceId()==null)
                    return false
            } ?: return false

            _AttributeEnvironment?.let {
                if(it.getCreateTime() == null
                    && it.getEndTime() == null)
                    return false
            } ?: return false

            if(_AttributePermission == null)
                return false
            return true
        }
    fun addPolicy(
        block:Block
    ):Boolean{
        if (!Blockchain.addBlock(block))
            throw Error("Error While Adding New Block")
        return true
    }
}