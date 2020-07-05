package org.dwp.dwpcityusers.service;

import java.util.ArrayList;
import java.util.List;

import org.dwp.dwpcityusers.domain.User;
import org.dwp.dwpcityusers.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserCityServiceImpl implements UserCityService {

	Logger logger = LoggerFactory.getLogger(UserCityServiceImpl.class);

	@Autowired
	private BPDTSUserProxyService userProxyService;

	@Value("${dwpDefaultCity}")
	private String dwpDefaultCity;

	@Value("${dwpDefaultCityLatitude}")
	private double dwpDefaultCityLatitude;

	@Value("${dwpDefaultCityLongitude}")
	private double dwpDefaultCityLongitude;

	@Override
	public List<User> getUsers(String rangeInMiles) {

		int rangeInMileIntVal = 0;
		try {
			rangeInMileIntVal = Integer.parseInt(rangeInMiles);
		} catch (Exception ex) {
			throw new ServiceException("Invalid range", "Try passing int value , example 50", 400);
		}

		List<User> listUserInLondon = userProxyService.getUsersByCity(dwpDefaultCity);

		List<User> listAllUsers = userProxyService.getUsers();

		List<User> userInGivenRange = filterUsersByRange(listAllUsers, rangeInMileIntVal);

		List<User> userListedInLondonPlusInRange = new ArrayList<User>();
		userListedInLondonPlusInRange.addAll(listUserInLondon);
		userListedInLondonPlusInRange.addAll(userInGivenRange);

		logger.info("return resultSize : " + userListedInLondonPlusInRange.size());

		return userListedInLondonPlusInRange;
	}

	private List<User> filterUsersByRange(List<User> listAllUsers, int rangeInMiles) {
		List<User> userInRange = new ArrayList<User>();
		listAllUsers.stream().forEach(user -> {
			double distanceDiff = getDistanceDifference(dwpDefaultCityLatitude, dwpDefaultCityLongitude,
					user.getLatitude().doubleValue(), user.getLongitude().doubleValue());
			if (distanceDiff < rangeInMiles) {
				userInRange.add(user);
			}
		});

		return userInRange;
	}

	private double getDistanceDifference(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			return (dist);
		}
	}

}
