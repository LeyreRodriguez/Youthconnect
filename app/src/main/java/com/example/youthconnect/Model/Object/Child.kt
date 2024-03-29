package com.example.youthconnect.Model.Object

data class Child(
    val FullName: String,
    val ID: String,
    val Course: String,
    val Password: String,
    val BelongsToSchool: Boolean,
    val FaithGroups: Boolean,
    val GoOutAlone: Boolean,
    val Observations: String? = null,
    val ParentID: List<String>,
    var InstructorID: String,
    val State : Boolean,
    val Score : Int? =  0
)

