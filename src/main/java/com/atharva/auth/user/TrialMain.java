package com.atharva.auth.user;

import java.util.Base64;

public class TrialMain {
    public static void main(String[] args) {
        System.out.println("user: " + Base64.getEncoder().encodeToString("user".getBytes()));
        System.out.println("project1: " + Base64.getEncoder().encodeToString("project1".getBytes()));
        System.out.println("pass: " + Base64.getEncoder().encodeToString("123456789".getBytes()));
    }
}
