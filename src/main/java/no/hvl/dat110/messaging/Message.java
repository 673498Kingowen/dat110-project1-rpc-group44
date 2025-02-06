package no.hvl.dat110.messaging;


public class Message {

	private final byte[] data;

	public Message(byte[] data) {

		if (data == null || data.length > 127) {
			throw new IllegalArgumentException("Data should be not null, and Data must not exceed 127 bytes");
		}
		this.data = data;
	}

	public byte[] getData() {
		return this.data; 
	}

}
