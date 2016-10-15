package fr.caylar.devices.api;

import fr.caylar.devices.exceptions.CaylarException;
import fr.caylar.devices.mpu.commands.ReadStatus;

public interface Mpu extends Device {

	ReadStatus readStatus() throws CaylarException;

}