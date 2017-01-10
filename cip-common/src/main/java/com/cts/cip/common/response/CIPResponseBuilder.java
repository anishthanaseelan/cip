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
package com.cts.cip.common.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ErrorCode;
import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.common.response.model.ErrorType;
import com.cts.cip.common.response.model.ResponseStatus;

public class CIPResponseBuilder<T> {

	Logger logger = LoggerFactory.getLogger(CIPResponseBuilder.class);
	
	public Response build(T payloadEntity, MediaType mediaType) {

		return build ( null, payloadEntity , mediaType);
	}
	
	public Response build(Exception e, T payloadEntity, MediaType mediaType) {


		CIPResponse<T>  responseEntity = buildPayload(e, payloadEntity);

		return  buildResponse(e, responseEntity, mediaType);

	}

	public Response build(Exception e, MediaType mediaType) {

			return build ( e, null , mediaType);

	}

	
	public Response buildOk( Object entity , MediaType mediaType) {

		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(entity)
				.type(mediaType).build();

	}
	
	public Response buildServerError(Object entity , MediaType mediaType) {

		return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(entity)
				.type(mediaType).build();

	}

	private Response buildResponse(Exception e , CIPResponse<T> responseEntity, MediaType mediaType) {
		
	
		if (e != null && responseEntity != null) {

			if (e.getClass().equals(BusinessException.class) || e.getClass().getSuperclass().equals(BusinessException.class)) {
				return Response.status(javax.ws.rs.core.Response.Status.EXPECTATION_FAILED).entity(responseEntity)
						.type(mediaType).build();
			} else {
				return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
						.type(mediaType).build();
			}

		}  else {
				return Response.status(javax.ws.rs.core.Response.Status.OK).entity(responseEntity)
						.type(mediaType).build();

		}
	}

	public CIPResponse<T> buildPayload(Exception e, T payloadEntity) {

		CIPResponse<T> responseEntity = new CIPResponse<>();

		if (payloadEntity != null) {
			responseEntity.setEntity(payloadEntity);
		}
		
		if (e != null) {
			responseEntity.setStatus(ResponseStatus.FAILURE);
		} else {
			responseEntity.setStatus(ResponseStatus.SUCCESS);
			return responseEntity;
		}

		if (e.getClass().equals(BusinessException.class) || e.getClass().getSuperclass().equals(BusinessException.class)) {

			responseEntity.setErrorType(ErrorType.BUSINESS_ERROR);
			responseEntity.setErrorCode(ErrorCode.VALIDATION_EXCEPTION);
			ErrorDetail error = new ErrorDetail();
			error.setErrorMessage(e.getMessage());
			if ( ((BusinessException) e).getErrorList() != null && !((BusinessException) e).getErrorList().isEmpty() ){
				responseEntity.getErrorList().addAll(((BusinessException) e).getErrorList());
			} else {
				responseEntity.getErrorList().add(error);
			}
			
		} else {
			responseEntity.setErrorType(ErrorType.SYSTEM_ERROR);
			ErrorDetail error = new ErrorDetail();
			error.setErrorMessage(e.getMessage());
			responseEntity.getErrorList().add(error);
		}
		return responseEntity;

	}

}
