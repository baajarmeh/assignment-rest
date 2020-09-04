package io.baay.assignment.filters;

import io.baay.assignment.models.Device;
import io.baay.assignment.utils.Constants;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BrandFilter implements Filter {

  @Override
  public List<Device> execute(List<Device> deviceList, Map<String, String> queryParams) {
    return deviceList.stream()
        .filter(p -> StringUtils.containsIgnoreCase(p.getBrand(), queryParams.get(Constants.BRAND)))
        .collect(Collectors.toList());
  }
}
