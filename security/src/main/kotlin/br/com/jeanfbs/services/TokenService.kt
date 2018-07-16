package br.com.jeanfbs.services

import br.com.jeanfbs.exceptions.MissingTokenException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


interface TokenService {
    fun authenticate(username: String, password: String): String
    fun getAuthentication(request: HttpServletRequest): Authentication
}

@Service
class TokenServiceImpl: TokenService {

    @Value("\${jwt.token.expire.hours}")
    lateinit var expirationHours: String

    private val SECRET = "mysecret"

    val BEARER = "Bearer"

    override fun authenticate(username: String, password: String): String {

        var user = User("123124213", mutableListOf(Role("USER")))
        var claim = DefaultClaims().apply {
            set("authorities", user.authorities)
            subject = user.cpf
        }

        val token = Jwts.builder()
                .setClaims(claim)
                .setExpiration(getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()

        return "${BEARER} ${token}"
    }

    private fun getExpirationDate(): Date{
        val expirationLocalDateTime = LocalDateTime.now().plusHours(expirationHours.toLong())
        return Date.from(expirationLocalDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }


    override fun getAuthentication(request: HttpServletRequest): Authentication {

        val token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElseThrow{ MissingTokenException("Token isn't present on request") }

        val claim = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(StringUtils.removeStart(token, BEARER))
                .body as DefaultClaims

        val authorities = claim.get("authorities") as MutableList<LinkedHashMap<String, String>>
        val roles = authorities.stream()
                .map { it.get("value")?.let { Role(it) } }
                .collect(Collectors.toList())
        return UsernamePasswordAuthenticationToken(claim.subject, null, roles)
    }

}

data class Role(
        val value: String
): GrantedAuthority{
    override fun getAuthority(): String {
        return "ROLE_${value}"
    }

}


data class User(
        val cpf: String,
        val authorities: MutableList<GrantedAuthority>
)