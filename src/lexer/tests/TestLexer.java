package lexer;

import java.io.*;
import java.util.*;

public class TestLexer{

    public static void main(String[] arg) throws IOException{
        Lexer lexer = new Lexer();
        int fin = 0;
        while(fin==0){
            fin = lexer.scan();
        }
    }
}