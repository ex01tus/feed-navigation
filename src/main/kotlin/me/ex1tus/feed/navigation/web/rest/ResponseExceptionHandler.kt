package me.ex1tus.feed.navigation.web.rest

import jakarta.validation.ConstraintViolationException
import me.ex1tus.feed.navigation.domain.ErrorCode
import me.ex1tus.feed.navigation.domain.ErrorResponse
import me.ex1tus.feed.navigation.exception.InvalidRequestException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class ResponseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [ConstraintViolationException::class, InvalidRequestException::class])
    fun handleRequestValidationException(exception: Exception) =
        ErrorResponse(ErrorCode.CONSTRAINT_VIOLATION, exception.message ?: "no description")
            .also { log.warn { "<- [GET /feed] response=$it" } }
}
