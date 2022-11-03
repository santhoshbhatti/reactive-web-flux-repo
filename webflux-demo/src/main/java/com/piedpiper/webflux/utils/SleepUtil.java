package com.piedpiper.webflux.utils;

public class SleepUtil {
	
	public static void sleepSeconds(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
