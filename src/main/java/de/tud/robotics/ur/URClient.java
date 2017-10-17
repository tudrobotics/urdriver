package de.tud.robotics.ur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.tud.robotics.ur.api.URScriptInterface;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.RobotPackageData;


public abstract class URClient {

	protected static final Logger LOG = Logger.getLogger(URClient.class.getSimpleName());
	
	protected static final int DEFAULT_TIMEOUT = 1000;
			
	private URClientThread thread;
	private URScriptExtendedInvocationHandler invoker;
	
	private String name;
	private String host;
	private int port;

	private Socket socket;
	private InputStream in;
	private BufferedWriter writer;

	private List<URClientListener> listeners;
	
	private boolean connected;
	
	private Map<Class<?>, Object> proxies;
	private final Map<RobotPackageType, Integer> updateFequences;
	
	public URClient(String host, int port) {		
		this.host = host;
		this.port = port;
		this.name = host;
		Validate.notEmpty(host);
		Validate.isTrue( port > 0);
		listeners = new LinkedList<>();
		proxies  = new HashMap<Class<?>, Object>();
		updateFequences  = new HashMap<RobotPackageType, Integer>();
		invoker = new URScriptExtendedInvocationHandler(this);
	}

	public void setName(String name) {
		Validate.notNull(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void addListener(URClientListener l) {
		listeners.add(l);
	}
	public void removeListener(URClientListener l) {
		listeners.remove(l);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends URScriptInterface> T getProxy(Class<T> clazz) {
		if (proxies.containsKey(clazz)) {
			return (T) proxies.get(clazz);
		}
		else {
			T result = (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, invoker);
			proxies.put(clazz, result);
			return result;
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	public void connect() throws UnknownHostException, IOException {
		LOG.log(Level.INFO, "connecting to " + host + ":" +  port);
		socket = new Socket(host, port);
		socket.setTcpNoDelay(true);
		socket.setSoTimeout(DEFAULT_TIMEOUT);
		in = socket.getInputStream();
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		thread = new URClientThread(name);
		thread.start();
		connected = true;
	}
	
	public void dispose() {
		thread.disposing = true;
		if (thread != null)
			thread.interrupt();
	}
	/**
	 * send a message to the server
	 * 
	 * @param msg
	 *            the message to be written
	 */

	public void write(String msg) throws IOException {
		if (writer == null)
			return;
		writer.write(msg);
		writer.flush();
	}

	/**
	 * 
	 * @param packageType
	 *            type of the package to update
	 * @param updatesPerSecond
	 *            how many times the package should be updated in a second 
	 *            0 = automatic update disabled 
	 *            125 max update fequence by robot
	 */
	public void setUpdateFrequence(RobotPackageType packageType, int updatesPerSecond) {
		if (updatesPerSecond <= 0) {
			updateFequences.remove(packageType);
			return;
		}
		if (updatesPerSecond > 125)
			updatesPerSecond = 125;
		updateFequences.put(packageType, updatesPerSecond);
	}
	/**
	 * is a robot information older than the specified update frequence
	 * @param type
	 * @param data
	 * @param currentTime
	 * @return
	 */
	protected boolean isOutdated(RobotPackageType type, RobotPackageData data, long currentTime) {
		return updateFequences.get(type) != null
				&& currentTime - data.getLastUpdated() > 1000 / updateFequences.get(type);
	}
	private class URClientThread extends Thread {

		private boolean disposing = false;

		public URClientThread(String name) {
			super(name + "-"+URClient.this.getClass().getSimpleName()+"Thread");
		}

		@Override
		public void run() {
			try {
				while (!isInterrupted()) {
					parse(in);
				}
			} catch (SocketException e) {
				LOG.log(Level.WARNING, getName() + ": closed");
			} catch (IOException e) {
				LOG.log(Level.WARNING, "exception while retrieving data from server", e);
			} catch (ParsingException e) {
				LOG.log(Level.WARNING, "parsing error", e);
			} finally {
				disconnect();
				if (!disposing) {
					try {
						connect();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				LOG.log(Level.INFO, "disposing " + this.getName());
			}
		}

		/**
		 * disposes this client
		 */
		protected void disconnect() {
			try {
				socket.close();
				connected = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}

	protected abstract void parse(InputStream in) throws IOException, ParsingException;
	
	protected void notifyListeners(RobotPackageData data) {
		listeners.forEach( l -> l.notify(data));
	}
}
