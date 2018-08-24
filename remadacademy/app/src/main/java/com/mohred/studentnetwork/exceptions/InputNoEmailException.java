package com.mohred.studentnetwork.exceptions;

/**
 * Created by Redan on 11/16/2016.
 */

public class InputNoEmailException extends InputProblemException
{
    public InputNoEmailException(){}
    public InputNoEmailException(String message)
    {
        super(message);
    }
}
