package pro.aswin.exception

open class EntityNotFoundException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)