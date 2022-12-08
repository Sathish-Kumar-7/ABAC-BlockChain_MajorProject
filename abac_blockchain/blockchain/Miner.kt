package abac_blockchain.blockchain

import abac_blockchain.database.DataBase

object Miner {
    private var reward = 0.0
    fun mine(block: Block):Boolean{
        if (!Blockchain.getBlockchain().map { it.blockId }.contains(block.blockId)) {
            while (notGoldenHash(block)) {
                block.generateHash()
                block.incrementNonce()
            }
            println("$block has just mined...")
            println("Hash is: " + block.hash)
            DataBase.addBlockToDatabase(block)
            reward += Constants.MINER_REWARD
            println("Total Reward = $reward")
            return true
        }
        return false
    }
    private fun notGoldenHash(block: Block): Boolean {
        val leadingZeros = String(CharArray(Constants.DIFFICULTY)).replace('\u0000', '0')
        return block.hash.substring(0, Constants.DIFFICULTY) != leadingZeros
    }
}