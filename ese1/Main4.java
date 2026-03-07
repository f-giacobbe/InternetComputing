package it.unical.dimes.reti.ese1;

import java.io.IOException;
import java.net.Socket;

public class Main4 {

	public static void main(String[] args) {
		String host = "google.it";
//		String host = "www.dimes.unical.it";
		int port = 80;
		int timeout = 10000; // dieci secondi
		Socket s = SocketOpener.openSocket(host, port, timeout);
		if (s == null)
			System.out.println("The socket could not be opened.");
		else
			System.out.println(s);

	}

}

class SocketOpener extends Thread {
	private String host;
	private int port;
	private Socket socket;

	public static Socket openSocket(String host, int port, int timeout) {
		SocketOpener opener = new SocketOpener(host, port);
		opener.start();
		try {
			opener.join(timeout);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return opener.getSocket();
	}

	public SocketOpener(String host, int port) {
		this.host = host;
		this.port = port;
		socket = null;
	}

	public Socket getSocket() {
		return socket;
	}

	public void run() {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
