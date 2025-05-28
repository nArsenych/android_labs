package com.example.lab6

data class Country(
    val name: Name,
    val capital: List<String>?,
    val population: Long,
    val region: String,
    val flags: Flags
)

data class Name(val common: String)
data class Flags(val png: String)
