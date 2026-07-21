package com.orbit.dashboard.neos.model

data class neosModel(

    val element_count: Int,
    val links: Links,
    val near_earth_objects: Map<String, List<NearEarthObject>>
)

data class Links(
    val next: String,
    val previous: String,
    val self: String
)

data class NearEarthObject(
    val absolute_magnitude_h: Double,
    val close_approach_data: List<CloseApproachData>,
    val estimated_diameter: EstimatedDiameter,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: LinksX,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String,
    val sentry_data: String? = null   // only present when is_sentry_object == true
)

data class LinksX(
    val self: String
)

data class CloseApproachData(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistance,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocity
)

data class RelativeVelocity(
    val kilometers_per_hour: String,
    val kilometers_per_second: String,
    val miles_per_hour: String
)

data class MissDistance(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
)

data class EstimatedDiameter(
    val feet: Feet,
    val kilometers: Kilometers,
    val meters: Meters,
    val miles: Miles
)

data class Feet(val estimated_diameter_max: Double, val estimated_diameter_min: Double)
data class Kilometers(val estimated_diameter_max: Double, val estimated_diameter_min: Double)
data class Meters(val estimated_diameter_max: Double, val estimated_diameter_min: Double)
data class Miles(val estimated_diameter_max: Double, val estimated_diameter_min: Double)