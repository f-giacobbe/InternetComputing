package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO.Es2;

import org.springframework.util.SerializationUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sensore extends Thread {
    private String idSensore;
    private final Random random = new Random();

    Sensore(String id) {
        this.idSensore = id;
    }


    public String getIdSensore() {
        return idSensore;
    }

    @Override
    public void run() {
        try (DatagramSocket ds = new DatagramSocket()) {
            while (true) {
                Misura nuova = new Misura(idSensore, random.nextDouble(), Calendar.getInstance());
                byte[] buf = SerializationUtils.serialize(nuova);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(Server.IP_SERVER), Server.PORTA_UDP);
                ds.send(packet);

                TimeUnit.MINUTES.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Sensore("1").start();
        new Sensore("2").start();
        new Sensore("3").start();
    }
}
