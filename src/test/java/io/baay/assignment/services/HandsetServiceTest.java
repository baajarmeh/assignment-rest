package io.baay.assignment.services;

import io.baay.assignment.exceptions.ServiceException;
import io.baay.assignment.models.Device;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class HandsetServiceTest {

  @Mock private RestTemplate mockRestTemplate;

  @Mock private ResponseEntity<String> mockResponse;

  private HandsetService handsetService;

  @Mock private ResourceAccessException mockResourceAccessException;

  @Mock private RestClientException mockRestClientException;

  @Mock private Exception mockException;

  @Rule public ExpectedException expectedException = ExpectedException.none();
  private String responseBody =
      "[\n"
          + "  {\n"
          + "    \"id\": \"25846\",\n"
          + "    \"brand\": \"Apple\",\n"
          + "    \"phone\": \"Apple iPad Pro 12.9 (2018)\",\n"
          + "    \"picture\": \"https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg\",\n"
          + "    \"sim\": \"Nano-SIM eSIM\",\n"
          + "    \"resolution\": \"2048 x 2732 pixels\",\n"
          + "    \"release\": {\n"
          + "      \"announceDate\": \"2018 October\",\n"
          + "      \"priceEur\": \"1100\"\n"
          + "    },\n"
          + "    \"hardware\": {\n"
          + "      \"audioJack\": \"No\",\n"
          + "      \"gps\": \"Yes with A-GPS\",\n"
          + "      \"battery\": \"Li-Po 9720 mAh battery (36.71 Wh)\"\n"
          + "    }\n"
          + "  }]";

  @Before
  public void setUp() {
    handsetService = new HandsetService(mockRestTemplate, "https://abc/handsets/list");
  }

  @Test
  public void testGetHandsetRecords() throws ServiceException {
    Mockito.when(mockRestTemplate.getForEntity("https://abc/handsets/list", String.class))
        .thenReturn(mockResponse);
    Mockito.when(mockResponse.getBody()).thenReturn(responseBody);
    List<Device> handsetList = handsetService.getHandsetRecords();
    Assert.assertNotNull(handsetList);
    Assert.assertEquals(1, handsetList.size());
  }

  @Test
  public void testGetHandsetRecordsResourceException() throws ServiceException {
    Mockito.when(mockRestTemplate.getForEntity("https://abc/handsets/list", String.class))
        .thenThrow(mockResourceAccessException);
    Mockito.when(mockResourceAccessException.getMessage()).thenReturn("I/O error on request");
    expectedException.expect(ServiceException.class);
    expectedException.expectMessage("I/O error on request");
    handsetService.getHandsetRecords();
  }

  @Test
  public void testGetHandsetRecordsRestClientException() throws ServiceException {
    Mockito.when(mockRestTemplate.getForEntity("https://abc/handsets/list", String.class))
        .thenThrow(mockRestClientException);
    Mockito.when(mockRestClientException.getMessage())
        .thenReturn("RestClient Exception occurred while sending request");
    expectedException.expect(ServiceException.class);
    expectedException.expectMessage("RestClient Exception occurred while sending request");
    handsetService.getHandsetRecords();
  }

  @Test
  public void testGetHandsetRecordsException() throws ServiceException {
    Mockito.when(mockRestTemplate.getForEntity("https://abc/handsets/list", String.class))
        .thenReturn(null);
    expectedException.expect(ServiceException.class);
    handsetService.getHandsetRecords();
  }
}
