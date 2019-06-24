package com.example.kotlinstory.controllers
import com.example.kotlinstory.models.User
import com.example.kotlinstory.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private val userService: UserService? = null

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = ["/users"], produces = ["application/json"])
    fun listAllUsers(request: HttpServletRequest): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val myUsers = userService!!.findAll()
        return ResponseEntity(myUsers, HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = ["/user/{userId}"], produces = ["application/json"])
    fun getUser(request: HttpServletRequest, @PathVariable userId: Long?): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        val u = userService!!.findUserById(userId!!)
        return ResponseEntity(u, HttpStatus.OK)
    }


    @GetMapping(value = ["/getusername"], produces = ["application/json"])
    @ResponseBody
    fun getCurrentUserName(request: HttpServletRequest, authentication: Authentication): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        return ResponseEntity(authentication.principal, HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = ["/user"], consumes = ["application/json"], produces = ["application/json"])
    @Throws(URISyntaxException::class)
    fun addNewUser(request: HttpServletRequest, @Valid @RequestBody newuser: User): ResponseEntity<*> {
        var newuser = newuser
        logger.trace(request.requestURI + " accessed")

        newuser = userService!!.save(newuser)

        // set the location header for the newly created resource
        val responseHeaders = HttpHeaders()
        val newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.userid)
                .toUri()
        responseHeaders.location = newUserURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = ["/user/{id}"])
    fun updateUser(request: HttpServletRequest, @RequestBody updateUser: User, @PathVariable id: Long): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        userService!!.update(updateUser, id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    fun deleteUserById(request: HttpServletRequest, @PathVariable id: Long): ResponseEntity<*> {
        logger.trace(request.requestURI + " accessed")

        userService!!.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}