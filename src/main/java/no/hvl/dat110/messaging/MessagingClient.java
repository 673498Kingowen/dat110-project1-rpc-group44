package no.hvl.dat110.messaging;


import java.io.IOException;
import java.net.Socket;


public class MessagingClient {

	private final String server;
	private final int port;

	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public MessageConnection connect() {
		try {
			System.out.println("Attempting to connect to server: " + server + " on port: " + port);
			Socket clientSocket = new Socket(server, port);
			System.out.println(server + " connected on port: " + port);
			return new MessageConnection(clientSocket);
		} catch (IOException ex) {
			System.err.println(" Error connecting to server: " + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

}
