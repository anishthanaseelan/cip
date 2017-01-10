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
package com.cts.cip.core.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.model.CancelShippingUnitResponse;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.UnloadShippingUnitResponse;
import com.cts.cip.common.response.model.ErrorCode;
import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.common.response.model.ErrorType;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.common.tracker.model.TrackResponseContainer;

/**
 * @author 
 *
 */
public class CIPResourceResponseBuilder {

	Logger logger = LoggerFactory.getLogger(CIPResourceResponseBuilder.class);
	
	static final String TRAN_ID = "transactionId";
	
	/** To build response
	 * @param payloadEntity
	 * @param mediaType
	 * @param transactionId
	 * @return
	 */
	public Response build(Object payloadEntity, MediaType mediaType,String transactionId) {

		return build ( null, payloadEntity , mediaType,transactionId);
	}
	
	/** To build response
	 * @param e
	 * @param payloadEntity
	 * @param mediaType
	 * @param transactionId
	 * @return
	 */
	public Response build(Exception e, Object payloadEntity, MediaType mediaType,String transactionId) {

		CIPResourceResponse  responseEntity = buildPayload(e, payloadEntity);

		return  buildResponse(e, responseEntity, mediaType,transactionId);

	}

	/** To build response
	 * @param e
	 * @param mediaType
	 * @param transctionId
	 * @return
	 */
	public Response build(Exception e, MediaType mediaType,String transctionId) {

			return build ( e, null , mediaType,transctionId);

	}

	
	/** To build Good response
	 * @param entity
	 * @param mediaType
	 * @return
	 */
	public Response buildOk( Object entity , MediaType mediaType) {

		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(entity)
				.type(mediaType).build();

	}
	
	/** To build error response
	 * @param entity
	 * @param mediaType
	 * @return
	 */
	public Response buildServerError(Object entity , MediaType mediaType) {

		return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(entity)
				.type(mediaType).build();

	}

	private Response buildResponse(Exception e , Object responseEntity, MediaType mediaType,String transactionId) {
		
	
		if (e != null && responseEntity != null) {

			if (e.getClass().equals(BusinessException.class) || e.getClass().getSuperclass().equals(BusinessException.class)) {
				return Response.status(javax.ws.rs.core.Response.Status.EXPECTATION_FAILED).entity(responseEntity)
						.type(mediaType).header(TRAN_ID,transactionId).build();
			} else {
				return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
						.type(mediaType).header(TRAN_ID,transactionId).build();
			}

		}  else {
				return Response.status(javax.ws.rs.core.Response.Status.OK).entity(responseEntity)
						.type(mediaType).header(TRAN_ID,transactionId).build();
		}
	}

	/** To Build Payload
	 * @param e
	 * @param payloadEntity
	 * @return
	 */
	public CIPResourceResponse buildPayload(Exception e, Object payloadEntity) {

		CIPResourceResponse responseEntity = new CIPResourceResponse();

		if (payloadEntity != null) {
			if (payloadEntity instanceof CreateShippingUnitResponse) {
				responseEntity.setCreateShippingUnitResponse((CreateShippingUnitResponse)payloadEntity);
			} else if (payloadEntity instanceof CancelShippingUnitResponse) {
				responseEntity.setCancelShippingUnitResponse((CancelShippingUnitResponse)payloadEntity);
			} else if (payloadEntity instanceof LoadShippingUnitResponse) {
				responseEntity.setLoadShippingUnitResponse((LoadShippingUnitResponse)payloadEntity);
			}  else if (payloadEntity instanceof UnloadShippingUnitResponse) {
				responseEntity.setUnloadShippingUnitResponse((UnloadShippingUnitResponse)payloadEntity);
			} else if (payloadEntity instanceof ManifestShippingUnitsResponse) {
				responseEntity.setManifestShippingUnitsResponse((ManifestShippingUnitsResponse)payloadEntity);
			} else if (payloadEntity instanceof ShippingUnit) {
				responseEntity.setShippingUnit((ShippingUnit)payloadEntity);
			} else if (payloadEntity instanceof GetDocumentsResponse) {
				responseEntity.setGetDocumentsResponse((GetDocumentsResponse)payloadEntity);
			} else if (payloadEntity instanceof Load) {
				responseEntity.setLoad((Load)payloadEntity);
			} else if (payloadEntity instanceof TrackResponseContainer) {
				responseEntity.setTrackResponse((TrackResponseContainer)payloadEntity);
			} 
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
