package com.example.kotlinstory.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.persistence.*
import java.util.ArrayList
//todo need to figure out the exact role style
@Entity
@Table(name = "roles")
class Role : Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var roleid: Long = 0

    @Column(nullable = false, unique = true)
    var name: String? = null

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("role")
    var userRoles: List<UserRoles> = mutableListOf()

    constructor() {}

    constructor(name: String) {
        this.name = name
    }
}
