package pro.aswin.member

interface MemberRepository {
    suspend fun getMemberByPhoneNumber(phoneNumber: String): Member?
    suspend fun getMemberById(id: String): Member?
    suspend fun insertMember(member: Member): Boolean
}