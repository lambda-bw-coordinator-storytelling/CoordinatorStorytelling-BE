package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "stories")
public class Story extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long storiesid;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
                nullable = false)
    @JsonIgnoreProperties({"quotes", "hibernateLazyInitializer"})
    private User user;

    public Story()
    {
    }

    public Story(String title, String country, String description, String content, String date, User user) {
        this.title = title;
        this.country = country;
        this.description = description;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public Story(String title, String country, String description, String content, String date) {
        this.title = title;
        this.country = country;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public long getStoriesid() {
        return storiesid;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}