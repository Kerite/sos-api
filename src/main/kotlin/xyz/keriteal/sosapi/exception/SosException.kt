package xyz.keriteal.sosapi.exception

import xyz.keriteal.sosapi.enum.ApiResult

class SosException(val result: ApiResult) : RuntimeException(result.message)