package org.camptocamp.serac.model

data class Locale(
        val modifications: String?,
        val motivations: String?,
        val training: String?,
        val place: String?,
        val route_study: String?,
        val summary: String?,
        val safety: String?,
        val description: String?,
        val reduce_impact: String?,
        val risk: String?,
        val title: String,
        val other_comments: String?,
        val increase_impact: String?,
        val time_management: String?,
        val conditions: String?,
        val group_management: String?,
        val lang: String = "fr"
)
