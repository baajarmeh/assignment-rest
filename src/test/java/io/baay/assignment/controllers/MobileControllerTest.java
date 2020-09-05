package io.baay.assignment.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

import io.baay.assignment.exceptions.InvalidCriteriaException;
import io.baay.assignment.exceptions.ServiceException;
import io.baay.assignment.filters.FilterExecutor;
import io.baay.assignment.models.Device;
import io.baay.assignment.models.Hardware;
import io.baay.assignment.models.Release;

@RunWith(MockitoJUnitRunner.class)
public class MobileControllerTest {
	
	@Mock
	private FilterExecutor mockFilterExecutor;
	private MobileController mobileCtrl;
	
	@Before
	public void setUp() {
		mobileCtrl = new MobileController(mockFilterExecutor);
	}
	
	@Test
	public void testSearchByPriceValid() throws ServiceException, InvalidCriteriaException {
		HashMap<String, String> queryParameters = new HashMap<>();
		queryParameters.put("announceDate", "1999");
		queryParameters.put("resolution", "chars");
		
		Mockito.when(mockFilterExecutor.processRequest(Mockito.anyMap())).thenReturn(filteredHandsetList());
		
		ResponseEntity responseEntity = mobileCtrl.searchByPrice(queryParameters);
		Assert.assertNotNull("Response from SearchEndpoint is Null", responseEntity);
		Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		
		ArrayList<Device> responseList = ( ArrayList<Device> ) responseEntity.getBody();
		Assert.assertEquals(1, responseList.size());
	}
	
	private ArrayList<Device> filteredHandsetList() {
		Device handset = new Device();
		handset.setId("4525");
		handset.setBrand("Lenovo");
		handset.setPhone("Lenovo Amaze");
		handset.setPicture("https://cdn2.gsmarena.com/vv/bigpic/lenovo-amaze.jpg");
		handset.setSim("Nano-SIM eSIM");
		handset.setResolution("2048 x 2732 chars");
		Hardware hardware2 = new Hardware();
		hardware2.setAudioJack("No");
		hardware2.setBattery("Li-po battery");
		hardware2.setGps("Yes");
		Release release2 = new Release();
		release2.setPriceEur(350);
		release2.setAnnounceDate("1999");
		handset.setRelease(release2);
		handset.setHardware(hardware2);
		
		ArrayList<Device> handsetList = new ArrayList<>();
		handsetList.add(handset);
		return handsetList;
	}
}
