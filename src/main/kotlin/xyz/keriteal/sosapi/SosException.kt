package xyz.keriteal.sosapi

import xyz.keriteal.sosapi.enum.ApiResult

class SosException(val result: ApiResult) : RuntimeException(result.message)