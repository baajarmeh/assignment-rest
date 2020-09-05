package io.baay.assignment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import io.baay.assignment.exceptions.InvalidCriteriaException;
import io.baay.assignment.exceptions.ServiceException;
import io.baay.assignment.filters.FilterExecutor;
import io.baay.assignment.models.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Handset Service")
@RestController
@RequestMapping(value = "/mobile")
public class MobileController {
	
	private FilterExecutor filterExecutor;
	
	public MobileController(FilterExecutor filterExecutor) {
		this.filterExecutor = filterExecutor;
	}
	
	@ApiOperation(
		value =
			"Allows consumers to filter Device Details based on criteria provided as query parameters",
		produces = "application/json",
		httpMethod = "GET")
	@ApiResponses(
		value = {
			@ApiResponse(
				code = 200,
				message = "Successfully retrieved handset details with the filters provided",
				response = Device.class),
			@ApiResponse(code = 400, message = "No records found for specified filters"),
			@ApiResponse(code = 500, message = "Internal Error")
		})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/search")
	public ResponseEntity searchByPrice(@RequestParam Map<String, String> queryParameters)
		throws ServiceException, InvalidCriteriaException {
		
		List<Device> filteredDeviceList = filterExecutor.processRequest(queryParameters);
		ResponseEntity<List<Device>> responseEntity = ResponseEntity
			.status(HttpStatus.OK)
			.body(filteredDeviceList);
		
		return responseEntity;
	}
}
