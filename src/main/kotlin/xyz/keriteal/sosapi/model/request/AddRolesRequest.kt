package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel

class AddRolesRequest(
    val userId: Long,
    val roleIds: Set<Long>
): RequestModel()