package io.baay.assignment.models;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class Hardware {
	
	private String audioJack;
	private String gps;
	private String battery;
	
	public String getAudioJack() {
		return audioJack;
	}
	
	public void setAudioJack(String audioJack) {
		this.audioJack = audioJack;
	}
	
	public String getGps() {
		return gps;
	}
	
	public void setGps(String gps) {
		this.gps = gps;
	}
	
	public String getBattery() {
		return battery;
	}
	
	public void setBattery(String battery) {
		this.battery = battery;
	}
	
	@Override
	public String toString() {
		return "Hardware{" +
			"audioJack='" + audioJack + '\'' +
			", gps='" + gps + '\'' +
			", battery='" + battery + '\'' +
			'}';
	}
}
