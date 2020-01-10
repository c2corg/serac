package org.camptocamp.serac.c2c.client

import org.camptocamp.serac.model.Locale
import org.camptocamp.serac.model.XReport

data class C2cReport(
        val elevation: Int?,
        val nb_participants: Int?,
        val geometry: String?,
        val age: Int?,
        val autonomy: String?,
        val avalanche_slope: String?,
        val event_activity: String,
        val gender: String?,
        val nb_impacted: Int?,
        val date: String?,
        val rescue: Boolean?,
        val author_status: String?,
        val event_type: String?,
        val severity: String?,
        val activity_rate: String?,
        val previous_injuries: String?,
        val avalanche_level: String?,
        val supervision: String?,
        val qualification: String?,
        val locales: Array<Locale>
) {
    val associations: Associations = Associations(arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf())
    val anonymous: Boolean = false
    val type: String = "x"
    val quality: String = "medium"
    val disable_comments: Boolean = false

    companion object {
        @JvmStatic
        fun of(report: XReport): C2cReport {
            return C2cReport(
                    report.elevation,
                    report.nb_participants,
                    report.geometry,
                    report.age,
                    report.autonomy,
                    report.avalanche_slope,
                    report.event_activity,
                    report.gender,
                    report.nb_impacted,
                    report.date,
                    report.rescue,
                    report.author_status,
                    report.event_type,
                    report.severity,
                    report.activity_rate,
                    report.previous_injuries,
                    report.avalanche_level,
                    report.supervision,
                    report.qualification,
                    report.locales
            )
        }

    }
}
