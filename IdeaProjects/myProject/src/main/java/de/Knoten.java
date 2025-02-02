package de;

public class Knoten {
    int value;
    Knoten next;

    public Knoten(int newElement) {
        this.value=newElement;
        this.next=null;
    }
}

  class linkedList {
    public Knoten head;

    public linkedList() {
        this.head = null;
    }

    public void appendBack(int newElement) {
        Knoten newKnoten = new Knoten(newElement);
        if (this.head == null) {
            this.head = newKnoten;
            return;
        }
        Knoten temp = this.head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newKnoten;
    }

    public void appendFront(int newElement) {
        Knoten newKnoten = new Knoten(newElement);
        if (this.head == null) {
            this.head = newKnoten;
            newKnoten.next = null;
        }
        else {
            newKnoten.next = this.head;
            this.head = newKnoten;
        }

    }

    public void printLinkedList() {
        if (this.head == null) {
            System.out.println("List is empty");

        } else {
            Knoten temp=this.head;
            while (temp != null) {
                System.out.print(temp.value);
                if(temp.next != null){
                    System.out.print(" -> ");
                }
                temp=temp.next;

            }
        }

    }
    public void deleteBack(){
        if(this.head==null){
            System.out.println("List is empty");
        }
        else if(this.head.next==null){
            this.head=null;
        }
        else{
            Knoten prev=null;
            Knoten current=this.head;
            while(current.next!=null){
                prev=current;
                current=current.next;
            }
            prev.next=null;
        }
    }

    public void deleteFront(){
        if(this.head==null){
            System.out.println("List is empty");
        }
        else{
            this.head=this.head.next;
        }
    }

    public void countKnoten() {
        int count = 0;
        Knoten temp = this.head;
        while(temp!=null){
            temp=temp.next;
            count++;
        }
        System.out.println("\nAnzahl der Knoten: " + count);
    }

    public void deleteList() {
        if(this.head==null) {
            System.out.println("List is already empty");
        }
        else{
            while(this.head!=null){
                deleteFront();
            }
        }
    }
}
