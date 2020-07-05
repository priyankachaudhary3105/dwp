package org.dwp.dwpcityusers;

import java.util.List;

import org.dwp.dwpcityusers.domain.User;
import org.dwp.dwpcityusers.service.UserCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Controller Class with GET endpoint to get the List of users located in
 * default location given in application.yml file with in default range of 50
 * miles if rangeInMiles not mentioned in query param
 * 
 */
@RestController
public class CityController {

	Logger logger = LoggerFactory.getLogger(CityController.class);

	public final static String DEFAULT_RANGE_IN_MILES = "50";

	@Autowired
	UserCityService userCityService;

	@GetMapping(path = "/London/users")
	@ResponseStatus(code = HttpStatus.OK)
	public List<User> getCityUsers(@RequestParam(required = false) String rangeInMiles) {
		logger.info("Getting request for range : " + rangeInMiles);
		rangeInMiles = StringUtils.isEmpty(rangeInMiles) ? DEFAULT_RANGE_IN_MILES : rangeInMiles;
		return userCityService.getUsers(rangeInMiles);

	}

}
