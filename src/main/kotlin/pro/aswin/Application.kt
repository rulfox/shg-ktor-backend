package pro.aswin

import io.ktor.server.application.*
import pro.aswin.plugins.*
import pro.aswin.utils.Extensions.getConfigProperty

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //configureSecurity()
    /*val factory = MongoDBFactory(getConfigProperty("db.mongo.uri"), getConfigProperty("db.mongo.databaseName"), this)
    factory.createMongoClient()
    factory.createMongoDatabase()*/
    root()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    //configureDatabases()
    configureExceptions()
}
