package pro.aswin

import io.ktor.server.application.*
import pro.aswin.member.MemberRepository
import pro.aswin.member.MemberRepositoryImpl
import pro.aswin.member.MemberService
import pro.aswin.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureExceptions()
}
