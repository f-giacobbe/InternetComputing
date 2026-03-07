package it.unical.dimes.reti.ese2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedEchoServer {
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(8189);

			while (true) {
				System.out.format("Il %s è in attesa di una connessione.%n", s.toString());
				Socket incoming = s.accept();
				ThreadedHandler t = new ThreadedHandler(incoming);
				t.start();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	} // main
} // class

class ThreadedHandler extends Thread {
	private Socket incoming;

	public ThreadedHandler(Socket s) {
		this.incoming = s;
	}

	public void run() {
		try {
			System.out.format("\t\tNuova connessione %s creata.%n", incoming.toString());
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
			System.out.format("Connessione %s terminata.%n", incoming.toString());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
