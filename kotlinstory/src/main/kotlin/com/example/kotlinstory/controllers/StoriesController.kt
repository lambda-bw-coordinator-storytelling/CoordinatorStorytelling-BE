package com.example.kotlinstory.controllers
import com.example.kotlinstory.models.Story
import com.example.kotlinstory.services.StoryService
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
import java.net.URISyntaxException

@RestController
@RequestMapping("/stories")
class StoriesController {

    @Autowired
    internal var storyService: StoryService? = null

    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun listAllStories(request: HttpServletRequest): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val allStorys = storyService!!.findAll()
        return ResponseEntity(allStorys, HttpStatus.OK)
    }


    @GetMapping(value = ["/{quoteId}"], produces = ["application/json"])
    fun getStory(request: HttpServletRequest, @PathVariable
    quoteId: Long?): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val q = storyService!!.findStoryById(quoteId!!)
        return ResponseEntity(q, HttpStatus.OK)
    }


/*
    @GetMapping(value = ["/username/{userName}"], produces = ["application/json"])
    fun findStoryByUserName(request: HttpServletRequest, @PathVariable
    userName: String): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val theStorys = storyService!!.findByUserName(userName)
        return ResponseEntity(theStorys, HttpStatus.OK)
    }
*/

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = ["/story"])
    @Throws(URISyntaxException::class)
    fun addNewStory(request: HttpServletRequest, @Valid
    @RequestBody
    newStory: Story): ResponseEntity<*> {
        var newStory = newStory
        logger.trace(request.requestURI + " accessed")

        newStory = storyService!!.save(newStory)

        // set the location header for the newly created resource
        val responseHeaders = HttpHeaders()
        val newStoryURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{quoteid}")
                .buildAndExpand(newStory.storiesid)
                .toUri()
        responseHeaders.location = newStoryURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }

    @DeleteMapping("/story/{id}")
    fun deleteStoryById(request: HttpServletRequest, @PathVariable
    id: Long): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        storyService!!.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
    @PutMapping("/story/{id}")
    fun updateBook(@PathVariable("id") id: Long, @RequestBody story: Story): ResponseEntity<*> {
        storyService!!.update(id, story)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RolesController::class.java)
    }
}
