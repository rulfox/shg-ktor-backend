package pro.aswin.exception

class GeneralException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)