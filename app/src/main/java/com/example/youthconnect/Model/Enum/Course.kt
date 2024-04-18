package com.example.youthconnect.Model.Enum

enum class Course(val displayName: String) {
    TerceroEP("3º de Primaria"),
    CuartoEP("4º de Primaria"),
    QuintoEP("5º de Primaria"),
    SextoEP("6º de Primaria"),
    PrimeroESO("1º de ESO"),
    SegundoESO("2º de ESO"),
    TerceroESO("3º de ESO"),
    CuartoESO("4º de ESO");

    companion object {
        fun fromString(s: String): Course? {
            println(s)
            return values().find { it.displayName == s }
        }
    }
}