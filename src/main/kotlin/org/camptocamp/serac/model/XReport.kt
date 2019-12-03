package org.camptocamp.serac.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "xreport")
data class XReport(
        @Id var id: String?,
        var validated: Boolean,
        var custom: Map<String, String>,
        val elevation: Int?,
        val nb_participants: Int?,
        val geometry: String?,
        val age: Int?,
        val autonomy: String?,
        val avalanche_slope: String?,
        val activities: Array<String>,
        val nb_outings: String?,
        val gender: String?,
        val nb_impacted: Int?,
        val date: String?,
        val rescue: Boolean?,
        val author_status: String?,
        val event_type: Array<String>,
        val severity: String?,
        val activity_rate: String?,
        val previous_injuries: String?,
        val avalanche_level: String?,
        val locales: Array<Locale>
)
