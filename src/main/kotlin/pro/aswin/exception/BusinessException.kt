package pro.aswin.exception

class BusinessException(val responseCode: Int, val errorReason: String): RuntimeException(errorReason)