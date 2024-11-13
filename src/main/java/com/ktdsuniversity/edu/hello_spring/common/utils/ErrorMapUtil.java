package com.ktdsuniversity.edu.hello_spring.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

public final class ErrorMapUtil {

	// 동시성 문제 발생 가능성 매우 높음
	// Validation 체크가 동시에 이루어질 경우 데이터가 꼬일 수 있음
	// private static Map<String, Object> errorMap;
	
	public static Map<String, Object> getErrorMap(BindingResult bindingResult) {
		
		Map<String, Object> errorMap = new HashMap<>();
		
		bindingResult.getFieldErrors()
					 .forEach(error -> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			
			if(!errorMap.containsKey(fieldName)) {
				errorMap.put(fieldName, new ArrayList<>());
			}
							
			List<String> errorList = (List<String>) errorMap.get(fieldName);
			errorList.add(errorMessage);
		});
		
		return errorMap;
	}
	
//	public static Map<String, Object> getErrorMap() {
//		return errorMap;
//	}
}
