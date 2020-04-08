package com.garlyks.core.worldengine.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidParameterException(message: String, cause: Exception? = null) : RuntimeException(message, cause)
