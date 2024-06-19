package pro.aswin.member

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import pro.aswin.exception.InsertionFailedException
import pro.aswin.exception.RequestedContentNotFoundException

class MemberRepositoryImpl(database: MongoDatabase): MemberRepository {

    companion object {
        const val MEMBER_COLLECTION = "member"
    }

    private val members: MongoCollection<Member> = database.getCollection(MEMBER_COLLECTION, Member::class.java)

    override suspend fun getMemberByPhoneNumber(phoneNumber: String): Member? {
        val filter = Filters.eq(Member::phoneNumber.name, phoneNumber)
        return members.find(filter).firstOrNull()
    }

    override suspend fun getMemberById(id: String): Member? {
        val filter = Filters.eq(Member::id.name, ObjectId(id))
        return members.find(filter).firstOrNull()
    }

    override suspend fun insertMember(member: Member): Boolean {
        return members.insertOne(member).wasAcknowledged()
    }
}