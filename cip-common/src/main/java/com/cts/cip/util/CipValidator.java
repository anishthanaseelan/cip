/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/
package com.cts.cip.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ErrorCode;
import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.common.response.model.ResponseStatus;

/**
 * @author 
 *
 */
public class CipValidator {
	
	private static Validator validator;	
	
	static Logger logger = LoggerFactory.getLogger(CommonUtility.class);
	
	
	
	/** To validate request
	 * @param request
	 * @return
	 */
	public static <T> CIPResponse<String> validateRequest(T request) {
		CIPResponse<String> cipResponse = null;		
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
			Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);
			logger.debug("Constraints Count ::"+ constraintViolations.size());			
			cipResponse = populateResponse(constraintViolations);
		} catch (Exception e) {
			if(cipResponse==null) {
				cipResponse =  new CIPResponse<>();				
			}
			cipResponse.setStatus(ResponseStatus.FAILURE);
			cipResponse.setErrorDesc("Error in applying validation constraints." + e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return cipResponse;
	}
	
	
	/** To populate response
	 * @param constraintViolations
	 * @return
	 */
	private static <T> CIPResponse<String> populateResponse (Set<ConstraintViolation<T>> constraintViolations) {
		CIPResponse<String> cipResponse = new CIPResponse<>();
		cipResponse.setStatus(ResponseStatus.SUCCESS);
		StringBuilder detailedError =  new StringBuilder();
		if(!constraintViolations.isEmpty()) {
			cipResponse.setStatus(ResponseStatus.FAILURE);
			
			List<ErrorDetail> errorDetailList = new ArrayList<ErrorDetail>();
			int index=0;
			for(ConstraintViolation<T> constraintViolation :  constraintViolations) {
				index=index+1;
				ErrorDetail fieldStatus =  new ErrorDetail();
				Object value = constraintViolation.getInvalidValue();
				if(value instanceof String) {
					fieldStatus.setFieldValue((String) constraintViolation.getInvalidValue());
				} else if (value instanceof Float) {
					fieldStatus.setFieldValue(String.valueOf((float)constraintViolation.getInvalidValue()));
				}
				
				fieldStatus.setErrorMessage(constraintViolation.getMessage());
				fieldStatus.setFieldName(constraintViolation.getPropertyPath().toString());
				errorDetailList.add(fieldStatus);
				detailedError.append(index);
				detailedError.append("-" + constraintViolation.getMessage() + "\n");				
			}
			cipResponse.setErrorList(errorDetailList);
			cipResponse.setErrorCode(ErrorCode.INVALID_REQUEST);
		}
		return cipResponse;
	}
	
	

}
