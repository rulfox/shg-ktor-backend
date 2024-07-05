package pro.aswin.member.mvvm

import org.bson.types.ObjectId
import pro.aswin.member.data.Member

interface MemberDataSource {
    suspend fun registerMember(member: Member): Boolean
    suspend fun deleteMember(memberId: ObjectId): Boolean
    suspend fun updateMember(member: Member): Member?
    suspend fun getMember(memberId: ObjectId): Member?
    suspend fun getMemberByEmail(email: String): Member?
}