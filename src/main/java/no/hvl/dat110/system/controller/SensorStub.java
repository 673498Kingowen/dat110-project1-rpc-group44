package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

import java.io.IOException;

public class SensorStub extends RPCLocalStub {

	public SensorStub(RPCClient rpcclient) {
		super(rpcclient);
	}

	public static int read() throws IOException {

		// marshall parameter to read call (void parameter)
		byte[] request = RPCUtils.marshallVoid();

		// make remote procedure call for read
		byte[] response = rpcclient.call((byte)Common.READ_RPCID, request);

		// unmarshall the return value from the call (an integer)
		int temp = RPCUtils.unmarshallInteger(response);

		return temp;
	}
}
