package org.dwp.dwpcityusers.service;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dwp.dwpcityusers.domain.User;
import org.dwp.dwpcityusers.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BPDTSUserProxyServiceImpl implements BPDTSUserProxyService {

	Logger logger = LoggerFactory.getLogger(BPDTSUserProxyServiceImpl.class);

	@Value("${bpdtsService.cityUserUrl}")
	private String cityUserServiceUrl;

	@Value("${bpdtsService.userIdServiceUrl}")
	private String userIdServiceUrl;

	@Value("${bpdtsService.usersServiceUrl}")
	private String userServiceUrl;

	private RestTemplate restTemplate;

	public BPDTSUserProxyServiceImpl() {
		restTemplate = new RestTemplate();
	}

	@Override
	public List<User> getUsersByCity(String city) {

		URI cityUserUrl = buildUrlForCityUserService(city);
		ResponseEntity<User[]> response = null;
		try {
			response = restTemplate.exchange(cityUserUrl, HttpMethod.GET, null, User[].class);
		} catch (RestClientException ex) {
			throw new ServiceException("Error Calling City User Service for " + city, "Error in downstream City Service", 500);
		}
		List<User> list = Arrays.asList(response.getBody());

		logger.info("returning result getUsersByCity of size :" + list.size());
		return list;
	}

	@Override
	public List<User> getUsers() {
		ResponseEntity<User[]> response = null;
		try {
			response = restTemplate.exchange(userServiceUrl, HttpMethod.GET, null, User[].class);
		} catch (RestClientException ex) {
			throw new ServiceException("Error Calling get User Service ", "Error in downstream User Service", 500);
		}
		List<User> list = Arrays.asList(response.getBody());
		logger.info("returning result getUsers of size :" + list.size());
		return list;
	}

	private URI buildUrlForCityUserService(String city) {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("cityName", city);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(cityUserServiceUrl);
		return builder.buildAndExpand(urlParams).toUri();
	}

}
