package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class BadCommandException extends CaylarException {
	
	@Override
	public String getMessage() {
		return "The device replied BAD_COMMAND";
	}
}
