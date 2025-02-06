package no.hvl.dat110.rpc;

import java.io.IOException;
import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

    private MessagingServer msgserver;
    private MessageConnection connection;

    // hashmap to register RPC methods which are required to extend RPCRemoteImpl
    // the key in the hashmap is the RPC identifier of the method
    private HashMap<Byte,RPCRemoteImpl> services;

    public RPCServer(int port) {

        this.msgserver = new MessagingServer(port);
        this.services = new HashMap<>();

    }

    public void run() throws IOException {

        // the stop RPC method is built into the server
        RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);

        System.out.println("RPC SERVER RUN - Services: " + services.size());

        connection = msgserver.accept();

        System.out.println("RPC SERVER ACCEPTED");

        boolean stop = false;

        while (!stop) {

            Message requestmsg, replymsg;

            requestmsg = connection.receive();

            if (requestmsg == null || requestmsg.getData() == null || requestmsg.getData().length == 0) {
                System.out.println("Received an empty or null message.");
                return;
            }
            byte rpcid = requestmsg.getData()[0];

            byte[] requestData = RPCUtils.decapsulate(requestmsg.getData());

            // Lookup the method to be invoked
            RPCRemoteImpl method = services.get(rpcid);

            byte[] replyData;

            if (method != null) {

                replyData = method.invoke(requestData);
            } else {
                System.out.println("RPC Server: Unknown RPC ID " + rpcid);
                replyData = new byte[0];
            }

            byte[] replyPayload = RPCUtils.encapsulate(rpcid, replyData);
            replymsg = new Message(replyPayload);
            connection.send(replymsg);

            if (rpcid == RPCCommon.RPIDSTOP) {
                stop = true;
            }
        }

    }

    // used by server side method implementations to register themselves in the RPC server
    public void register(byte rpcid, RPCRemoteImpl impl) {
        services.put(rpcid, impl);
    }

    public void stop() {

        if (connection != null) {
            connection.close();
        } else {
            System.out.println("RPCServer.stop - connection was null");
        }

        if (msgserver != null) {
            msgserver.stop();
        } else {
            System.out.println("RPCServer.stop - msgserver was null");
        }

    }
}