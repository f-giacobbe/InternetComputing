package it.unical.dimes.reti.ese1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main3 {
  public static void main(String[] args) {
    try {
      Socket s = new Socket("google.com",80);
      PrintWriter out = new PrintWriter (s.getOutputStream(),true);      
      BufferedReader in = 
         new BufferedReader (new InputStreamReader(s.getInputStream()));
      out.print ("GET / HTTP/1.1");
      out.println ();
      out.println ();
      boolean more = true;
      while (more) {
         String line = in.readLine();
         if (line == null) more = false;
         else System.out.println(line);
      }
    } catch (IOException e) { System.out.println("Error"+e); }
  }
}

