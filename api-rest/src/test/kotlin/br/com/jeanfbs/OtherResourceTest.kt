package br.com.jeanfbs

import br.com.jeanfbs.services.TokenService
import br.com.jeanfbs.services.User
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
class OtherResourceTest: DefaultResource(){

    @Autowired
    lateinit var tokenService: TokenService

    @MockBean
    lateinit var testeService: TesteService

    @Test
    @Throws(Exception::class)
    fun teste() {
        val lista = mutableListOf<User>()

        lista.add(User(cpf = "123", authorities = mutableListOf()))
        lista.add(User(cpf = "222", authorities = mutableListOf()))

        `when`(testeService.getUsers()).thenReturn(lista)

        val token = tokenService.authenticate("teste", "123")

        val response = this.mockMvc!!.perform(get("/api/teste")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].cpf", `is`("123")))
                .andDo(document("teste"))
    }

    @Test
    @Throws(Exception::class)
    fun testeTokenExpired() {

        val token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6W3sidmFsdWUiOiJVU0VSIiwiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJzdWIiOiIxMjMxMjQyMTMiLCJleHAiOjE1MzE2MzQ5NTd9.Llfm4Aig9kVG6FPkBSH0rl4YHZ_3UUyPFsBzCpuWS2WaqOAswmhgd_TdtxiVzIyyd0JO566zDzBzBWdOzESPiw"

        this.mockMvc!!.perform(get("/api/teste")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
                .andDo(MockMvcRestDocumentation.document("teste"))
    }


}