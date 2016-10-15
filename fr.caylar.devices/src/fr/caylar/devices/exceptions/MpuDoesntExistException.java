package fr.caylar.devices.exceptions;

@SuppressWarnings("serial")
public class MpuDoesntExistException extends CaylarException {
	
	private String name;

	public MpuDoesntExistException(String name) {
		this.name = name;
	}

	@Override
	public String getMessage() {
		return "Requesterd MPU : " + name + " doesn't exit !!!";
	}
}
