package org.dwp.dwpcityusers.service;

import java.util.List;

import org.dwp.dwpcityusers.domain.User;

/**
 * 
 * BPDTSUserProxyService is proxy service which uses restTemplate to fetch
 * required details
 * 
 */
public interface BPDTSUserProxyService {

	public List<User> getUsersByCity(String city);

	public List<User> getUsers();

}
