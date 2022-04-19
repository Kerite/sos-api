package xyz.keriteal.sosbk

import xyz.keriteal.sosbk.enum.ApiResult

class SosException(val result: ApiResult) : RuntimeException()