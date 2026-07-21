package com.example.jetpackcomposearchi.dashboard.events.model

data class Events(
    val title: String,
    val description: String,
    val link: String,
    val events: List<Event>,
)

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val link: String,
    val categories: List<Category>,
    val sources: List<Source>,
    val geometries: List<Geometry>,
)

data class Category(
    val id: Long,
    val title: String,
)

data class Source(
    val id: String,
    val url: String,
)

data class Geometry(
    val date: String,
    val type: String,
    val coordinates: List<Double>,
)

