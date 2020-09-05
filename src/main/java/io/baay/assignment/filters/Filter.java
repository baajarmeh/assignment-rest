package io.baay.assignment.filters;

import java.util.List;
import java.util.Map;

import io.baay.assignment.models.Device;

public interface Filter {
	
	public List<Device> execute(List<Device> deviceList, Map<String, String> queryParams);
}
