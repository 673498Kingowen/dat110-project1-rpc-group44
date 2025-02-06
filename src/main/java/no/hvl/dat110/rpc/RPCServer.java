package no.hvl.dat110.rpc;

import java.util.HashMap;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

    private final MessagingServer msgserver;
    private MessageConnection connection;

    private final HashMap<Byte, RPCRemoteImpl> services;

    public RPCServer(int port) {
        this.msgserver = new MessagingServer(port);
        this.services = new HashMap<>();
    }

    public void run() {
        RPCRemoteImpl rpcStopImpl = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
        register(RPCCommon.RPIDSTOP, rpcStopImpl);

        System.out.println("RPC SERVER RUN - Services: " + services.size());

        connection = msgserver.accept();
        System.out.println("RPC SERVER ACCEPTED");

        boolean stop = false;
        while (!stop) {
            Message requestmsg = connection.receive();

            if (requestmsg == null || requestmsg.getData() == null || requestmsg.getData().length == 0) {
                System.out.println("Received an empty or null message. Skipping to next request.");
                continue;
            }

            byte rpcid = requestmsg.getData()[0];
            byte[] requestData = RPCUtils.decapsulate(requestmsg.getData());

            RPCRemoteImpl method = services.get(rpcid);
            byte[] replyData;

            if (method != null) {
                replyData = method.invoke(requestData);
            } else {
                System.out.println("RPC Server: Unknown RPC ID " + rpcid);
                replyData = new byte[0];
            }

            byte[] replyPayload = RPCUtils.encapsulate(rpcid, replyData);
            Message replymsg = new Message(replyPayload);
            connection.send(replymsg);

            if (rpcid == RPCCommon.RPIDSTOP) {
                stop = true;
                System.out.println("Stopping the RPC server.");
            }
        }

        System.out.println("RPC SERVER STOPPED");
    }

    public void register(byte rpcid, RPCRemoteImpl impl) {
        services.put(rpcid, impl);
    }

    public void stop() {
        if (connection != null) {
            connection.close();
        } else {
            System.out.println("RPCServer.stop - connection was null");
        }

        msgserver.stop();
    }
}
