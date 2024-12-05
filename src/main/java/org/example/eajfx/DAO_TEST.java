package org.example.eajfx;

public class DAO_TEST {
    public static void main(String[] args) {
        DAO_DB dao = new DAO_DB();
        for (Command c : dao)
        {
            System.out.println(c);
        }
    }
}
