package com.example.kotlinstory.services

import com.example.kotlinstory.models.Role
import com.example.kotlinstory.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException
import java.util.ArrayList
import java.util.function.Consumer
// todo make sure this is correct as far as who can do what
@Service(value = "roleService")
class RoleServiceImpl : RoleService {
    @Autowired
    internal var rolerepos: RoleRepository? = null

    override fun findAll(): List<Role> {
        val list = ArrayList<Role>()
        rolerepos!!.findAll().iterator().forEachRemaining(Consumer<Role> { list.add(it) })
        return list
    }


    override fun findRoleById(id: Long): Role {
        return rolerepos!!.findById(id).orElseThrow { EntityNotFoundException(id.toString()) }
    }

    override fun findByName(name: String): Role {
        val rr = rolerepos!!.findByNameIgnoreCase(name)

        return rr ?: throw EntityNotFoundException(name)
    }

    override fun delete(id: Long) {
        rolerepos!!.findById(id).orElseThrow { EntityNotFoundException(id.toString()) }
        rolerepos!!.deleteById(id)
    }


    @Transactional
    override fun save(role: Role): Role {
        return rolerepos!!.save(role)
    }
}
