package it.unical.dimes.reti.ese1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(8189);
			System.out.println(s+" in attesa di una connessione.");
			Socket incoming = s.accept();
			System.out.println(incoming);
			BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);
			out.println("Hello! Enter \"BYE\" to exit.");
			boolean done = false;
			while (!done) {
				String line = in.readLine();
				if (line == null)
					done = true;
				else {
					out.println("Echo: " + line.toUpperCase());
					if (line.trim().toUpperCase().equals("BYE"))
						done = true;
				}
			} // while
			incoming.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	} // main
} // class

