package abac_blockchain.blockchain

import abac_blockchain.database.DataBase

object Blockchain {

    fun addBlock(
        block:Block
    ):Boolean{
        return Miner.mine(block);
    }

    fun getBlockchain(): List<Block> {
        return DataBase.getBlocks()
    }

    fun size(): Int {
        return DataBase.countRows()
    }

    override fun toString(): String {
        var blockChain = ""
        for (block in getBlockchain())
            blockChain += """$block""".trimIndent()
        return blockChain
    }
}