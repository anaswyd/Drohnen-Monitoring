package de;

import java.util.Scanner;

public class CompareDigits {
    int zahl1, zahl2;

    public void zahlenEinlesen(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Geben sie eine Zahl ein:");
        this.zahl1=scanner.nextInt();
        System.out.println("Geben sie eine weitere Zahl ein:");
        this.zahl2=scanner.nextInt();
        scanner.close();
    }

    public boolean zahlenVergleich(){
        this.zahl1=this.zahl1 %100;
        this.zahl2=this.zahl2 %100;

        int zahl1Zehner=this.zahl1/10;
        int zahl1Einer=this.zahl1%10;
        int zahl2Zehner=this.zahl2/10;
        int zahl2Einer=this.zahl2%10;

        boolean gemeinsameZiffer=(zahl1Zehner==zahl2Zehner || zahl1Zehner==zahl2Einer || zahl1Einer==zahl2Einer || zahl1Einer==zahl2Zehner);
        return gemeinsameZiffer;
    }



    public static void main(String[] args) {
        CompareDigits compareNumbers=new CompareDigits();
        compareNumbers.zahlenEinlesen();
        boolean gemeinsameZiffer=compareNumbers.zahlenVergleich();
        System.out.println("Gibt es Übereinstimmungen zwischen den Ziffern: " + gemeinsameZiffer);

    }
}
