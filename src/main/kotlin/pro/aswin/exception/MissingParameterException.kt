package pro.aswin.exception

open class MissingParameterException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)