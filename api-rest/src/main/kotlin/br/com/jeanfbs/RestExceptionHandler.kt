package br.com.jeanfbs

import br.com.jeanfbs.exceptions.ApiError
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {


    override fun handleBindException(e: BindException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(ApiError(e.javaClass.name, e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handlerException( e: Exception, request: WebRequest): ResponseEntity<ApiError>{

        return ResponseEntity.ok(ApiError(e.javaClass.name, e.message))

    }


}
