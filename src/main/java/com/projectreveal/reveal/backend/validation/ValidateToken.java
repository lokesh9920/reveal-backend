package com.projectreveal.reveal.backend.validation;

import java.net.URI;
import java.net.URISyntaxException;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletRequest;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.projectreveal.reveal.backend.exceptionhandler.ValidationException;
import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.User;

@Service
public class ValidateToken {
	
	@Value("${auth.service.address}")
	private String authServiceAddress;

	public boolean verifyToken(ServletRequest request, String jwtToken) {
		
		String userName = "";
		URI uri = null;
		try {
			uri = new URI(authServiceAddress + "/tokens/verify");
			System.out.println("The uri is : " + uri);
		} catch (URISyntaxException e) {
			System.out.println("Failed to form URI");
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("auth-token", jwtToken);
		httpHeaders.add("X-idms-Auth","A-b6O5nb549M9kSm0z9YSHB8ufTB36jLuTeFOCSZVQU");
		
		RequestEntity<Object> requestEntity = new RequestEntity<Object>(httpHeaders,HttpMethod.GET, uri);

		try{
			
			/**
			 * 
			 * Below lines are to skip certificate verfication for consuming https url
			 * 
			 */
			
			TrustStrategy acceptingTrustStrategy = (X509Certificates,s)->true;
		    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

			
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(csf)
					.build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
		
			// till here
			
			
			
			ResponseEntity<User> responseEntity = new RestTemplate(requestFactory)
					.exchange(requestEntity, User.class);
		
		
			if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
					return true;	//indicates successful validation
			}
			else
				throw new ValidationException("Failed to validate Token");
		}catch (Exception e) {
			throw new ValidationException("Failed to validate Token");
		}
				
	}
}
