package it.unical.dimes.reti.ese2.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Nodo {
    static final int PORT = 2222;

    Nodo() {
        new Ascolto().start();
    }

    class Ascolto extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket s = new ServerSocket(PORT);

                while (true) {
                    Socket incoming = s.accept();
                    new Chat(incoming).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void connessione(String ip) {
        try {
            Socket s = new Socket(InetAddress.getByName(ip), PORT);
            new Chat(s).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class Ricezione extends Thread {
        private BufferedReader in;
        private Socket socket;
        private PrintWriter out;

        Ricezione(BufferedReader in, Socket socket, PrintWriter out) {
            this.in = in;
            this.socket = socket;
            this.out = out;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String line = in.readLine();

                    if (line == null || line.equals("BYE")) {
                        break;
                    }

                    System.out.println(line);
                }
                in.close();
                out.close();
                System.out.printf("Connessione con %s terminata", socket);
                socket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Invio extends Thread {
        private PrintWriter out;
        private static Scanner sc = new Scanner(System.in);


        Invio(PrintWriter out) {
            this.out = out;
        }

        @Override
        public void run() {
            try {
                String line;

                while (true) {
                    synchronized (sc) {
                        line = sc.nextLine();
                    }

                    out.println(line);

                    if (line.equals("BYE")) {
                        break;
                    }
                }

                out.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Chat extends Thread {
        private Socket socket;

        Chat(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("Connessione stabilita con " + socket.toString());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                new Ricezione(in, socket, out).start();
                new Invio(out).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        Nodo francesco = new Nodo();
    }
}