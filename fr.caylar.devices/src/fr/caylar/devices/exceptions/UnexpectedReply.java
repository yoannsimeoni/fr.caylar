package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class UnexpectedReply extends CaylarException {
	
	private String result;
	private String pattern;

	public UnexpectedReply(String result, String pattern) {
		this.result = result;
		this.pattern = pattern;
	}

	@Override
	public String getMessage() {
		return String.format("The device replied %s but it doesn't match the expected pattern %s", result, pattern);
	}
}
