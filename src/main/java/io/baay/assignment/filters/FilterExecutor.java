package io.baay.assignment.filters;

import io.baay.assignment.exceptions.InvalidCriteriaException;
import io.baay.assignment.exceptions.ServiceException;
import io.baay.assignment.models.Device;
import io.baay.assignment.services.HandsetService;
import io.baay.assignment.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * FilterExecutor class controls the execution of different filters based on provided input
 * criteria. A single class where core logic of which filter to execute is implemented.
 */
@Component
public class FilterExecutor {
  private static final Logger LOGGER = LogManager.getLogger(FilterExecutor.class);

  private HandsetService handsetService;
  private AnnounceDateFilter announceDateFilter;
  private BrandFilter brandFilter;
  private PhoneFilter phoneFilter;
  private PriceFilter priceFilter;
  private SimFilter simFilter;

  public FilterExecutor(
      HandsetService handsetService,
      AnnounceDateFilter announceDateFilter,
      BrandFilter brandFilter,
      PhoneFilter phoneFilter,
      PriceFilter priceFilter,
      SimFilter simFilter) {
    this.handsetService = handsetService;
    this.announceDateFilter = announceDateFilter;
    this.brandFilter = brandFilter;
    this.phoneFilter = phoneFilter;
    this.priceFilter = priceFilter;
    this.simFilter = simFilter;
  }

  public List<Device> processRequest(Map<String, String> queryParams)
      throws InvalidCriteriaException, ServiceException {

    List<Filter> filterFilterList = new ArrayList<>();
    queryParams.keySet().stream()
        .forEach(
            s -> {
              if (Constants.ANNOUNCE_DATE.equalsIgnoreCase(s)) {
                filterFilterList.add(announceDateFilter);
              }
              if (Constants.BRAND.equalsIgnoreCase(s)) {
                filterFilterList.add(brandFilter);
              }
              if (Constants.PHONE.equalsIgnoreCase(s)) {
                filterFilterList.add(phoneFilter);
              }
              if (Constants.PRICE_EUR.equalsIgnoreCase(s) || Constants.PRICE.equalsIgnoreCase(s)) {
                filterFilterList.add(priceFilter);
              }
              if (Constants.SIM.equalsIgnoreCase(s)) {
                filterFilterList.add(simFilter);
              }
            });

    if (filterFilterList.isEmpty()) {
      throw new InvalidCriteriaException("No valid criteria provided to filter records");
    } else {
      LOGGER.info("Executing search with " + filterFilterList.size() + " filters");
      return execute(filterFilterList, queryParams);
    }
  }

  private List<Device> execute(List<Filter> filterList, Map<String, String> queryParams)
      throws ServiceException {
    List<Device> deviceResponseList;

    try {
      deviceResponseList = handsetService.getHandsetRecords();
      for (Filter filter : filterList) {
        deviceResponseList = filter.execute(deviceResponseList, queryParams);
      }

      LOGGER.info("Found " + deviceResponseList.size() + " records matching to provided filters.");
    } catch (Exception ex) {
      LOGGER.error("Filter execution failed while processing request", ex);
      throw new ServiceException(ex.getMessage());
    }

    return deviceResponseList;
  }
}
