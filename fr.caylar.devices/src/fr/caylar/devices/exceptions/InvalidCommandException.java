package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class InvalidCommandException extends CaylarException {
	
	private static final String MESSAGE = String.format("Invalid command syntax. It should respect the following syntax:%n"
			+ "uppercase only,%n"
			+ "12 chars,%n"
			+ "no end of line.");

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
