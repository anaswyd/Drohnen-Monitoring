package de;

public class HelloWorld {
    public static void main(String[] args) {
        String s1="Hello World";

        for(char c:s1.toCharArray()){
            if(c!='l'){
                System.out.print(c);
            }
        }
    }
}
