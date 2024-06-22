package pro.aswin.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pro.aswin.member.MemberRepository
import pro.aswin.member.MemberRepositoryImpl
import pro.aswin.member.MemberService

val memberModule = module {
    singleOf(::MemberRepositoryImpl) { bind<MemberRepository>() }
    singleOf(::MemberService)
}