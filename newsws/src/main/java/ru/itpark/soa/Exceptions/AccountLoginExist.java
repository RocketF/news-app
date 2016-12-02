package ru.itpark.soa.Exceptions;

/**
 * Created by rocketf on 02.12.16.
 */
public class AccountLoginExist extends Exception {
   public AccountLoginExist(String name){
        super(name);
    }
}
