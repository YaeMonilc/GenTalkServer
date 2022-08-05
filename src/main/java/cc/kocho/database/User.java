package cc.kocho.database;

import dev.morphia.annotations.Entity;

@Entity
public class User {

    private int id;
    public String account;
    public String password;
    public String name;
    public String token;

    public User(String account,String password,String name){
        this.account = account;
        this.password = password;
        this.name = name;
        this.token = "";

    }

}
