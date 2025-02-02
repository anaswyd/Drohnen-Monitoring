package de;

public class CaptainTask {
    public static void main (String[] args) {
        System.out.println("Number of bottles in total?");
        int anzahlBottles= new java.util.Scanner(System.in).nextInt();

        int bottlesCaptain,bottlesCrewMember;
        if(anzahlBottles%2!=0){
            bottlesCaptain=anzahlBottles/2-1;
            bottlesCrewMember=anzahlBottles-bottlesCaptain;
        }
        else{
            bottlesCaptain=anzahlBottles/2;
            bottlesCrewMember=anzahlBottles-bottlesCaptain;
        }
        System.out.println("Bottles captain: "+bottlesCaptain);
        System.out.println("Bottles crew member: "+bottlesCrewMember);
        System.out.println("Number of crewMembers: ");
        int anzahlCrewMembers=new java.util.Scanner(System.in).nextInt();
        boolean fairShare=bottlesCrewMember%anzahlCrewMembers==0;
        System.out.println("Fair share without remainder?"+fairShare);

    }
}
