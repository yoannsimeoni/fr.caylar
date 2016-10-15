package fr.caylar.devices.mpu.commands;

import fr.caylar.devices.Command;

public class SetPowerOn extends Command {

	protected SetPowerOn() {
		super("POWER_IS_ON");
	}

	@Override
	public String getCommand() {
		return "SET_POWER_ON";
	}
}
