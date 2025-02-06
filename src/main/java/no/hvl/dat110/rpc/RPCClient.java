package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.*;
import no.hvl.dat110.system.controller.Common;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;

	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server, port);
	}

	public void connect() {
        msgclient = new MessagingClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
        connection = msgclient.connect();
    }

	public void disconnect() {
        connection.close();
    }

	public byte[] call(byte rpcid, byte[] param) {
        connection.send(new Message(RPCUtils.encapsulate(rpcid, param)));
        Message reply = connection.receive();
        return RPCUtils.decapsulate(reply.getData());
    }
}