package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class CantReadException extends CaylarException {
	
	public CantReadException(Throwable cause) {
		initCause(cause);
	}

	@Override
	public String getMessage() {
		return "Can't read due to :" + getCause().getMessage();
	}
}
