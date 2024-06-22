package pro.aswin.di
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pro.aswin.jwt.JwtRepository
import pro.aswin.jwt.JwtRepositoryImpl
import pro.aswin.jwt.JwtService

val jwtModule = module {
    singleOf(::JwtRepositoryImpl) { bind<JwtRepository>() }
    singleOf(::JwtService)
}