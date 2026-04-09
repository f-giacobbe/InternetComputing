package it.unical.dimes.reti.ese2.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Nodo {
    static final int PORT = 2222;
    BufferedReader sc;

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

    class Chat extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private boolean terminata = false;

        Chat(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("Connessione stabilita con " + socket.toString());

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                Thread ricezione = new Ricezione();
                Thread invio = new Invio();

                ricezione.start();
                invio.start();
                ricezione.join();
                invio.join();

                System.out.printf("Connessione con %s terminata", socket);
                in.close();
                out.close();
                socket.close();
                sc.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        class Ricezione extends Thread {
            @Override
            public void run() {
                try {
                    while (!terminata) {
                        String line = in.readLine();

                        if (line == null || line.equals("BYE")) {
                            terminata = true;
                        }

                        System.out.println(line);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        class Invio extends Thread {
            @Override
            public void run() {
                try {
                    sc = new BufferedReader(new InputStreamReader(System.in));

                    while (!terminata) {
                        if (sc.ready()) {
                            String line = sc.readLine();

                            out.println(line);

                            if (line.equals("BYE")) {
                                terminata = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public static void main(String[] args) {
        Nodo francesco = new Nodo();
    }
}