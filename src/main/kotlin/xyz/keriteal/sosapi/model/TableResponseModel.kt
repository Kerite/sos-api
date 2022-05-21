package xyz.keriteal.sosapi.model

import org.springframework.data.domain.Page

data class TableResponseModel<T>(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int,
    val total: Long,
    val items: List<T>
) {
    companion object {
        fun <U, V> fromPage(page: Page<U>, mapper: (U) -> V): TableResponseModel<V> {
            val `data` = page.content.map(mapper)
            return TableResponseModel(
                pageSize = page.size,
                pageCount = page.totalPages,
                currentPage = page.number,
                total = page.totalElements,
                items = `data`
            )
        }
    }
}