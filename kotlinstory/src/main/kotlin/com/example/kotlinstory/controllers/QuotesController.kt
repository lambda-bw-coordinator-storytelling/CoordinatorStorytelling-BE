package com.example.kotlinstory.controllers
import com.example.kotlinstory.models.Quote
import com.example.kotlinstory.services.QuoteService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException

@RestController
@RequestMapping("/stories")
class QuotesController {

    @Autowired
    internal var quoteService: QuoteService? = null

    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun listAllQuotes(request: HttpServletRequest): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val allQuotes = quoteService!!.findAll()
        return ResponseEntity(allQuotes, HttpStatus.OK)
    }


    @GetMapping(value = ["/{quoteId}"], produces = ["application/json"])
    fun getQuote(request: HttpServletRequest, @PathVariable
    quoteId: Long?): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val q = quoteService!!.findQuoteById(quoteId!!)
        return ResponseEntity(q, HttpStatus.OK)
    }


/*
    @GetMapping(value = ["/username/{userName}"], produces = ["application/json"])
    fun findQuoteByUserName(request: HttpServletRequest, @PathVariable
    userName: String): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val theQuotes = quoteService!!.findByUserName(userName)
        return ResponseEntity(theQuotes, HttpStatus.OK)
    }
*/

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = ["/story"])
    @Throws(URISyntaxException::class)
    fun addNewQuote(request: HttpServletRequest, @Valid
    @RequestBody
    newQuote: Quote): ResponseEntity<*> {
        var newQuote = newQuote
        logger.trace(request.requestURI + " accessed")

        newQuote = quoteService!!.save(newQuote)

        // set the location header for the newly created resource
        val responseHeaders = HttpHeaders()
        val newQuoteURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{quoteid}")
                .buildAndExpand(newQuote.quotesid)
                .toUri()
        responseHeaders.location = newQuoteURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }

    @DeleteMapping("/story/{id}")
    fun deleteQuoteById(request: HttpServletRequest, @PathVariable
    id: Long): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        quoteService!!.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
    @PutMapping("/story/{id}")
    fun updateBook(@PathVariable("id") id: Long, @RequestBody story: Quote): ResponseEntity<*> {
        quoteService!!.update(id, story)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RolesController::class.java)
    }
}
