package pro.aswin.plugins

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import pro.aswin.jwt.JwtRepositoryImpl
import pro.aswin.jwt.JwtService
import pro.aswin.member.MemberRepositoryImpl
import pro.aswin.member.MemberService


fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
    val memberRepository = MemberRepositoryImpl(mongoDatabase)
    val jwtRepository = JwtRepositoryImpl(memberRepository = memberRepository)
    val jwtService = JwtService(application = this, jwtRepository = jwtRepository)
    val memberService = MemberService(repository = memberRepository, jwtService = jwtService)

    configureRouting(memberService, jwtService)
    configureSecurity(jwtService = jwtService)
}

/**
 * Establishes connection with a MongoDB database.
 *
 * The following configuration properties (in application.yaml/application.conf) can be specified:
 * * `db.mongo.user` username for your database
 * * `db.mongo.password` password for the user
 * * `db.mongo.host` host that will be used for the database connection
 * * `db.mongo.port` port that will be used for the database connection
 * * `db.mongo.maxPoolSize` maximum number of connections to a MongoDB server
 * * `db.mongo.database.name` name of the database
 *
 * IMPORTANT NOTE: in order to make MongoDB connection working, you have to start a MongoDB server first.
 * See the instructions here: https://www.mongodb.com/docs/manual/administration/install-community/
 * all the paramaters above
 *
 * @returns [MongoDatabase] instance
 * */
fun Application.connectToMongoDB(): MongoDatabase {
    /*val user = environment.config.tryGetString("aswin")
    val password = environment.config.tryGetString("aswin")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "shg"
    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb+srv://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"*/
    val uri = "mongodb+srv://aswin:aswin@cluster0.bzs3odj.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val databaseName = "shg"
    val pojoCodecRegistry: CodecRegistry = fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build())
    )
    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry)

    environment.monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
