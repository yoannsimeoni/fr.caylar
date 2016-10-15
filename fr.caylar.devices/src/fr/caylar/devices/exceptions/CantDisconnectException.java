package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class CantDisconnectException extends CaylarException {
	
	public CantDisconnectException(Throwable cause) {
		initCause(cause);
	}

	@Override
	public String getMessage() {
		return "Can't disconnect due to :" + getCause().getMessage();
	}
}
