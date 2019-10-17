package org.camptocamp.serac.controller

data class CreateResponse(val id: String) {
    companion object {
        @JvmStatic
        fun of(id: String): CreateResponse {
            return CreateResponse(id)
        }

    }
}
