package fr.caylar.devices.mpu.commands;

import fr.caylar.devices.Command;

public class ReadStatus extends Command {
	
	public ReadStatus() {
		super("[01]{12}");
	}

	@Override
	public String getCommand() {
		return "READ_STATUS1";
	}

	public boolean isLock1Normal() {
		return reply.charAt(0) == '1';
	}

	public boolean isLock2Normal() {
		return reply.charAt(1) == '1';
	}

	public boolean isLock3Normal() {
		return reply.charAt(2) == '1';
	}

	public boolean isSecurityNormal() {
		return reply.charAt(3) == '1';
	}

	public boolean isRemoteExternal() {
		return reply.charAt(4) == '1';
	}

	public boolean isCurrentRegulModel() {
		return reply.charAt(5) == '1';
	}

	public boolean isInRegulation() {
		return reply.charAt(6) == '1';
	}

	public boolean isPowerOn() {
		return reply.charAt(7) == '1';
	}

	public boolean isEdgeModeOn() {
		return reply.charAt(8) == '1';
	}

	public boolean isMaintenanceOn() {
		return reply.charAt(9) == '1';
	}

	public boolean isHeaterOn() {
		return reply.charAt(10) == '1';
	}

	public boolean isRaz() {
		return reply.charAt(11) == '1';
	}
}
