package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class NotConnectedException extends CaylarException {

	@Override
	public String getMessage() {
		return "MPU is NOT connected !!!";
	}
}
