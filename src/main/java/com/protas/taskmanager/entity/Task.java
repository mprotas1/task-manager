package com.protas.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "task")
@Getter
@Setter
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
    }

    public Task(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isCompleted = false;
    }

    public Task(String title, String content, boolean isCompleted, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isCompleted = isCompleted;
    }
}
