package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel

class AddRolesRequest(
    val userId: Int,
    val roleIds: Set<Long>
) : RequestModel()