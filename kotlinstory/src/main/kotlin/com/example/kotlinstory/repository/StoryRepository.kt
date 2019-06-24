package com.example.kotlinstory.repository

import com.example.kotlinstory.models.Quote
import org.springframework.data.repository.CrudRepository
//todo are we even using quotes or something that I can point here instead?
interface StoryRepository : CrudRepository<Quote, Long>
