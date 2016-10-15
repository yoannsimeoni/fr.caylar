package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class CantWriteException extends CaylarException {
	
	public CantWriteException(Throwable cause) {
		initCause(cause);
	}

	@Override
	public String getMessage() {
		return "Can't write due to :" + getCause().getMessage();
	}
}
