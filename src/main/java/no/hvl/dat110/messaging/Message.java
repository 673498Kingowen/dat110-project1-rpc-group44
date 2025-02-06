package no.hvl.dat110.messaging;

import no.hvl.dat110.TODO;

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
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
