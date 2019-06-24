package com.example.kotlinstory.repository

import com.example.kotlinstory.models.Story
import org.springframework.data.repository.CrudRepository
interface StoryRepository : CrudRepository<Story, Long>
