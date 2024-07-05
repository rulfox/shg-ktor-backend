package pro.aswin.utils

import com.google.gson.*
import org.bson.types.ObjectId
import java.lang.reflect.Type

object ObjectIdSerializer : JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {
    override fun serialize(src: ObjectId?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.toHexString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObjectId {
        return ObjectId(json?.asString)
    }
}