package ru.ifmo.volunteer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp startDate;

    private Timestamp endDate;

    private Long volunteersRequired;

    private Long volunteersPresent;

    private String photoLink;

    private String title;

    private String description;

    private String linkToEvent;

    private Boolean finished;

    private Long requiredRating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Long getVolunteersRequired() {
        return volunteersRequired;
    }

    public void setVolunteersRequired(Long volunteersRequired) {
        this.volunteersRequired = volunteersRequired;
    }

    public Long getVolunteersPresent() {
        return volunteersPresent;
    }

    public void setVolunteersPresent(Long volunteersPresent) {
        this.volunteersPresent = volunteersPresent;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkToEvent() {
        return linkToEvent;
    }

    public void setLinkToEvent(String linkToEvent) {
        this.linkToEvent = linkToEvent;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getRequiredRating() {
        return requiredRating;
    }

    public void setRequiredRating(Long requiredRating) {
        this.requiredRating = requiredRating;
    }

}
