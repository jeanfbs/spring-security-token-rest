package br.com.jeanfbs.exceptions

data class ApiError(
        val error: String,
        val message: String?
)