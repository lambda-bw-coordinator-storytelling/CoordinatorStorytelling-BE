package com.example.kotlinstory.repository

import com.example.kotlinstory.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User
}
