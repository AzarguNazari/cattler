package com.example.cattler;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.Duration;

@SpringBootApplication
public class Cattler {

	public static void main(String[] args) {

		DockerClientConfig standard = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

		DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
				.dockerHost(standard.getDockerHost())
				.sslConfig(standard.getSSLConfig())
				.maxConnections(100)
				.connectionTimeout(Duration.ofSeconds(30))
				.responseTimeout(Duration.ofSeconds(45))
				.build();

		DockerHttpClient.Request request = DockerHttpClient.Request.builder()
				.method(DockerHttpClient.Request.Method.GET)
				.path("/images/json")
				.build();


		try (DockerHttpClient.Response response = httpClient.execute(request)) {
			System.out.println(response.getStatusCode());
			System.out.println(IOUtils.toString(response.getBody()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		SpringApplication.run(Cattler.class, args);
	}

}
