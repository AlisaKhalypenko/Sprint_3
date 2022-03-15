package com.ya;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier(){

    }

    public Courier(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin (String login){
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName (String firstName){
        this.firstName = firstName;
    }


}
