package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

import java.io.IOException;

public class TestBooleanBooleanStub extends RPCLocalStub {
	
	public TestBooleanBooleanStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public boolean m(boolean b) throws IOException {
		
		byte[] request = RPCUtils.marshallBoolean(b);
		
		byte[] reply = rpcclient.call((byte)4,request);
		
		boolean bres = RPCUtils.unmarshallBoolean(reply);
		
		return bres;
	}
	
}
