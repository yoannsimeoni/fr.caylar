package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class CantConnectException extends CaylarException {
	
	public CantConnectException(Throwable cause) {
		initCause(cause);
	}

	@Override
	public String getMessage() {
		return "Can't connect due to :" + getCause().getMessage();
	}
}
