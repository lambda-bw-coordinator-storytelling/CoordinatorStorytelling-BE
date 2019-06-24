package com.example.kotlinstory.services

import com.example.kotlinstory.exceptions.ResourceNotFoundException
import com.example.kotlinstory.models.Story
import com.example.kotlinstory.repository.StoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException
import java.util.ArrayList
import java.util.function.Consumer
@Service(value = "storyService")
open class StoryServiceImpl : StoryService {
    @Autowired
    private val storyrepo: StoryRepository? = null

    override fun findAll(): List<Story> {
        val list = ArrayList<Story>()
        storyrepo!!.findAll().iterator().forEachRemaining(Consumer<Story> { list.add(it) })
        return list
    }

    override fun findStoryById(id: Long): Story {
        return storyrepo!!.findById(id)
                .orElseThrow { EntityNotFoundException(id.toString()) }
    }

    override fun delete(id: Long) {
        if (storyrepo!!.findById(id).isPresent) {
            val authentication = SecurityContextHolder.getContext().authentication
            if (storyrepo.findById(id).get().user!!.username!!.equals(authentication.name, ignoreCase = true)) {
                storyrepo.deleteById(id)
            } else {
                throw EntityNotFoundException(id.toString() + " " + authentication.name)
            }
        } else {
            throw EntityNotFoundException(id.toString())
        }
    }

    @Transactional
    override fun save(quote: Story): Story {
        return storyrepo!!.save(quote)
    }

    override fun findByUserName(username: String): List<Story> {
        val list = ArrayList<Story>()
        storyrepo!!.findAll().iterator().forEachRemaining(Consumer<Story> { list.add(it) })

        list.removeIf { q -> !q.user!!.username!!.equals(username, ignoreCase = true) }
        return list
    }

    override fun update(id: Long, book: Story) {
        val currentBook = storyrepo!!.findById(id)
                .orElseThrow { ResourceNotFoundException("Cannot find book id$id") }

        if (book.title != null) {
            currentBook.title = book.title
        }

        if (book.country != null) {
            currentBook.country = book.country
        }

        if (book.description != null) {
            currentBook.description = book.description
        }
        //todo will need to change if date is different type (other than string)
        if (book.date != null) {
            currentBook.date = book.date
        }


        storyrepo!!.save(currentBook)
    }
}
