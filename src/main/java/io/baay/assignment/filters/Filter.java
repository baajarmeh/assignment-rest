package io.baay.assignment.filters;

import io.baay.assignment.models.Device;

import java.util.List;
import java.util.Map;

public interface Filter {
  public List<Device> execute(List<Device> deviceList, Map<String, String> queryParams);
}
