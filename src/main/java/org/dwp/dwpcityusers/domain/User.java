package org.dwp.dwpcityusers.domain;

import java.math.BigDecimal;

public class User {

private String id;
private String first_name;
private String last_name;
private String email;
private String ip_address;
private BigDecimal latitude;
private BigDecimal longitude;


public User(String id, String first_name, String last_name, String email, String ip_address, BigDecimal latitude,
		BigDecimal longitude) {
	super();
	this.id = id;
	this.first_name = first_name;
	this.last_name = last_name;
	this.email = email;
	this.ip_address = ip_address;
	this.latitude = latitude;
	this.longitude = longitude;
}


public User() {
	}


public String getId() {
	return id;
}

public String getFirst_name() {
	return first_name;
}

public String getLast_name() {
	return last_name;
}

public String getEmail() {
	return email;
}

public String getIp_address() {
	return ip_address;
}

public BigDecimal getLatitude() {
	return latitude;
}

public BigDecimal getLongitude() {
	return longitude;
}




}
