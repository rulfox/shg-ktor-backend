package pro.aswin.member.mvvm

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import pro.aswin.member.data.Member

class MongoMemberDataSource(database: MongoDatabase) : MemberDataSource {
    private val memberCollection: MongoCollection<Member> = database.getCollection("members", Member::class.java)

    override suspend fun registerMember(member: Member): Boolean = withContext(Dispatchers.IO) {
        val memberInserted = memberCollection.insertOne(member)
        return@withContext memberInserted.wasAcknowledged()
    }

    override suspend fun deleteMember(memberId: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val result = memberCollection.deleteOne(Document("_id", memberId))
        return@withContext result.deletedCount > 0
    }

    override suspend fun updateMember(member: Member): Member? = withContext(Dispatchers.IO) {
        val result = memberCollection.replaceOne(Document("_id", member.id), member)
        return@withContext if (result.matchedCount > 0) member else null
    }

    override suspend fun getMember(memberId: ObjectId): Member? = withContext(Dispatchers.IO) {
        return@withContext memberCollection.find(Document("_id", memberId)).firstOrNull()
    }

    override suspend fun getMemberByEmail(email: String): Member? = withContext(Dispatchers.IO) {
        val memberQueried = memberCollection.find(Filters.eq(Member::email.name, email))
        val memberToReturn = memberQueried.firstOrNull()
        return@withContext memberToReturn
    }
}
