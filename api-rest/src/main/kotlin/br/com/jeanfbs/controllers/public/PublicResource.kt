package br.com.jeanfbs.controllers.public

import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/public")
class PublicResource{



    @GetMapping("/tokens")
    @ResponseBody
    fun login(): MutableList<Token> {

        val results = mutableListOf<Token>()
        for(i in 1..10){
            results.add(Token(UUID.randomUUID().toString()))
        }

        return results
    }

}

data class Token(
    val value: String
)
