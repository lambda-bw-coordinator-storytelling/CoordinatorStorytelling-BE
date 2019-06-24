package com.example.kotlinstory.controllers

import com.example.kotlinstory.models.User
import com.example.kotlinstory.models.UserRoles
import com.example.kotlinstory.services.RoleService
import com.example.kotlinstory.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException
import java.util.ArrayList

@RestController
class OpenController {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val roleService: RoleService? = null

    @PostMapping(value = ["/createnewuser"], consumes = ["application/json"], produces = ["application/json"])
    @Throws(URISyntaxException::class)
    fun addNewUser(request: HttpServletRequest, @Valid
    @RequestBody
    newuser: User): ResponseEntity<*> {
        var newuser = newuser
        logger.trace(request.requestURI + " accessed")

        val newRoles = ArrayList<UserRoles>()
        newRoles.add(UserRoles(newuser, roleService!!.findByName("user")))
        newuser.userRoles = newRoles

        newuser = userService!!.save(newuser)

        // set the location header for the newly created resource - to another controller!
        val responseHeaders = HttpHeaders()
        val newRestaurantURI = ServletUriComponentsBuilder
                .fromUriString(request.serverName + ":" + request.localPort + "/users/user/{userId}")
                .buildAndExpand(newuser.userid).toUri()
        responseHeaders.location = newRestaurantURI


        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RolesController::class.java)
    }

}
