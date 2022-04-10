package com.savita.login;

public class User {
    String name;
    String email;
    String password;

    public User (){};
    public User(String name,String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }


    public String getDisplayname() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return password;
    }
}
