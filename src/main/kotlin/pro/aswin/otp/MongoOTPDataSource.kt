package pro.aswin.otp

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import pro.aswin.utils.Extensions.getVariableKeyName

class MongoOTPDataSource(mongoClient: MongoClient) : OTPDataSource {

    private val database: MongoDatabase = mongoClient.getDatabase("shg_db")
    private val otpCollection: MongoCollection<OTP> = database.getCollection("otps", OTP::class.java)

    override suspend fun createOTP(otp: OTP): OTP = withContext(Dispatchers.IO) {
        otpCollection.insertOne(otp)
        return@withContext otp
    }

    override suspend fun getOTPByCode(otpCode: String): OTP? = withContext(Dispatchers.IO) {
        otpCollection.find(Document("otpCode", otpCode)).firstOrNull()
    }

    override suspend fun validateOTP(id: ObjectId, memberId: ObjectId, expiryTimeThreshold: Long): OTP? {
        val query = and(
            eq(OTP::memberId.getVariableKeyName(), memberId),
            eq(OTP::id.getVariableKeyName(), id),
            gte(OTP::expiryTime.getVariableKeyName(), expiryTimeThreshold)
        )
        val otpFetched = otpCollection.find(query).firstOrNull()
        return otpFetched
    }

    override suspend fun deleteOTP(otpCode: String): Boolean = withContext(Dispatchers.IO) {
        val result = otpCollection.deleteOne(Document("otpCode", otpCode))
        return@withContext result.deletedCount > 0
    }

    override suspend fun generateOTP(): String {
        return (1000..9999).random().toString()
    }

    override suspend fun generateOTPExpiryTime(): Long {
        val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 minutes in milliseconds
        val currentTimeMillis = System.currentTimeMillis()
        val expirySetAfter30Minutes = currentTimeMillis + thirtyMinutesInMillis
        return expirySetAfter30Minutes
    }
}