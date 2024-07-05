package pro.aswin

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import pro.aswin.otp.OTP

class MongoDBFactory(private val uri: String, private val databaseName: String, private val application: Application) {

    private val mongoClient: MongoClient by lazy {
        val codecRegistry = CodecRegistries.fromRegistries(
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
            MongoClientSettings.getDefaultCodecRegistry()
        )

        // Configure MongoDB client settings
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .codecRegistry(codecRegistry)
            .build()
        MongoClients.create(settings)
    }

    fun createMongoClient(): MongoClient {
        return mongoClient
    }

    fun createMongoDatabase(): MongoDatabase {


        val database = mongoClient.getDatabase(databaseName)

        application.environment.monitor.subscribe(ApplicationStopped) {
            mongoClient.close()
        }

        return database
    }
}

/*
class MongoDBFactory(private val uri: String, private val databaseName: String, private val application: Application) {

    fun createMongoDatabase(): MongoDatabase {
        */
/*val user = environment.config.tryGetString("aswin")
    val password = environment.config.tryGetString("aswin")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "shg"
    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb+srv://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"*//*

        val uri = "mongodb+srv://aswin:aswin@cluster0.bzs3odj.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        val databaseName = "shg"
        val pojoCodecRegistry: CodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
        val mongoClient = MongoClients.create(uri)
        val database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry)

        application.environment.monitor.subscribe(ApplicationStopped) {
            mongoClient.close()
        }

        return database
    }
}*/
