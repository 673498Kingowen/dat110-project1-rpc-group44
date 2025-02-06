package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

import java.io.IOException;

public class SensorStub extends RPCLocalStub {

	public SensorStub(RPCClient rpcclient) {
		super(rpcclient);
	}

	public static int read() {

        byte[] request = RPCUtils.marshallVoid();
        byte[] response = rpcclient.call((byte) Common.READ_RPCID, request);

        int temp = RPCUtils.unmarshallInteger(response);
        return temp;
    }
}
