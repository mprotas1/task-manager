package com.protas.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name ="is_completed", nullable = false)
    private boolean isCompleted;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "task_priority")
    @Range(min = 0, max = 10, message = "The value should be between 0 and 10")
    private Integer taskPriority;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {

    }

    public Task(String title, String content) {
        this.title = title;
        this.content = content;
        this.isCompleted = false;
        this.creationTime = LocalDateTime.now();
    }

    public Task(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isCompleted = false;
        this.creationTime = LocalDateTime.now();
    }

    public Task(String title, String content, boolean isCompleted, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isCompleted = isCompleted;
        this.creationTime = LocalDateTime.now();
    }
}
