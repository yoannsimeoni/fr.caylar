package fr.caylar.devices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import fr.caylar.devices.api.Mpu;
import fr.caylar.devices.exceptions.AlreadyConnectedException;
import fr.caylar.devices.exceptions.CaylarException;
import fr.caylar.devices.exceptions.MpuDoesntExistException;
import fr.caylar.devices.mpu.MpuImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Factory {
	
	private static final String PROP_FILE_NAME = "caylar.config.properties";
	private static final Properties PROPS = new Properties();
	private static final ObservableMap<String, MpuImpl> mpus = FXCollections.observableMap(new HashMap<String, MpuImpl>());
	
	static { new Factory(); }
	private Factory() {
		// load default properties from jar
		try {
			PROPS.load(getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME));
		} catch (IOException e) {
			System.err.println("Unexpected IOException when reading default factory configuration: ");
			e.printStackTrace();
		}
		
		// load user properties from PROP_FILE_NAME
		try {
			File userConfigFile = new File(PROP_FILE_NAME);
			if(userConfigFile.exists()) {
				PROPS.load(new FileInputStream(userConfigFile));
			}
		} catch (IOException e) {
			System.err.println("Error when reading factory configuration: ");
			e.printStackTrace();
		}
		
		// initial load of MPUs
		try {
			resolvePropertied();
		} catch (AlreadyConnectedException e1) {
			// impossible: at startup, no MPU has been connected
			e1.printStackTrace();
		}
	}
	private void resolvePropertied() throws AlreadyConnectedException {
		for (Entry<Object, Object> entry : PROPS.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			String[] splitted = key.split("\\.");
			if(splitted.length> 0) {
				if(splitted[0].equals("mpu")) {
					if(splitted.length == 3) {
						MpuImpl mpu = mpus.get(splitted[1]);
						if(mpu == null) {
							mpu = new MpuImpl();
							mpus.put(splitted[1], mpu);
						}
						if(splitted[2].equals("ipOrHost")) {
							mpu.setIpOrHost(value);
						} else if(splitted[2].equals("port")) {
							mpu.setPort(Integer.parseInt(value));
						}
					} else {
						System.out.println("Invalid config file : mpu section should be a length 3");
					}
				}
				
			}
		}
		PROPS.elements();
	}
	
	public static MpuImpl getMpu(String name) throws CaylarException {
		MpuImpl mpu = mpus.get(name);
		if(mpu == null) {
			throw new MpuDoesntExistException(name);
		}
		return mpu;
	}
	
	public static ObservableMap<String, MpuImpl> getMpus() {
		return mpus;
	}
	
	public static Mpu createMpu() {
		return new MpuImpl();
	}

}
