package pro.aswin.exception

open class InsertionFailedException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)