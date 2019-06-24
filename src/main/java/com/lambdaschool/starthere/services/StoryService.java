package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Story;

import java.util.List;

public interface StoryService
{
    List<Story> findAll();

    Story findQuoteById(long id);

    List<Story> findByUserName(String username);

    void delete(long id);

    Story save(Story story);
}
