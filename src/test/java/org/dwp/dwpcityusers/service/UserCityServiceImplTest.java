package org.dwp.dwpcityusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dwp.dwpcityusers.domain.User;
import org.dwp.dwpcityusers.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCityServiceImplTest {

	@Autowired
	UserCityService serviceUnderTest;

	@Mock
	private BPDTSUserProxyService userProxyService;

	@Test
	public void testGetUsers_WhenUserServiceHaveUserInRange() {

		ReflectionTestUtils.setField(serviceUnderTest, "userProxyService", userProxyService);

		List<User> listCityListedUser = new ArrayList<>();
		listCityListedUser.add(new User("1", "test", "test", "test", "test", null, null));
		listCityListedUser.add(new User("2", "test", "test", "test", "test", null, null));

		List<User> listUsers = new ArrayList<>();
		listUsers.add(
				new User("3", "test", "test", "test", "test", new BigDecimal(51.5489435), new BigDecimal(0.3860497)));
		listUsers.add(
				new User("4", "test", "test", "test", "test", new BigDecimal(51.6710832), new BigDecimal(0.8078532)));
		listUsers.add(
				new User("5", "test", "test", "test", "test", new BigDecimal(30.015509), new BigDecimal(73.2192788)));

		Mockito.when(userProxyService.getUsersByCity(Mockito.anyString())).thenReturn(listCityListedUser);
		Mockito.when(userProxyService.getUsers()).thenReturn(listUsers);

		List<User> result = serviceUnderTest.getUsers("50");

		assertEquals(result.size(), 4);

	}

	@Test
	public void testGetUsers_WhenInValidRange() {
		try {

			serviceUnderTest.getUsers("abc");
		} catch (ServiceException ex) {
			assertEquals(ex.getHttpStatus(), 400);
			Mockito.verify(userProxyService, times(0)).getUsersByCity(Mockito.anyString());
			Mockito.verify(userProxyService, times(0)).getUsers();
		}

	}

}
