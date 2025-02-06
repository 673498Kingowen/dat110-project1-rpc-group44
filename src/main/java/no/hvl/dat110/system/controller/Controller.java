package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

import java.io.IOException;

public class Controller  {

	public static void main(String[] args) {
		RPCClient displayclient = null, sensorclient = null;

			System.out.println("Controller starting ...");

			// Create RPC clients for the system
			displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
			sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

			try {
				displayclient.connect();
			} catch (Exception e) {
				System.out.println("Error connecting to display client: " + e.getMessage());
			}

			try {
				sensorclient.connect();
			} catch (Exception e) {
				System.out.println("Error connecting to sensor client: " + e.getMessage());
			}

			displayclient.connect();
			sensorclient.connect();

			DisplayStub display = new DisplayStub(displayclient);
			SensorStub sensor = new SensorStub(sensorclient);

			int n = 5;
			for (int i = 0; i < n; i++) {
				System.out.println("Reading from sensor ...");
				int temp = sensor.read();
				System.out.println("Temperature read: " + temp);
				display.write(String.valueOf(temp));
			}

			RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
			RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

			stopdisplay.stop();
			stopsensor.stop();

			displayclient.disconnect();
			sensorclient.disconnect();

			System.out.println("Controller stopping ...");
	}
}