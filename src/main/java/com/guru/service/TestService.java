package com.guru.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.guru.pojo.EmployeeName;

@Service
public class TestService {
	
	public static Map<Integer,EmployeeName> db = new HashMap<>();
	
	public String saveEmployee(EmployeeName emp){
		System.out.println("saveEmployee");
		if(emp == null) {
			return "Empname is null";	
		}
		if(db.containsValue(emp)) {
			return "Empname is already exist";
		}
		db.put(db.size()+1, emp);
		return db.get(emp)+"";
	}
}
