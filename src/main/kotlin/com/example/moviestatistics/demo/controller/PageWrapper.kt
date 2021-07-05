package com.example.moviestatistics.demo.controller

import org.springframework.data.domain.Page


class PageWrapper<T>(private val page: Page<T>, var url: String) {
    private val items: MutableList<PageItem>
    val number: Int

    fun getItems(): List<PageItem> {
        return items
    }

    val content: List<T>
        get() = page.content
    val size: Int
        get() = page.size
    val totalPages: Int
        get() = page.totalPages
    val totalElements: Long
        get() = page.totalElements
    val isFirstPage: Boolean
        get() = page.isFirst
    val isLastPage: Boolean
        get() = page.isLast
    val isHasPreviousPage: Boolean
        get() = page.hasPrevious()
    val isHasNextPage: Boolean
        get() = page.hasNext()

    inner class PageItem(val number: Int, val isCurrent: Boolean)
    companion object {
        const val MAX_PAGE_ITEM_DISPLAY = 10
    }

    init {
        items = ArrayList()
        number = page.number + 1 //start from 1 to match page.page
        val start: Int
        val size: Int
        if (page.totalPages <= MAX_PAGE_ITEM_DISPLAY) {
            start = 1
            size = page.totalPages
        } else {
            if (number <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
                start = 1
                size = MAX_PAGE_ITEM_DISPLAY
            } else if (number >= page.totalPages - MAX_PAGE_ITEM_DISPLAY / 2) {
                start = page.totalPages - MAX_PAGE_ITEM_DISPLAY + 1
                size = MAX_PAGE_ITEM_DISPLAY
            } else {
                start = number - MAX_PAGE_ITEM_DISPLAY / 2
                size = MAX_PAGE_ITEM_DISPLAY
            }
        }
        for (i in 0 until size) {
            items.add(PageItem(start + i, start + i == number))
        }
    }
}