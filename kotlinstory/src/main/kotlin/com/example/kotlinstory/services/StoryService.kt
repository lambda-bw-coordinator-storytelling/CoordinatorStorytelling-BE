package com.example.kotlinstory.services

import com.example.kotlinstory.models.Quote
//todo may not use
interface StoryService {
    fun findAll(): List<Quote>

    fun findQuoteById(id: Long): Quote

    fun findByUserName(username: String): List<Quote>

    fun delete(id: Long)

    fun save(quote: Quote): Quote

    fun update(id:Long, quote: Quote)
}
