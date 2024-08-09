package com.robert.googlefirestroredemo.Entity;

public record User(
        String username,
        String firstname,
        String lastName,
        String email,
        int password
) {
}
