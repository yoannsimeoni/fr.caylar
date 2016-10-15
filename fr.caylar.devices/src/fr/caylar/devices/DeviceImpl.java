package fr.caylar.devices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Pattern;

import fr.caylar.devices.api.Device;
import fr.caylar.devices.exceptions.AlreadyConnectedException;
import fr.caylar.devices.exceptions.BadCommandException;
import fr.caylar.devices.exceptions.CantConnectException;
import fr.caylar.devices.exceptions.CantDisconnectException;
import fr.caylar.devices.exceptions.CantReadException;
import fr.caylar.devices.exceptions.CantWriteException;
import fr.caylar.devices.exceptions.InvalidCommandException;
import fr.caylar.devices.exceptions.NotConnectedException;
import fr.caylar.devices.exceptions.UnexpectedReply;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class DeviceImpl implements Device {

	private final Pattern PATTERN = Pattern.compile("[A-Z0-9_]{12}");
	private final ReadOnlyBooleanWrapper isConnected = new ReadOnlyBooleanWrapper(false);
	private final ReadOnlyStringWrapper ipOrHost = new ReadOnlyStringWrapper(null);
	private final ReadOnlyIntegerWrapper port = new ReadOnlyIntegerWrapper(-1);
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;

	private void checkIsConnected(String string) throws AlreadyConnectedException {
		if(isConnected.get()) {
			throw new AlreadyConnectedException("connecting");
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#getIpOrHost()
	 */
	@Override
	public ReadOnlyStringProperty getIpOrHost() {
		return ipOrHost.getReadOnlyProperty();
	}
	/* (non-Javadoc)
	 * @see fr.caylar.Device#setIpOrHost(java.lang.String)
	 */
	@Override
	public void setIpOrHost(String ipOrHost) throws AlreadyConnectedException {
		checkIsConnected("changing ip / host name");
		this.ipOrHost.set(ipOrHost);
		System.out.println("Ip/Host is set to " + ipOrHost);
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#getPort()
	 */
	@Override
	public ReadOnlyIntegerProperty getPort() {
		return port.getReadOnlyProperty();
	}
	/* (non-Javadoc)
	 * @see fr.caylar.Device#setPort(int)
	 */
	@Override
	public void setPort(int port) throws AlreadyConnectedException {
		checkIsConnected("changing port number");
		this.port.set(port);
		System.out.println("Port is set to " + port);
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#isConnected()
	 */
	@Override
	public ReadOnlyBooleanProperty isConnected() {
		return isConnected.getReadOnlyProperty();
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#connect()
	 */
	@Override
	public void connect() throws AlreadyConnectedException, CantConnectException {
		checkIsConnected("connecting");
		try {
			socket = new Socket(ipOrHost.get(), port.get());
			isConnected.set(true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("Connected to " + ipOrHost + " on port " + port);
		} catch (Exception e) {
			throw new CantConnectException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#disconnect()
	 */
	@Override
	public void disconnect() throws CantDisconnectException {
		if(!isConnected.get()) {
			try {
				socket.close();
				isConnected.set(false);
				socket = null;
				reader = null;
				writer = null;
				System.out.println("Disconnect");
			} catch (IOException e) {
				throw new CantDisconnectException(e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#send(java.lang.String)
	 */
	@Override
	public String send(String command) throws NotConnectedException, CantWriteException, InvalidCommandException, CantReadException, BadCommandException {
		if(socket == null) {
			throw new NotConnectedException();
		}
		if(!PATTERN.matcher(command).matches()) {
			throw new InvalidCommandException();
		}
		try {
			writer.write(command);
			writer.flush();
			System.out.println("Command sent : " + command);
		} catch (IOException e) {
			throw new CantWriteException(e);
		}
		try {
			String line = reader.readLine();
			System.out.println("Read : " + line);
			if(line.equals("BAD_COMMAND")) {
				throw new BadCommandException();
			}
			return line;
		} catch (IOException e) {
			throw new CantReadException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.caylar.Device#execute(CommandType)
	 */
	@Override
	public <CommandType extends Command> CommandType execute(CommandType command) throws NotConnectedException, CantWriteException, CantReadException, BadCommandException, UnexpectedReply {
		try {
			String reply = send(command.getCommand());
			command.setReply(reply);
		} catch (InvalidCommandException e) {
			System.err.println("Your command in your code doesn't match the pattern !!!");
			e.printStackTrace();
		}
		return command;
	}
}
