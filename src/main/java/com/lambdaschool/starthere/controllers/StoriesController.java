package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Story;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.repository.UserRepository;
import com.lambdaschool.starthere.services.StoryService;
import com.lambdaschool.starthere.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/stories")
public class StoriesController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    StoryService storyService;
    @Autowired
    UserRepository userrepo;



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


    @GetMapping(value = "/random",
            produces = {"application/json"})
    public ResponseEntity<?> RandomStory(HttpServletRequest request)
    {
        logger.trace(request.getRequestURI() + " accessed");

        List<Story> allStories = storyService.findAll();
        int max = allStories.size();
        int min = 0;
        Random rand = new Random();
        Story story = allStories.get(rand.nextInt(max));
        return new ResponseEntity<>(story, HttpStatus.OK);
    }



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
    @GetMapping(value = "/mine", produces = {"application/json"})
    public ResponseEntity<?> getMyStories(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Story> theStories = storyService.findByUserName(authentication.getName());
        return new ResponseEntity<>(theStories, HttpStatus.OK);
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/story")
    public ResponseEntity<?> addNewStory(HttpServletRequest request, @Valid
    @RequestBody
            Story newStory) throws URISyntaxException
    {
        logger.trace(request.getRequestURI() + " accessed");
        if(newStory.getUser() == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            newStory.setUser(userrepo.findByUsername(username));
        }
        newStory = storyService.save(newStory);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newQuoteURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{quoteid}").buildAndExpand(newStory.getStoriesid()).toUri();
        responseHeaders.setLocation(newQuoteURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    @PutMapping(value = "/story/update/{storyid}",produces = {"application/json"},consumes = {"application/json"})
    public ResponseEntity<?> updatestory(@PathVariable long storyid, @RequestBody Story story){
        return new ResponseEntity<>(storyService.update(story, storyid), HttpStatus.OK);
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
