package io.baay.assignment.config;

import com.testingsyndicate.hc.jmx.HcJmx;
import com.testingsyndicate.hc.jmx.HcJmxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketException;

import javax.management.JMException;

import io.baay.assignment.exceptions.CustomResponseHandler;

/**
 * SpringConfig provides configuration capability to prepare RestTemplate and other objects to in
 * interact with handset services
 */
@Configuration
public class SpringConfig {
	private static final Logger LOGGER = LogManager.getLogger(SpringConfig.class);
	
	private int connectionTimeout;
	private int connectionRequestTimeout;
	private int socketTimeout;
	private int maxConnections;
	private int maxConnectionsPerRoute;
	private int maxRetries;
	private int retryInterval;
	
	public SpringConfig(
		@Value("${http.connection.timeout:7000}") int connectionTimeout,
		@Value("${http.connection.request.timeout:7000}") int connectionRequestTimeout,
		@Value("${http.socket.timeout:10000}") int socketTimeout,
		@Value("${http.max.connections:200}") int maxConnections,
		@Value("${http.max.connections.per.route:50}") int maxConnectionsPerRoute,
		@Value("${request.max.retries:3}") int maxRetries,
		@Value("${request.retry.interval:300}") int retryInterval) {
		this.connectionTimeout = connectionTimeout;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout = socketTimeout;
		this.maxConnections = maxConnections;
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
		this.maxRetries = maxRetries;
		this.retryInterval = retryInterval;
	}
	
	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom()
			.setConnectionRequestTimeout(connectionRequestTimeout)
			.setConnectTimeout(connectionTimeout)
			.setSocketTimeout(socketTimeout)
			.build();
	}
	
	/**
	 * Create a pool and limits the maximum of http connections that can be reuse used in rest call
	 * which will help in reducing new connections.
	 *
	 * @return PoolingHttpClientConnectionManager
	 * @throws JMException
	 */
	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() throws JMException {
		LOGGER.info(
			"Inside PoolingHttpClientConnectionManager Max Conn count: "
				+ maxConnections
				+ " - maxConnectionsPerRoute: "
				+ maxConnectionsPerRoute);
		PoolingHttpClientConnectionManager poolingConnectionManager =
			new PoolingHttpClientConnectionManager();
		poolingConnectionManager.setMaxTotal(maxConnections);
		poolingConnectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
		return poolingConnectionManager;
	}
	
	/**
	 * Configures httpclient to retry in case of dependent services unavailability. This takes out
	 * extra configuration to implement during the actual invocation of request using rest template.
	 *
	 * @param requestConfig
	 * @param poolingHttpClientConnectionManager
	 * @return CloseableHttpClient
	 */
	@Bean
	@DependsOn({ "requestConfig", "poolingConnectionManager" })
	public CloseableHttpClient httpClient(
		RequestConfig requestConfig,
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
		CloseableHttpClient client =
			HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingHttpClientConnectionManager)
				.setRetryHandler(
					new HttpRequestRetryHandler() {
						@Override
						public boolean retryRequest(
							IOException exception, int executionCount, HttpContext context) {
							return executionCount <= maxRetries && exception instanceof SocketException;
						}
					})
				.setServiceUnavailableRetryStrategy(
					new ServiceUnavailableRetryStrategy() {
						@Override
						public boolean retryRequest(
							HttpResponse response, int executionCount, HttpContext context) {
							int responseCode = response.getStatusLine().getStatusCode();
							LOGGER.info(
								"Execution count: "
									+ executionCount
									+ " - Response Status Code: "
									+ responseCode);
							return executionCount <= maxRetries && responseCode >= 500;
						}
						
						@Override
						public long getRetryInterval() {
							return retryInterval;
						}
					})
				.build();
		
		try {
			HcJmx.getInstance().register(client);
		} catch ( HcJmxException ex ) {
			LOGGER.warn("Unable to monitor HttpClient, continuing...", ex);
		}
		return client;
	}
	
	@Bean
	@DependsOn({ "httpClient" })
	public ClientHttpRequestFactory requestFactory(CloseableHttpClient httpClient) {
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	
	@Bean
	@DependsOn("requestFactory")
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.setErrorHandler(new CustomResponseHandler());
		return restTemplate;
	}
}
