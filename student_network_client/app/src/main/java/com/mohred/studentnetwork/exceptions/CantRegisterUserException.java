package com.mohred.studentnetwork.exceptions;

/**
 * Created by Redan on 11/20/2016.
 */

public class CantRegisterUserException extends Exception
{
    public CantRegisterUserException(){}
    public CantRegisterUserException(String message)
    {
        super(message);
    }
}