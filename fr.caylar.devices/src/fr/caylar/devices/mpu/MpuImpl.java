package fr.caylar.devices.mpu;

import fr.caylar.devices.DeviceImpl;
import fr.caylar.devices.api.Mpu;
import fr.caylar.devices.exceptions.CaylarException;
import fr.caylar.devices.mpu.commands.ReadStatus;

public class MpuImpl extends DeviceImpl implements Mpu {
	
	/* (non-Javadoc)
	 * @see fr.caylar.mpu.Mpu#readStatus()
	 */
	@Override
	public ReadStatus readStatus() throws CaylarException {
		return execute(new ReadStatus());
	}

}
