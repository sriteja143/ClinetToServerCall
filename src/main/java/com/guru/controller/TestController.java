package com.guru.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.guru.pojo.EmployeeName;
import com.guru.service.TestService;

@RestController
public class TestController {
	
	@Autowired TestService testService;

	@PostMapping("/save")
	public String test(@RequestBody EmployeeName emp) {
		return testService.saveEmployee(emp);
	}
	
	@GetMapping("/resttemp/{name}")
	public String test(@PathVariable String name) {
		RestTemplate template = new RestTemplate();
		EmployeeName emp = new EmployeeName();
		emp.setFirstName(name);
		String lastname = "lastname";
		emp.setLastName(lastname);
		System.out.println(emp);
		System.out.println("name  : "+name + "lastname : "+lastname );
		final String uri = "http://localhost:9898/save";
		String resp = template.postForObject(uri,emp,String.class);;
		System.out.println("resp   :" + resp);
		return resp;
	}
	
	@GetMapping("/okhttp/{name}")
	public String testokhttp(@PathVariable String name) throws IOException {
		EmployeeName emp = new EmployeeName();
		
		emp.setFirstName(name);
		String lastname = "lastname";
		emp.setLastName(lastname);
		System.out.println(emp);
		System.out.println("name  : "+name + "lastname : "+lastname );
		final String uri = "http://localhost:9898/save";
		
		Gson gson = new Gson();
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		System.out.println("sendMessageios....notification body +"+ gson.toJson(emp));
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(gson.toJson(emp).getBytes());

		// Read GCM response.
		InputStream inputStream = conn.getInputStream();
		String resp = IOUtils.toString(inputStream);
		 
		return resp;
	}
	
	@GetMapping("/")
	public Map<Integer, EmployeeName> test() {
		return testService.db;
	}

	
}
