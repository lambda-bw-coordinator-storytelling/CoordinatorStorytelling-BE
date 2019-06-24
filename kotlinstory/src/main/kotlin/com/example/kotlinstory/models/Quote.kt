package com.example.kotlinstory.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.persistence.*
//todo dont even know if we are going to use the quote class
@Entity
@Table(name = "quotes")
class Quote : Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var quotesid: Long = 0

    @Column(nullable = false)
    var title: String? = null

    @Column(nullable = false)
    var country: String? = null

    @Column(nullable = false)
    var description: String? = null

    @Column(nullable = false)
    var content:String?= null

    @Column(nullable = false)
    var date: String? = null


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties("quotes", "hibernateLazyInitializer")
    var user: User? = null

    constructor() {}

    constructor(title: String, country: String, description: String, content:String, date:String, user: User) {
        this.title = title
        this.user = user
        this.country = country
        this.description = description
        this.content = content
        this.date = date
    }

    constructor(title: String?, country: String?, description: String?, content: String?, date: String?) : super() {
        this.title = title
        this.country = country
        this.description = description
        this.content = content
        this.date = date
    }
}