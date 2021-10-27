package ru.job4j.todo.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String description;

    @Column(unique = true)
    private final Timestamp created = new Timestamp(System.currentTimeMillis());

    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    public Item() {
    }

    public Item(String description, int userId) {
        this.description = description;
        this.user = User.of(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
