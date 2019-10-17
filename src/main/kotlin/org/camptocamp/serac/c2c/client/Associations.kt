package org.camptocamp.serac.c2c.client

data class Associations(
        val articles: Array<String>,
        val images: Array<String>,
        val outings: Array<String>,
        val routes: Array<String>,
        val users: Array<String>,
        val waypoints: Array<String>
)
