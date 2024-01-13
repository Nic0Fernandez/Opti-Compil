package tests;

import java.io.IOException;

import parse_tree.ArbreSyntaxique;
import parser.*;

public class TestParseur {
    public static void main(String[] arg) throws IOException{
        Grammaire g=new GrammaireTest();
        ArbreSyntaxique arbreSyntaxique=g.analyse();
        arbreSyntaxique.showAsAbstractTree(arbreSyntaxique.getRacine(), "",true);
    }
}
