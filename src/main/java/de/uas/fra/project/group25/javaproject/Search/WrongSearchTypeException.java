package de.uas.fra.project.group25.javaproject.Search;

public class WrongSearchTypeException extends RuntimeException{
    public WrongSearchTypeException(String message){
        super("Wrong search type used: " + message);
    }
}
