package br.com.jeanfbs.configs

import br.com.jeanfbs.exceptions.ApiError
import br.com.jeanfbs.exceptions.MissingTokenException
import br.com.jeanfbs.services.TokenService
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter(val tokenService: TokenService): GenericFilterBean() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        try {
            val authentication = tokenService.getAuthentication(httpRequest)
            SecurityContextHolder.getContext().authentication = authentication
            chain?.doFilter(httpRequest, httpResponse)
        }catch (e: Exception ){
            var httpStatus: HttpStatus? = null
            when(e){
                is MalformedJwtException -> httpStatus = HttpStatus.BAD_REQUEST
                is MissingTokenException -> httpStatus = HttpStatus.BAD_REQUEST
                is ExpiredJwtException -> httpStatus = HttpStatus.UNAUTHORIZED
                else -> httpStatus = HttpStatus.FORBIDDEN
            }
            sendError(e,httpResponse,HttpStatus.BAD_REQUEST)
        }

    }



    private fun sendError(e: Exception, response: HttpServletResponse, status: HttpStatus){
        val apiError = ApiError(e.javaClass.simpleName, e.message)
        val mp = ObjectMapper()

        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
        response.writer.print(mp.writeValueAsString(apiError))
        response.status = status.value()
    }

}