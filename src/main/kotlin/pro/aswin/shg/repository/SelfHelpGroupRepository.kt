package pro.aswin.shg.repository

import pro.aswin.shg.model.SelfHelpGroup

interface SelfHelpGroupRepository {
    suspend fun createSelfHelpGroup(creatingMemberId: String, selfHelpGroup: SelfHelpGroup): Boolean
    suspend fun getSelfHelpGroupById(id: String): SelfHelpGroup?
}