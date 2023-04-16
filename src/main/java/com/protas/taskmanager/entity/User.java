package com.protas.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotBlank
    @Size(min = 1, max = 255)
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String username;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }
}
