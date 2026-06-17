package io.github.tusharnagdive.codekit.model;

import java.util.Objects;

public class Users {
    Integer id;
    String name;
    String email;
    String username;

    public Users(Integer id, String name, String email, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
    }

    public Users() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("User=[{id=%d, name='%s', email='%s', username='%s'}]", id, name, email, username);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Users users)) return false;
        return Objects.equals(id, users.id) && Objects.equals(name, users.name) && Objects.equals(email, users.email) && Objects.equals(username, users.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, username);
    }
}
