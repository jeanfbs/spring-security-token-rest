package br.com.jeanfbs.controllers.public

import br.com.jeanfbs.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class AuthenticationResource{

    @Autowired
    lateinit var tokenService: TokenService

    @PostMapping("/login", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun login(@RequestParam("username") username: String,
              @RequestParam("password") password: String): ResponseEntity<MutableMap<String,String>> {
        var response: MutableMap<String, String> = mutableMapOf()
        response.put("token", tokenService.authenticate(username, password))
        return ResponseEntity.ok(response)
    }

}