package com.example.kotlinstory.services

import com.example.kotlinstory.models.Role
//todo need to make sure this is correct and who can do it
interface RoleService {
    fun findAll(): List<Role>

    fun findRoleById(id: Long): Role

    fun delete(id: Long)

    fun save(role: Role): Role

    fun findByName(name: String): Role
}