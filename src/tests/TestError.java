package tests;

import lexer.*;
import lexer.Error;

public class TestError {

public static void main(String[] arg){
        Error error = new Error(("this is an error"));
        ErrorManager errorManager = new ErrorManager();
        error.throwError();
        errorManager.saveError("message");
        errorManager.throwErrors();
    }
}
