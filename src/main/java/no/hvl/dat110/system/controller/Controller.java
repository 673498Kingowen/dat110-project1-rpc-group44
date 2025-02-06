package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

import java.io.IOException;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) throws IOException {
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);

		displayclient.connect();
		sensorclient.connect();

		DisplayStub display = new DisplayStub(displayclient);
		SensorStub sensor = new SensorStub(sensorclient);


		for (int i = 0; i < N; i++) {
			int temp = sensor.read();
			display.write(String.valueOf(temp));
		}
		// TODO - START
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);
		// create local display and sensor stub objects
		// connect to sensor and display RPC servers - using the RPCClients
		// read value from sensor using RPC and write to display using RPC

		// TODO - END
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
