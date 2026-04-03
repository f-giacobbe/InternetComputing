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
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html");
			out.println(); // riga vuota che separa header e body

			out.println("<html>");
			out.println("<head></head>");
			out.println("<body>");
			out.println("<p><b>Prima pagina HTML</b></p>");
			out.println("</body>");
			out.println("</html>");

			while (true) {
				String line = in.readLine();
				out.println(line);
			} // while
		} catch (Exception e) {
			System.err.println(e);
		}
	} // main
} // class

