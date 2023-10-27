package lexer; //pour le mettre dans le package de l'analyseur lexical

import java.io.*;
import java.util.*;

public class Lexer {

    //compteur de ligne
    private int line=1;
    //compteur de caractere
    private int nbChar=0;
    //la ligne en cours
    private String currentLine="";
    //le caractere que l on lit
    private char caractere=' ';
    //prochain caractere a lire
    private char prochain=' ';
    // Table des string, pour gerer les mots cles et identifiants. Utilisation d une table de hashage
    private HashMap<String, Mots> mots=new HashMap<String, Mots>();
    //Pour gerer les erruers, ie les stocker et les envoyer
    public ErrorManager errorManager = new ErrorManager();

    //permet de mettre les tokens des mots cles dans la table des strings
    void reserve(Mots t){
        mots.put(t.lexeme, t);
    }

    //on initialise la table des strings
    public Lexer(){
        reserve(new Mots(Tag.TRUE, "true"));
        reserve(new Mots(Tag.FALSE, "false"));
        //a continuer avec les autres mots cles
    }

    public int getLine(){
        return(this.line);
    }

    public int getNbChar(){
        return(this.nbChar);
    }

    public String getCurrentLine(){
        return(this.currentLine);
    }

    //pour lire le prochain caractere
    public void read() throws IOException{
        this.nbChar+=1;
        this.caractere=(char)System.in.read();
        //on incremente le compteur de ligne si on a un saut de ligne, utile pour la gestion de bug
        if(this.caractere=='\n'){
            errorManager.throwErrorsLexer(this);//On renvoie toutes les erreurs
            this.line+=1;//incremente le nombre de ligne
            this.nbChar=0;//Le compteur de caractere repart au debut
            currentLine="";//La ligne courante se reinitialise
        }
        else{//On rajoute le caractere lu a la ligne courante
            this.currentLine+=this.caractere;
        }
    }

    public int scan() throws IOException{
        
        //suppression des espaces, tabulation et les commentaires

        //on remet caractere a la bonne valeur si jamais on a lut 1 caractere en avance a un moment
        if(this.prochain!=' '){
            this.caractere=this.prochain;
        }
        else{
            read();
        }

        while(this.caractere<=32 || this.caractere =='-'){

            //on gere les commenentaires
            if(this.caractere=='-'){
                read();
                if(this.caractere=='-'){
                    //detection du debut du commentaire
                    //tant que les deux curseurs n ont pas des "-", on continue a skip
                    while(this.caractere!='\n'){
                        //on continue a skip
                        read();
                    }
                    //quand on a fini de traiter les commentaires, on remet les curseurs comme il faut
                    this.line+=1;
                    read();
                }
                //si ce n est pas un debut de commentaire, on remet le curseur comme il faut
                else{
                    this.prochain=this.caractere;
                    this.caractere='-';
                }
            }
        
            //on avance
            read();
        }

        //on teste si on a un nombre (automate entier)

        //on teste si on a un identifiant (automate ident)
        /*
        if(Automate.estIdent(caractere)){
            String s;//le mot qu'on a recconu avec l'automate//
            Mots w=(Mots)mots.get(s);//on recupere sa valeur dans la table des strings//
            if(w!=null){
                return w;//si il est dans la table, on a pas a le traiter plus//
            }
            w=new Mots(Tag.IDENT, s);//si il n est pas dans la table, on cree le token associe//
            mots.put(s,w);//et on le met dans la table//
            return w;
        }*/
        
        //on teste si on a un symbole (automate symbole)

        //on teste si on a un caractere (automate caractere)

        //Si on arrive en fin du fichier
        if((int)this.caractere==65535){
            Token t=new Token('$');//token fin de texte
            errorManager.throwErrorsLexer(this);//On renvoie toutes les erreurs
            return 1;
        }
        //si on a pas reconnu le caractere
        else{
            //sauvegarde du message d erreur
            errorManager.saveError(this.line, this.nbChar, "Le caractere " + this.caractere + " n'est pas reconnu");
        }
        return 0;
    }
}