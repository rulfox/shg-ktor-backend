package pro.aswin.exception

open class RequestedContentNotFoundException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)