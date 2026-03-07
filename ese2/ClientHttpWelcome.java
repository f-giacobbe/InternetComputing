package it.unical.dimes.reti.ese2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHttpWelcome {
    public static void main(String[] args){
        try {
            Socket s = new Socket("localhost", 8188);
            System.out.format("Connessione creata %s.%n", s.toString());
            PrintWriter out = new PrintWriter(
                    s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));

            new MyReader(in).start();

            while(true){
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if(line.equals("EXIT"))
                    break;
                out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }
}
