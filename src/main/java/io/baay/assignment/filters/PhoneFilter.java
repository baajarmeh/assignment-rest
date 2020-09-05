package io.baay.assignment.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.baay.assignment.models.Device;
import io.baay.assignment.utils.Constants;

@Component
public class PhoneFilter implements Filter {
	
	@Override
	public List<Device> execute(List<Device> deviceList, Map<String, String> queryParams) {
		return deviceList.stream()
			.filter(p -> StringUtils.containsIgnoreCase(p.getPhone(), queryParams.get(Constants.PHONE)))
			.collect(Collectors.toList());
	}
}
