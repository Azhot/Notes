package fr.azhot.notes

import java.util.*

data class Note(
    var title: String = "",
    var text: String = "",
    val id: String = UUID.randomUUID().toString()
)
