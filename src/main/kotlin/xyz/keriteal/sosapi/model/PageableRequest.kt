package xyz.keriteal.sosapi.model

/**
 * 需要分页的请求
 */
open class PageableRequest {
    var pageSize: Int = 20
    var pageIdx: Int = 0
}