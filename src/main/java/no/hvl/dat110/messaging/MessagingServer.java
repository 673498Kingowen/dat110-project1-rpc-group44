package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class MessagingServer {

	private final ServerSocket welcomeSocket;

	public MessagingServer(int port)  {
		try{
			this.welcomeSocket = new ServerSocket(port);
		} catch (IOException ex) {
			System.out.println("Error establishing server: " + ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException("Could not establish server");
		}
	}

	public MessageConnection accept() {
		try {
			System.out.println(" Waiting for client connection on port " + welcomeSocket.getLocalPort() + "...");
			Socket socket = welcomeSocket.accept();
			System.out.println(" Client connected from " + socket.getInetAddress() + ":" + socket.getPort());
			MessageConnection connection = new MessageConnection(socket);
			return connection;
		} catch (IOException ex) {
			System.err.println(" Error accepting client connection: " + ex.getMessage());
			return null;
		}
	}



	public void stop() {
		try {
            welcomeSocket.close();
        } catch (IOException ex) {
			System.out.println("Error stopping server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
