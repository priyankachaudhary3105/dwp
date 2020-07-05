package org.dwp.dwpcityusers.service;

import java.util.List;

import org.dwp.dwpcityusers.domain.User;

/**
 * 
 * UserCityService is used to fetch users located in given range and listed in
 * default location London
 *
 */
public interface UserCityService {

	public List<User> getUsers(String rangeInMiles);

}
