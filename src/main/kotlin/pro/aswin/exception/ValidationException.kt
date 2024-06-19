package pro.aswin.exception

open class ValidationException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)