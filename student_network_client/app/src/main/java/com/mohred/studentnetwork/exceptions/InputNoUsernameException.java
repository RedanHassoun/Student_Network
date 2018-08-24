package com.mohred.studentnetwork.exceptions;
/**
 * Created by Redan on 11/16/2016.
 */

public class InputNoUsernameException extends InputProblemException
{
    public InputNoUsernameException(){}
    public InputNoUsernameException(String message)
    {
        super(message);
    }
}
