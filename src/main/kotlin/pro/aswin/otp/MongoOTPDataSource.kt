package pro.aswin.otp

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import pro.aswin.utils.AppConstants.OTP.DEFAULT_EXPIRY_DURATION_IN_MILLIS
import pro.aswin.utils.Extensions.getVariableKeyName

class MongoOTPDataSource(database: MongoDatabase) : OTPDataSource {

    private val otpCollection: MongoCollection<OTP> = database.getCollection("otps", OTP::class.java)

    override suspend fun createOTP(otp: OTP): OTP = withContext(Dispatchers.IO) {
        otpCollection.insertOne(otp)
        return@withContext otp
    }

    override suspend fun getOTPByCode(otpCode: String): OTP? = withContext(Dispatchers.IO) {
        otpCollection.find(Document("otpCode", otpCode)).firstOrNull()
    }

    override suspend fun validateOTP(request: ValidateOtpRequest): OTP? {
        val query = and(
            eq(OTP::memberId.getVariableKeyName(), request.memberId),
            eq(OTP::id.getVariableKeyName(), request.id),
            lte(OTP::expiryTime.getVariableKeyName(), request.expiryTimeThreshold?:0L)
        )
        val otpFetched = otpCollection.find(query).firstOrNull()
        return otpFetched
    }

    override suspend fun deleteOTP(id: String): Boolean = withContext(Dispatchers.IO) {
        val result = otpCollection.deleteOne(Document(OTP::id.getVariableKeyName(), id))
        return@withContext result.deletedCount > 0
    }

    override suspend fun generateOTP(): String {
        return (1000..9999).random().toString()
    }

    override suspend fun generateOTPExpiryTime(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val expirySetAfter30Minutes = currentTimeMillis + DEFAULT_EXPIRY_DURATION_IN_MILLIS
        return expirySetAfter30Minutes
    }
}