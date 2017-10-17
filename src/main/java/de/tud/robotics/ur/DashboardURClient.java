package de.tud.robotics.ur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.DashboardInterface;

public class DashboardURClient {

	private static final Logger LOG = Logger.getLogger(URClient.class.getSimpleName());
	private static final int DEFAULT_TIMEOUT = 1000;
	private static final String connectionString = "Connected: Universal Robots Dashboard Server";
	
	private String name;
	private String host;
	private static final int port = 29999;
	
	private Map<Class<?>, Object> proxies;
	private URDashboardInvocationHandler invoker;
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private boolean connected;
	
	public DashboardURClient(String host) {
		this.host  = host;
		Validate.notNull(host);
		this.name = host;
		proxies = new HashMap<Class<?>, Object>();
		invoker  = new URDashboardInvocationHandler(this);
	}

	public void setName(String name) {
		Validate.notEmpty(name);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isConnected() {
		return connected;
	}
	public void connect() {
		new URDashBoardReconnectThread().start();
	}

	private void connectIntern() throws IOException {
		LOG.log(Level.INFO, "try connecting to "+name+" Dashboard "+host+":"+port);
		init();
	}
	private void init() throws IOException {
		socket = new Socket(host, port);
		socket.setSoTimeout(DEFAULT_TIMEOUT);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		String connected = read();
		if(connected.equalsIgnoreCase(connectionString)) {
			LOG.log(Level.INFO, "connected to "+name+" Dashboard "+host+":"+port);
		}
		this.connected = true;
	}

	@SuppressWarnings("unchecked")
	public <T  extends DashboardInterface> T getProxy(Class<T> clazz) {
		if (proxies.containsKey(clazz))
			return (T) proxies.get(clazz);
		else {
			T result = (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, invoker);
			proxies.put(clazz, result);
			return result;
		}
	}
	
	public void dispose() {
		try {
			socket.close();
			connected = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public synchronized String call(String msg) throws IOException {
		if (writer == null)
			return null;
		writer.write(msg);
		writer.flush();
		return read();
	}
	
	private String read() {
		try {
			return reader.readLine();
		} catch(Exception e) {
		}
		return null;
	}
	private class URDashBoardReconnectThread extends Thread {

			public void run() {
				while (!isInterrupted()) {
					try {
						connectIntern();
						return;
					} catch (Exception e) {
						System.err.println(this + ": " + e);
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.err.println(this + ": " + e);
					}
				}
			};
	}
}
