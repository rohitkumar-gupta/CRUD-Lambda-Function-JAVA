package com.rohit.exceptions;

public class userExceptions extends Exception{
    public userExceptions(String message)
    {
        super(message);
    }
    public void AlreadyExistsException()
    {
        new userExceptions("This employee ID already exists");
    }
    public void UserNotFound()
    {
        new userExceptions("No user found ");
    }
    public void invalidInputException()
    {
        new userExceptions("Please check your input");
    }

}
