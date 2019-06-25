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
}
