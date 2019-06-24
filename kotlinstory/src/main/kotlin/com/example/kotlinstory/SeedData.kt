package com.example.kotlinstory

import com.example.kotlinstory.models.Story
import com.example.kotlinstory.models.Role
import com.example.kotlinstory.models.User
import com.example.kotlinstory.models.UserRoles
import com.example.kotlinstory.services.RoleService
import com.example.kotlinstory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import java.util.ArrayList
//todo change the seed data to match the needed information
@Transactional
@Component
open class SeedData : CommandLineRunner {
    @Autowired
    internal var roleService: RoleService? = null

    @Autowired
    internal var userService: UserService? = null

    @Throws(Exception::class)
    override fun run(args: Array<String>) {
        val adminRole = Role("admin")
        val userRole = Role("user")
        val dataRole = Role("data")

        roleService!!.save(adminRole)
        roleService!!.save(userRole)
        roleService!!.save(dataRole)

        // admin, data, user
        val admins = ArrayList<UserRoles>()
        admins.add(UserRoles(User(), adminRole))
        admins.add(UserRoles(User(), userRole))
        admins.add(UserRoles(User(), dataRole))
        val u1 = User("admin", "password", admins)

//        constructor(quote: String,country: String, description: String, content:String, date:String,  user: User)
        (u1.stories as MutableList).add(Story("fakequote1","Bolivia","fakequoteDescription1" ,"A creative man is motivated by the desire to achieve, not by the desire to beat others","january 1 2001", u1))
        (u1.stories as MutableList).add(Story("fakequote2","US of A","fakequoteDescription2" ,"2 creative men is motivated by the desire to achieve, not by the desire to beat others","january 1 2001", u1))
        userService!!.save(u1)

        // data, user
        val datas = ArrayList<UserRoles>()
        datas.add(UserRoles(User(), dataRole))
        datas.add(UserRoles(User(), userRole))
        val u2 = User("data", "password", datas)
        userService!!.save(u2)

        // user
        val users = ArrayList<UserRoles>()
        users.add(UserRoles(User(), userRole))
        val u3 = User("user", "password", users)
//        (u3.stories as MutableList).add(Story("fake quote title", "Argentina", "fake description","Live long and prosper","fake date", u3))
//        (u3.stories as MutableList).add(Story("fake quote title", "Argentina", "fake description","The enemy of my enemy is the enemy I kill last","fake date", u3))
//        (u3.stories as MutableList).add(Story("fake quote title", "Argentina", "fake description","Beam me up","fake date", u3))
        userService!!.save(u3)


    }
}
