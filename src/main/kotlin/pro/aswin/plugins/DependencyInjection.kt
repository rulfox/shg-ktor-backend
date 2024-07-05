package pro.aswin.plugins

import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pro.aswin.MongoDBFactory
import pro.aswin.member.domain.DeleteMemberUseCase
import pro.aswin.member.domain.GetMemberUseCase
import pro.aswin.member.domain.RegisterMemberUseCase
import pro.aswin.member.domain.UpdateMemberUseCase
import pro.aswin.member.mvvm.MemberDataSource
import pro.aswin.member.mvvm.MongoMemberDataSource
import pro.aswin.otp.MongoOTPDataSource
import pro.aswin.otp.OTPDataSource
import pro.aswin.otp.ValidateOTPUseCase
import pro.aswin.member.mvvm.MemberRepository
import pro.aswin.otp.OTPRepository
import pro.aswin.jwt.JwtService
import pro.aswin.jwt.JwtRepository
import pro.aswin.jwt.JwtRepositoryImpl
import pro.aswin.utils.Extensions.getConfigProperty

fun Application.root() {
    install(Koin) {

        slf4jLogger()

        val ktorModule = module {
            single { this@root }
        }

        val mongoModule = module {
            single(named("mongoUri")) { environment.config.property(path = "db.mongo.uri").getString() }
            single(named("databaseName")) { environment.config.property(path = "db.mongo.databaseName").getString() }
        }

        val databaseModule = module {
            single { MongoDBFactory(getConfigProperty("db.mongo.uri"), getConfigProperty("db.mongo.databaseName"), get()) }
            single { get<MongoDBFactory>().createMongoClient() }
            single { get<MongoDBFactory>().createMongoDatabase() }
        }

        /*val jwtModule = module{
            singleOf(::JwtRepositoryImpl) { bind<JwtRepository>() }
            single { JwtService(get(), get()) }
        }*/

        val dataSourceModule = module {
            singleOf(::MongoMemberDataSource) { bind<MemberDataSource>() }
            singleOf(::MongoOTPDataSource) { bind<OTPDataSource>() }
        }

        val repositoryModule = module {
            singleOf(::MemberRepository)
            singleOf(::OTPRepository)
        }

        val useCaseModule = module {
            singleOf(::RegisterMemberUseCase)
            singleOf(::DeleteMemberUseCase)
            singleOf(::UpdateMemberUseCase)
            singleOf(::GetMemberUseCase)
            singleOf(::ValidateOTPUseCase)
        }

        val appModule = listOf(ktorModule, mongoModule, databaseModule, dataSourceModule, repositoryModule, useCaseModule)

        modules(
            appModule
        )
    }
}