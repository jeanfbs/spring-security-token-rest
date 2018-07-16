package br.com.jeanfbs

import br.com.jeanfbs.services.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class TesteService{

    fun getUsers(): MutableList<User>{

        val lista = mutableListOf<User>()

        lista.add(User(cpf = "123", authorities = mutableListOf()))
        lista.add(User(cpf = "222", authorities = mutableListOf()))
        return lista

    }


}