package org.camptocamp.serac.c2c.client

data class Credentials(val username: String, val password: String) {
    val discourse: Boolean = false
}
