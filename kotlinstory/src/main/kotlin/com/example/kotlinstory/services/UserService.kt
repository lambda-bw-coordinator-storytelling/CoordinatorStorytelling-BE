package com.example.kotlinstory.services

import com.example.kotlinstory.models.User
// todo should be able to keep the same, as far as who can use it not sure yet
interface UserService {

    fun findAll(): List<User>

    fun findUserById(id: Long): User

    fun delete(id: Long)

    fun save(user: User): User

    fun update(user: User, id: Long): User
}