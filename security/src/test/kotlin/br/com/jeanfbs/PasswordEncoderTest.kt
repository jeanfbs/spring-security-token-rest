package br.com.jeanfbs

import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


class PasswordEncoderTest{


    @Test
    fun test(){
        val encoder = BCryptPasswordEncoder()
        println(encoder.encode("123"))
    }

}