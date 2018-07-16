package br.com.jeanfbs.controllers

import br.com.jeanfbs.TesteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class OtherResource{

    @Autowired
    lateinit var testeService: TesteService

    @GetMapping("/teste", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun login(): ResponseEntity<Any>{
        return ResponseEntity.ok(testeService.getUsers())
    }
}