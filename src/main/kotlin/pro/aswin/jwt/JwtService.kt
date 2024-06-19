package pro.aswin.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import pro.aswin.member.Member
import pro.aswin.member.MemberService
import pro.aswin.member.routing.LoginRequest
import java.util.*

class JwtService(private val application: Application, private val memberService: MemberService) {

    private val audience = getConfigProperty("jwt.audience")//"jwt-audience"
    private val issuer = getConfigProperty("jwt.issuer")//"http://127.0.0.1:8080/member/login"
    private val secret = getConfigProperty("jwt.secret")//"secret"
    val realm = getConfigProperty("jwt.realm")//"shg"

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createJwtToken(request: LoginRequest): String?{
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(LoginRequest::phoneNumber.name, request.phoneNumber)
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
            .sign(Algorithm.HMAC256(secret))
    }

    private fun extractPhoneNumber(credential: JWTCredential): String?{
        return credential.payload.getClaim(LoginRequest::phoneNumber.name).asString()
    }

    private fun audienceMatches(credential: JWTCredential): Boolean{
        return credential.payload.audience.contains(audience)
    }

    fun jwtTokenValidator(credential: JWTCredential): JWTPrincipal? {
        val memberID = extractPhoneNumber(credential)
        val memberFound =  runBlocking {
            memberID?.let { memberService.getMemberByPhoneNumber(it) }
        }
        memberFound?.let {
            if(audienceMatches(credential)){
                return JWTPrincipal((credential.payload))
            } else return null
        } ?: run {
            return null
        }
    }

    private fun getConfigProperty(path: String) = application.environment.config.property(path = path).getString()
}