package org.dwp.dwpcityusers.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.dwp.dwpcityusers.domain.User;
import org.dwp.dwpcityusers.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BPDTSUserProxyServiceTest {

	@Autowired
	BPDTSUserProxyService serviceUnderTest;

	@Mock
	RestTemplate restTemplate;

	@Test
	public void testGetUsersByCity_Success() {
		ReflectionTestUtils.setField(serviceUnderTest, "restTemplate", restTemplate);

		User[] userArray = new User[2];
		userArray[0] = new User("1", "Test", "test", "test", "test", null, null);
		userArray[1] = new User("2", "Test2", "test2", "test2", "test2", null, null);

		ResponseEntity<User[]> responseEntity = new ResponseEntity<User[]>(userArray, HttpStatus.OK);

		Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.eq(User[].class)))
				.thenReturn(responseEntity);

		List<User> result = serviceUnderTest.getUsersByCity("London");

		assertEquals(result.size(), 2);

	}

	@Test
	public void testGetUsersByCity_FailureWhenRestException() {
		ReflectionTestUtils.setField(serviceUnderTest, "restTemplate", restTemplate);

		Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.eq(User[].class)))
				.thenThrow(RestClientResponseException.class);

		try {
			serviceUnderTest.getUsersByCity("London");
		} catch (ServiceException ex) {
			assertEquals(ex.getHttpStatus(), 500);
		}

	}

	@Test
	public void testGetUsers_Success() {
		User[] userArray = new User[2];
		userArray[0] = new User("1", "Test", "test", "test", "test", null, null);
		userArray[1] = new User("2", "Test2", "test2", "test2", "test2", null, null);

		ResponseEntity<User[]> responseEntity = new ResponseEntity<User[]>(userArray, HttpStatus.OK);

		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.eq(User[].class)))
				.thenReturn(responseEntity);

		List<User> result = serviceUnderTest.getUsers();

		assertTrue(result.size() > 0);

	}

	@Test
	public void testGetUsers_FailureWhenRestException() {
		ReflectionTestUtils.setField(serviceUnderTest, "restTemplate", restTemplate);

		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.eq(User[].class)))
				.thenThrow(RestClientResponseException.class);

		try {
			serviceUnderTest.getUsers();
		} catch (ServiceException ex) {
			assertEquals(ex.getHttpStatus(), 500);
		}

	}

}
