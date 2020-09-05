package io.baay.assignment.models;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class Device {
	
	private String id;
	private String brand;
	private String phone;
	private String picture;
	private String sim;
	private String resolution;
	private Release release;
	private Hardware hardware;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getSim() {
		return sim;
	}
	
	public void setSim(String sim) {
		this.sim = sim;
	}
	
	public String getResolution() {
		return resolution;
	}
	
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public Release getRelease() {
		return release;
	}
	
	public void setRelease(Release release) {
		this.release = release;
	}
	
	public Hardware getHardware() {
		return hardware;
	}
	
	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}
	
	@Override
	public String toString() {
		return "Device{" +
			"id='" + id + '\'' +
			", brand='" + brand + '\'' +
			", phone='" + phone + '\'' +
			", picture='" + picture + '\'' +
			", sim='" + sim + '\'' +
			", resolution='" + resolution + '\'' +
			", release=" + release +
			", hardware=" + hardware +
			'}';
	}
}
