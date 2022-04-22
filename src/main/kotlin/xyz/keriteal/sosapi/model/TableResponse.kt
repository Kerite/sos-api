package xyz.keriteal.sosapi.model

data class TableResponse<T>(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int,
    val list: List<T>
)