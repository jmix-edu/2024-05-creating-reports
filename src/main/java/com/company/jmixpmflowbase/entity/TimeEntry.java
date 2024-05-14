package com.company.jmixpmflowbase.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "TIME_ENTRY", indexes = {
        @Index(name = "IDX_TIME_ENTRY_TASK", columnList = "TASK_ID"),
        @Index(name = "IDX_TIME_ENTRY_USER", columnList = "USER_ID")
})
@Entity
public class TimeEntry {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @JoinColumn(name = "TASK_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Task task;

    @NotNull
    @Column(name = "TIME_SPENT", nullable = false)
    private Integer timeSpent;

    @Column(name = "ENTRY_DATE")
    private LocalDateTime entryDate;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @InstanceName
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "APPROVED", nullable = false)
    @NotNull
    private Boolean approved = false;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}