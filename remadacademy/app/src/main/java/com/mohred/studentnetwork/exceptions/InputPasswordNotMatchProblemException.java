package com.mohred.studentnetwork.exceptions;

/**
 * Created by Redan on 11/16/2016.
 */

public class InputPasswordNotMatchProblemException extends InputProblemException
{
    public InputPasswordNotMatchProblemException(){}
    public InputPasswordNotMatchProblemException(String message)
    {
        super(message);
    }
}
