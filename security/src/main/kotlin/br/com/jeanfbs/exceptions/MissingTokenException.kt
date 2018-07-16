package br.com.jeanfbs.exceptions

import io.jsonwebtoken.JwtException

class MissingTokenException(msg: String?) : JwtException(msg)
