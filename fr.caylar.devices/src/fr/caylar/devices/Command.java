package fr.caylar.devices;

import java.util.regex.Pattern;

import fr.caylar.devices.exceptions.UnexpectedReply;

public abstract class Command {
	
	private final Pattern pattern;
	protected String reply;
	
	protected Command(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	protected void setReply(String reply) throws UnexpectedReply {
		this.reply = reply;
		if(!pattern.matcher(reply).matches()) {
			throw new UnexpectedReply(reply, pattern.toString());
		}
	}

	//package visibility only
	public abstract String getCommand();
	
	public String getResult() {
		return reply;
	}
}
