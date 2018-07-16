package br.com.jeanfbs

import br.com.jeanfbs.controllers.public.PublicResource
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@RunWith(SpringJUnit4ClassRunner::class)
@WebMvcTest(PublicResource::class)
class PublicResourceTest: DefaultResource() {


    @Test
    @Throws(Exception::class)
    fun shouldReturnDefaultMessage() {
        val retorno = this.mockMvc!!.perform(get("/public/tokens")).andDo(print())
                .andDo(document("tokens"))
                .andReturn().response

        print("teste")
    }

}