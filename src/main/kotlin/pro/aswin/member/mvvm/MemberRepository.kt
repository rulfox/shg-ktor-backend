package pro.aswin.member.mvvm

import org.bson.types.ObjectId
import pro.aswin.member.data.Member

class MemberRepository(private val dataSource: MemberDataSource) {
    suspend fun registerMember(member: Member): Boolean = dataSource.registerMember(member)
    suspend fun deleteMember(memberId: ObjectId): Boolean = dataSource.deleteMember(memberId)
    suspend fun updateMember(member: Member): Member? = dataSource.updateMember(member)
    suspend fun getMember(memberId: ObjectId): Member? = dataSource.getMember(memberId)
    suspend fun getMemberByEmail(email: String): Member? = dataSource.getMemberByEmail(email)
}