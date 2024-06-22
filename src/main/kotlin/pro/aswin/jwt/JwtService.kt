package pro.aswin.jwt

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import org.koin.core.component.KoinComponent
import pro.aswin.member.routing.LoginRequest
import java.util.*

class JwtService(private val application: Application, private val jwtRepository: JwtRepository): KoinComponent {

    private val audience = getConfigProperty("jwt.audience")//"jwt-audience"
    private val issuer = getConfigProperty("jwt.issuer")//"http://127.0.0.1:8080/member/login"
    private val secret = getConfigProperty("jwt.secret")//"secret"
    val realm = getConfigProperty("jwt.realm")//"shg"

    val jwtVerifier: JWTVerifier = jwtRepository.createJwtVerifier(
        audience = audience,
        issuer = issuer,
        algorithm = Algorithm.HMAC256(secret)
    )

    fun createJwtToken(request: LoginRequest): String? =
        jwtRepository.createJwtToken(
            audience = audience,
            issuer = issuer,
            expiry = Date(System.currentTimeMillis() + 3_600_000),
            algorithm = Algorithm.HMAC256(secret),
            request = request
        )

    fun jwtTokenValidator(credential: JWTCredential): JWTPrincipal? = jwtRepository.jwtTokenValidator(
        audience = audience,
        credential = credential
    )

    private fun getConfigProperty(path: String) = application.environment.config.property(path = path).getString()

}