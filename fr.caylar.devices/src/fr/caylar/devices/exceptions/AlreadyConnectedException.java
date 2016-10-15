package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class AlreadyConnectedException extends CaylarException {
	
	private final String reason;

	public AlreadyConnectedException(String reason) {
		this.reason = reason;
	}

	@Override
	public String getMessage() {
		return "MPU is already connected !!! You can't " + reason + ".";
	}
}
