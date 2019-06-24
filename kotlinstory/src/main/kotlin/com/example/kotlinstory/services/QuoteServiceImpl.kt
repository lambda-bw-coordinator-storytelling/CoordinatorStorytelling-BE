package com.example.kotlinstory.services

import com.example.kotlinstory.exceptions.ResourceNotFoundException
import com.example.kotlinstory.models.Quote
import com.example.kotlinstory.repository.QuoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException
import java.util.ArrayList
import java.util.function.Consumer
//todo may not use at all
@Service(value = "quoteService")
open class QuoteServiceImpl : QuoteService {
    @Autowired
    private val quoterepos: QuoteRepository? = null

    override fun findAll(): List<Quote> {
        val list = ArrayList<Quote>()
        quoterepos!!.findAll().iterator().forEachRemaining(Consumer<Quote> { list.add(it) })
        return list
    }

    override fun findQuoteById(id: Long): Quote {
        return quoterepos!!.findById(id)
                .orElseThrow { EntityNotFoundException(id.toString()) }
    }

    override fun delete(id: Long) {
        if (quoterepos!!.findById(id).isPresent) {
            val authentication = SecurityContextHolder.getContext().authentication
            if (quoterepos.findById(id).get().user!!.username!!.equals(authentication.name, ignoreCase = true)) {
                quoterepos.deleteById(id)
            } else {
                throw EntityNotFoundException(id.toString() + " " + authentication.name)
            }
        } else {
            throw EntityNotFoundException(id.toString())
        }
    }

    @Transactional
    override fun save(quote: Quote): Quote {
        return quoterepos!!.save(quote)
    }

    override fun findByUserName(username: String): List<Quote> {
        val list = ArrayList<Quote>()
        quoterepos!!.findAll().iterator().forEachRemaining(Consumer<Quote> { list.add(it) })

        list.removeIf { q -> !q.user!!.username!!.equals(username, ignoreCase = true) }
        return list
    }

    override fun update(id: Long, book: Quote) {
        val currentBook = quoterepos!!.findById(id)
                .orElseThrow { ResourceNotFoundException("Cannot find book id$id") }

        if (book.quote != null) {
            currentBook.quote = book.quote
        }

        if (book.country != null) {
            currentBook.country = book.country
        }

        if (book.description != null) {
            currentBook.description = book.description
        }
        //todo will need to change if date is different type (other than string)
        if (book.date != null) {
            currentBook.date = book.date
        }


        quoterepos!!.save(currentBook)
    }
}
