package pro.aswin.shg.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import pro.aswin.shg.model.SelfHelpGroup

class SelfHelpGroupRepositoryImpl(private val database: MongoDatabase): KoinComponent, SelfHelpGroupRepository {

    companion object {
        const val SHG_COLLECTION = "self_help_groups"
    }

    private val selfHelpGroups: MongoCollection<SelfHelpGroup> = database.getCollection(SHG_COLLECTION, SelfHelpGroup::class.java)

    override suspend fun createSelfHelpGroup(creatingMemberId: String, selfHelpGroup: SelfHelpGroup): Boolean {
        return selfHelpGroups.insertOne(selfHelpGroup).wasAcknowledged()
    }

    override suspend fun getSelfHelpGroupById(id: String): SelfHelpGroup? {
        val filter = Filters.eq(SelfHelpGroup::id.name, ObjectId(id))
        return selfHelpGroups.find(filter).firstOrNull()
    }
}