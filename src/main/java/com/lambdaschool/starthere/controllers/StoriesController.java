package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Story;
import com.lambdaschool.starthere.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoriesController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    StoryService storyService;

    @GetMapping(value = "/all",
                produces = {"application/json"})
    public ResponseEntity<?> listAllQuotes(HttpServletRequest request)
    {
        logger.trace(request.getRequestURI() + " accessed");

        List<Story> allStories = storyService.findAll();
        return new ResponseEntity<>(allStories, HttpStatus.OK);
    }
//Todo: add a Put Mapping
    //Todo: figure out JSON format for putting/posting

    @GetMapping(value = "/{storyId}",
                produces = {"application/json"})
    public ResponseEntity<?> getQuote(HttpServletRequest request,
                                      @PathVariable
                                              Long storyId)
    {
        logger.trace(request.getRequestURI() + " accessed");

        Story q = storyService.findQuoteById(storyId);
        return new ResponseEntity<>(q, HttpStatus.OK);
    }


    @GetMapping(value = "/username/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> findQuoteByUserName(HttpServletRequest request,
                                                 @PathVariable
                                                         String userName)
    {
        logger.trace(request.getRequestURI() + " accessed");

        List<Story> theStories = storyService.findByUserName(userName);
        return new ResponseEntity<>(theStories, HttpStatus.OK);
    }


    @PostMapping(value = "/story")
    public ResponseEntity<?> addNewQuote(HttpServletRequest request, @Valid
    @RequestBody
            Story newStory) throws URISyntaxException
    {
        logger.trace(request.getRequestURI() + " accessed");

        newStory = storyService.save(newStory);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newQuoteURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{quoteid}").buildAndExpand(newStory.getStoriesid()).toUri();
        responseHeaders.setLocation(newQuoteURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @DeleteMapping("/story/{id}")
    public ResponseEntity<?> deleteQuoteById(HttpServletRequest request,
                                             @PathVariable
                                                     long id)
    {
        logger.trace(request.getRequestURI() + " accessed");

        storyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
