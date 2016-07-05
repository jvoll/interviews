// N number of people stand in a circle. The people are numbered in order from 1 to N. Starting from 1, we remove every other person
// from the circle until there is only one person remaining. Write a function which takes in the number N and outputs the number of
// the last person remaining.
//
// For example:
// f(3) outputs 3
// f(4) outputs 1
// f(5) outputs 3

public class Person {
    
    int index;
    Person nextPerson;
    
    public Person(int index, Person nextPerson) {
        
        this.index = index;
        this.nextPerson = nextPerson;
    }
    
    public static int getLastPerson(Person startPerson) {
        
        Person cur = startPerson;
        while (cur.index != cur.nextPerson.index) {
            cur.nextPerson = cur.nextPerson.nextPerson;
            cur = cur.nextPerson;
        }
        
        return cur.index;
    }

    public static void f(int n) {
        Person firstPerson = new Person(1, null);
        Person lastPerson = firstPerson;
        for (int i = 2; i < n + 1; i++) {
            Person cur = new Person(i, null);
            lastPerson.nextPerson = cur;
            lastPerson = cur;
        }
        lastPerson.nextPerson = firstPerson;
        System.out.println("f(" + n + ") = " + getLastPerson(firstPerson));
    }
    
    public static void main(String[] args) {
       f(3); 
       f(4);
       f(5);
    }
}
