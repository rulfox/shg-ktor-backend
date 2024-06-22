package pro.aswin.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pro.aswin.member.MemberRepository
import pro.aswin.member.routing.LoginRequest
import java.util.*

class JwtRepositoryImpl(private val memberRepository: MemberRepository): KoinComponent, JwtRepository {

    override fun createJwtVerifier(audience: String, issuer: String, algorithm: Algorithm): JWTVerifier {
        return JWT
            .require(algorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }

    override fun createJwtToken(audience: String, issuer: String, expiry: Date, algorithm: Algorithm, request: LoginRequest): String? {
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(LoginRequest::phoneNumber.name, request.phoneNumber)
            .withExpiresAt(expiry)
            .sign(algorithm)
    }

    override fun jwtTokenValidator(audience: String, credential: JWTCredential): JWTPrincipal? {
        val memberID = extractPhoneNumber(credential)
        val memberFound =  runBlocking {
            memberID?.let { memberRepository.getMemberByPhoneNumber(it) }
        }
        memberFound?.let {
            if(audienceMatches(audience = audience, credential = credential)){
                return JWTPrincipal((credential.payload))
            } else return null
        } ?: run {
            return null
        }
    }

    private fun extractPhoneNumber(credential: JWTCredential): String?{
        return credential.payload.getClaim(LoginRequest::phoneNumber.name).asString()
    }

    private fun audienceMatches(audience: String, credential: JWTCredential): Boolean{
        return credential.payload.audience.contains(audience)
    }
}