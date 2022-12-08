package abac_blockchain.database

import abac_blockchain.blockchain.Block
import java.sql.Connection
import java.sql.DriverManager

object DataBase {
    private var connection: Connection? = null

    private fun connectToDataBase():Connection?{
        return try {
            val connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres"
            )
            connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS blockchain(" +
                        "id SERIAL PRIMARY KEY," +
                        "blockId INTEGER UNIQUE," +
                        "transaction TEXT," +
                        "previousHash TEXT," +
                        "data TEXT," +
                        "nonce INTEGER," +
                        "timestamp BIGINT," +
                        "hash TEXT," +
                        "signature BYTEA" +
                        ")"
            ).execute()
            connection
        }catch (e:Exception){
            e.printStackTrace()
            println("Connection Failed")
            null
        }
    }
    private fun closeDatabase(){
        connection?.close()
        connection = null
    }
    fun addBlockToDatabase(block:Block):Boolean{
        connection = connectToDataBase()
        connection?.let {
            return try {
                val result = it.prepareStatement(
                    "INSERT INTO blockchain (blockId,transaction,previousHash,data,nonce,timestamp,hash,signature) VALUES (" +
                            "?,?,?,?,?,?,?,?" +
                            ")"
                )
                result.setInt(1,block.blockId)
                result.setString(2,block.transaction)
                result.setString(3,block.previousHash)
                result.setString(4,block.data)
                result.setInt(5,block.nonce)
                result.setLong(6,block.timeStamp)
                result.setString(7,block.hash)
                result.setBytes(8,block.signature)
                result.execute()
                closeDatabase()
                true
            }catch (e:Exception) {
                closeDatabase()
                false
            }
        }
        closeDatabase()
        return false
    }

    fun countRows():Int{
        connection = connectToDataBase()
        connection?.let {
            val result = it.prepareStatement("" +
                    "SELECT count(*) FROM blockchain").executeQuery()
            connection!!.close()
            return if(result.next())
                result.getInt("count")
            else
                0
        }
        connection?.close()
        return 0
    }
    fun lastHashValue():String?{
        connection = connectToDataBase()
        connection?.let {
            val result = it.prepareStatement("" +
                    "SELECT hash FROM Blockchain ORDER BY id DESC LIMIT 1").executeQuery()
            connection!!.close()
            return if (result.next())
                result.getString("hash")
            else
                null
        }
        connection?.close()
        return null
    }

    fun getBlocks(): List<Block> {
        connection = connectToDataBase()
        connection?.let {
            val resultSet = it.prepareStatement(
                "SELECT * FROM blockchain"
            ).executeQuery()
            connection!!.close()
            val blockchain = arrayListOf<Block>()
            while (resultSet.next()){
                val block = Block(
                    blockId = resultSet.getInt("blockid"),
                    transaction = resultSet.getString("transaction"),
                    previousHash = resultSet.getString("previoushash"),
                    data = resultSet.getString("data"),
                    nonce = resultSet.getInt("nonce"),
                    timestamp = resultSet.getLong("timestamp"),
                    hash = resultSet.getString("hash"),
                    signature = resultSet.getBytes("signature")
                )
                blockchain.add(block)
            }
            return blockchain
        }
        return listOf()
    }
}