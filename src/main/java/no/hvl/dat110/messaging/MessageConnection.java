package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public MessageConnection(Socket socket) {
		if (socket == null || socket.isClosed()) {
			throw new IllegalArgumentException(" Invalid socket: Connection is null or closed.");
		}
		try {
			this.socket = socket;
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("✅ Connection established with " + socket.getInetAddress() + ":" + socket.getPort());
		} catch (IOException ex) {
			System.out.println(" Error establishing connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}


	public void send(Message message) {
		try {
			byte[] data = MessageUtils.encapsulate(message);
			System.out.println("📤 Sending message: " + Arrays.toString(data) + " to " + socket.getInetAddress() + ":" + socket.getPort());
			outStream.write(data);
			outStream.flush();
		} catch (IOException ex) {
			System.out.println("❌ Error sending message: " + ex.getMessage());
			ex.printStackTrace();
		}
	}


	public Message receive() {
		try {
			byte[] data = new byte[MessageUtils.SEGMENTSIZE];
			inStream.readFully(data);
			Message message = MessageUtils.decapsulate(data);
			System.out.println(" Received message: " + Arrays.toString(message.getData()));
			return message;
		} catch (IOException ex) {
			System.out.println("❌ Error receiving message: " + ex.getMessage());
			ex.printStackTrace();
			return null; // Return null if receiving fails
		}
	}



	public void close() {
		try {
			outStream.close();
			inStream.close();
			socket.close();
		} catch (IOException ex) {
			System.out.println("Error closing connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}