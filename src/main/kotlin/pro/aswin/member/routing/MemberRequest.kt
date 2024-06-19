package pro.aswin.member.routing

import kotlinx.serialization.Serializable

@Serializable
data class MemberRequest (
    val phoneNumber: String,
    val password: String,
)