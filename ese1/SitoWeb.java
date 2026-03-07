package it.unical.dimes.reti.ese1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SitoWeb {
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(8189);
			System.out.println(s+" in attesa di una connessione.");
			Socket incoming = s.accept();
			System.out.println(incoming);
			BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);
			out.println("<HEAD></HEAD><BODY><P><b>Prima pagina HTML</b></P></BODY>");
			out.println ();
			out.println ();
			while (true) {
				String line = in.readLine();
				out.println(line);
			} // while
			
			//incoming.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	} // main
} // class

