package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

import java.io.IOException;

public class TestIntIntStub extends RPCLocalStub {
	
	public TestIntIntStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public int m(int x) throws IOException {
				
		byte[] request = RPCUtils.marshallInteger(x);
		
		byte[] reply = rpcclient.call((byte)3,request);
		
		int xres = RPCUtils.unmarshallInteger(reply);
		
		return xres;
	}
}
