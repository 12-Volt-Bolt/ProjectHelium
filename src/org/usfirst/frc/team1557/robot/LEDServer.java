package org.usfirst.frc.team1557.robot;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This server has a start up time of ~2000 milliseconds. Any attempt to send
 * data in that time will be lost. Also, if data is sent too quickly it will be
 * lost. (This shouldn't be a problem on the roboRIO.)
 * 
 * 
 * To send data to the RaspberryPi, simple call {@link #sendData(String)} method
 * 
 * @author Levi
 *
 */
class LEDServer {
	/**
	 * The server socket
	 */
	ServerSocket _serverSocket;
	/**
	 * The socket used for the client.
	 */
	Socket _connection;
	/**
	 * Stream used to send data
	 */
	PrintWriter _output;
	private int PORT;
	/**
	 * Create a seperate thread to run all socket communications.
	 *
	 */
	private Thread serverThread = new Thread(() -> {
		while (true) {
			if (connect()) {
				tick();
			}
		}
	});

	public void start() {
		serverThread.start();
	}

	private boolean connect() {
		try {
			_serverSocket = new ServerSocket(this.PORT);
			System.out.println("Connecting...");
			_connection = this._serverSocket.accept();
			System.out.println("Success!");
			_output = new PrintWriter(_connection.getOutputStream());
			_output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void tick() {
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getData() == null || getData().length() <= 0) {

			} else {
				_output.print(getData());
				_output.flush();
				sendData("");
			}
			if (_output.checkError()) {
				try {
					_output.close();
					_connection.close();
					_serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			}

		}
	}

	void init(int PORT) {
		serverThread.start();
		this.PORT = PORT;
	}

	Object lock = new Object();
	/**
	 * This data should only ever be read or written to using the dedicated
	 * methods
	 */
	private String _dataToSend = "Successful Connection!";

	/**
	 * The <b>only</b> safe way to send to data the RaspberryPi. Simply supply a
	 * string which is the data to send
	 * 
	 * @param s
	 *            The data to supply
	 */
	void sendData(String s) {
		// Convert String into char array
		synchronized (lock) {
			_dataToSend = s;
		}
	}

	/**
	 * The <b>only</b> safe way to request the data that is going to be sent to
	 * the RaspberryPi.
	 * 
	 * @return
	 */
	private String getData() {
		synchronized (lock) {
			return _dataToSend;
		}
	}
}