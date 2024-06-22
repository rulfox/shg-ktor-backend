package pro.aswin.jwt

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import pro.aswin.member.routing.LoginRequest
import java.util.*

interface JwtRepository {
    fun createJwtVerifier(audience: String, issuer: String, algorithm: Algorithm): JWTVerifier
    fun createJwtToken(audience: String, issuer: String, expiry: Date, algorithm: Algorithm, request: LoginRequest): String?
    fun jwtTokenValidator(audience: String, credential: JWTCredential): JWTPrincipal?
}