package com.example.kotlinstory.services

import com.example.kotlinstory.models.Story
//todo may not use
interface StoryService {
    fun findAll(): List<Story>

    fun findStoryById(id: Long): Story

    fun findByUserName(username: String): List<Story>

    fun delete(id: Long)

    fun save(quote: Story): Story

    fun update(id:Long, quote: Story)
}
