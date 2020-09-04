package io.baay.assignment.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.baay.assignment.exceptions.ServiceException;
import io.baay.assignment.models.Device;

import java.lang.reflect.Type;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * HandsetService helps in calling Mobile handset services to pull the list of handset details.
 */
@Service
public class HandsetService {
  private static final Logger LOGGER = LogManager.getLogger(HandsetService.class);

  private RestTemplate restTemplate;
  private Type handsetList;
  private Gson gson;
  private String handsetServiceURL;

  public HandsetService(
      RestTemplate restTemplate, @Value("${handsetService}") String handsetServiceURL) {
    gson = new Gson();
    handsetList = new TypeToken<List<Device>>() {}.getType();
    this.restTemplate = restTemplate;
    this.handsetServiceURL = handsetServiceURL;
  }

  public List<Device> getHandsetRecords() throws ServiceException {
    List<Device> deviceResponseList;

    try {
      ResponseEntity<String> response = restTemplate.getForEntity(handsetServiceURL, String.class);
      deviceResponseList = gson.fromJson(response.getBody(), handsetList);
      LOGGER.info("Retrieved response from HandsetService");
    } catch (ResourceAccessException exception) {
      LOGGER.error("I/O error on request", exception);
      throw new ServiceException(exception.getMessage());
    } catch (RestClientException exception) {
      LOGGER.error("RestClient Exception occurred while sending request", exception);
      throw new ServiceException(exception.getMessage());
    } catch (Exception exception) {
      LOGGER.error("Exception occurred while sending request", exception);
      throw new ServiceException(exception.getMessage());
    }

    return deviceResponseList;
  }
}
