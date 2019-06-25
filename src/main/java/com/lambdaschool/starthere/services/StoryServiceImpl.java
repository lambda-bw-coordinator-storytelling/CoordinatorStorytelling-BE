package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Story;
import com.lambdaschool.starthere.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "storyService")
public class StoryServiceImpl implements StoryService
{
    @Autowired
    private StoryRepository storyRepository;

    @Override
    public List<Story> findAll()
    {
        List<Story> list = new ArrayList<>();
        storyRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Story findQuoteById(long id)
    {
        return storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (storyRepository.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (storyRepository.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                storyRepository.deleteById(id);
            } else
            {
                throw new ResourceNotFoundException(id + " " + authentication.getName());
            }
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Story save(Story story)
    {
        return storyRepository.save(story);
    }

    @Override
    public List<Story> findByUserName(String username)
    {
        List<Story> list = new ArrayList<>();
        storyRepository.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(q -> !q.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }
    @Transactional
    @Override
    public Story update(Story story, Long id)
    {
        Story currentStory = storyRepository.findById(id).orElseThrow();
        if (story.getTitle() != null){
            currentStory.setTitle(story.getTitle());
        }
        if (story.getCountry() != null){
            currentStory.setCountry(story.getCountry());
        }
        if (story.getDescription() != null){
            currentStory.setDescription(story.getDescription());
        }
        if (story.getContent() != null){
            currentStory.setContent(story.getContent());
        }
        if (story.getDate() != null){
            currentStory.setDate(story.getDate());
        }
        if (story.getUser() != null){
            currentStory.setUser(story.getUser());
        }

        return storyRepository.save(story);
    }


}
