package com.example.kotlinstory.repository

import com.example.kotlinstory.models.Role
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
//todo not sure if I will need this to change
interface RoleRepository : CrudRepository<Role, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE from UserRoles where userid = :userid")
    fun deleteUserRolesByUserId(userid: Long)

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UserRoles(userid, roleid) values (:userid, :roleid)", nativeQuery = true)
    fun insertUserRoles(userid: Long, roleid: Long)

    fun findByNameIgnoreCase(name: String): Role
}
