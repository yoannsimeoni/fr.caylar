package fr.caylar.devices.api;

import fr.caylar.devices.Command;
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
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;

public interface Device {

	ReadOnlyStringProperty getIpOrHost();
	void setIpOrHost(String ipOrHost) throws AlreadyConnectedException;

	ReadOnlyIntegerProperty getPort();
	void setPort(int port) throws AlreadyConnectedException;

	ReadOnlyBooleanProperty isConnected();
	void connect() throws AlreadyConnectedException, CantConnectException;
	void disconnect() throws CantDisconnectException;

	String send(String command) throws NotConnectedException, CantWriteException, InvalidCommandException,
			CantReadException, BadCommandException;

	<CommandType extends Command> CommandType execute(CommandType command)
			throws NotConnectedException, CantWriteException, CantReadException, BadCommandException, UnexpectedReply;

}