package pro.aswin.member

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import pro.aswin.member.routing.LoginResponse

class MemberRepositoryImpl(database: MongoDatabase): KoinComponent, MemberRepository {

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

    override suspend fun login(phoneNumber: String, password: String): LoginResponse? {
        val filters = mutableListOf<Bson>()
        filters.add(Filters.eq(Member::phoneNumber.name, phoneNumber))
        filters.add(Filters.eq(Member::password.name, password))
        val combinedFilter = Filters.and(filters)
        members.find(combinedFilter).firstOrNull()?.let {
            return it.toLoginResponse()
        } ?: run {
            return null
        }
    }
}