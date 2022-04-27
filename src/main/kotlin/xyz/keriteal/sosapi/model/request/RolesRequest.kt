package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel

data class RolesRequest(
    val userId: Long
) : RequestModel()