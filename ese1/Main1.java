package it.unical.dimes.reti.ese1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main1 {

	public static void main(String[] args) throws UnknownHostException {
		printLocalAddress();
		System.out.println("--------");
		printRemoteAddress("www.dimes.unical.it");
		
		System.out.println("--------");
		printRemoteAddress("8.8.4.4");

		System.out.println("--------");
		printAllRemoteAddress("dns.google");

	}
	
	static void printLocalAddress () {
	    try {
	      InetAddress myself = InetAddress.getLocalHost ();
	      System.out.println ("My name : " + myself.getHostName ());
	      System.out.println ("My IP : " + myself.getHostAddress ());
	    } catch (UnknownHostException ex) {
	      System.out.println ("Failed to find myself:");
	    }
	}
	static void printRemoteAddress (String name) {
	    try {
	      InetAddress machine = InetAddress.getByName (name);
	      System.out.println ("Host name : " + machine.getHostName ());
	      System.out.println ("Host IP : " + machine.getHostAddress ());
	    } catch (UnknownHostException ex) {
	      System.out.println ("Failed to lookup " + name);
	    }
	}
	static void printAllRemoteAddress (String name) {
		try {
			InetAddress[] machines = InetAddress.getAllByName (name);
			int i=0;
			for (InetAddress machine: machines) {
				System.out.println ("Host name ("+i+"): " + machine.getHostName ());
				System.out.println ("Host IP ("+i+"): " + machine.getHostAddress ());
				i++;
			}
		} catch (UnknownHostException ex) {
			System.out.println ("Failed to lookup " + name);
		}
	}


}
