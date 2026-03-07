package it.unical.dimes.reti.ese2;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class SendObject {
  public static void main (String args[]) {
    try {
      ServerSocket server = new ServerSocket (3575);
      Socket client = server.accept();
      System.out.println(client);
      ObjectOutputStream output =
        new ObjectOutputStream (client.getOutputStream ());
      output.writeObject("<Welcome>");
      Studente studente =
        new Studente (14520,"Leonardo","da Vinci","Ingegneria Informatica");
      output.writeObject(studente);
      output.writeObject("<Goodbye>");
      System.out.println("Oggetto inviato.");
      client.close();
      server.close();
    } catch (Exception e) { System.err.println (e); }
  } }
