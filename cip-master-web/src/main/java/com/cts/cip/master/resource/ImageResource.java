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
/**
 * 
 */
package com.cts.cip.master.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.core.MasterImageService;
import com.cts.cip.master.exception.ImageUploadException;
import com.cts.cip.master.exception.ImageValidationException;
import com.cts.cip.master.exception.ResourceNotAvailableException;
import com.cts.cip.master.model.ImageDetailsRequest;
import com.cts.cip.master.model.ImageDetailsResponse;

import io.swagger.annotations.Api;

/**
 * @author 417765
 *
 */
@Path("/image")
@Api(value = "/image", description = "Image service")
public class ImageResource {
	
	@Autowired 
	MasterImageService imageService;
	
	Logger logger = LoggerFactory.getLogger(ImageResource.class);
	
	@GET
	@Path("/test")
	public String test() {
		String message = "You have reached the node resource end point and the resource is available";
		return message;
	}

	@PUT
	@Path("/{parentResourceName}/{parentResourceId}")
	@Consumes({ "application/json","application/xml" })
	@Produces({ "application/json","application/xml" })	
	@Transactional
	public Response imageUpload(@Context HttpHeaders headers, ImageDetailsRequest imageDetailsRequest,
			 @PathParam("parentResourceName") String resourceName,  
			 @PathParam("parentResourceId") Integer resourceId) {
		
		logger.info("Started uploading the image..");
		Response response = null;
		ImageDetailsResponse imageDetailRes = new ImageDetailsResponse();
		MediaType mediaType = headers.getMediaType();
		try {
			if (imageDetailsRequest == null)
				throw new ImageValidationException("Image Request Details are Empty.");
			imageDetailRes = imageService.uploadImage(imageDetailsRequest, resourceName, resourceId);
			response = Response.status(200).entity(imageDetailRes).build(); 
			
		} catch (ImageValidationException ive) {
			imageDetailRes = buildResponse(ive.getMessage(), imageDetailsRequest.getFileName());
			response = Response.status(Status.BAD_REQUEST).entity(imageDetailRes).type(mediaType).build();
		} catch (ImageUploadException iue) {
			imageDetailRes = buildResponse(iue.getMessage(), imageDetailsRequest.getFileName());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(imageDetailRes).type(mediaType).build();
		} catch (ResourceNotAvailableException rnae) {
			imageDetailRes = buildResponse(rnae.getMessage(), imageDetailsRequest.getFileName());
			response = Response.status(Status.NOT_FOUND).entity(imageDetailRes).type(mediaType).build();
		}
		return response;
	}
	
	private ImageDetailsResponse buildResponse (String errorMsg, String fileName) {
		StatusResponse statusResponse = new StatusResponse();
		statusResponse.setCode("0");
		statusResponse.setCodeDescription("FAILURE");
		statusResponse.setDetail(errorMsg);
		statusResponse.setDescription("Failed in Uploading  the "+ fileName);
		ImageDetailsResponse imageDetailsRes = new ImageDetailsResponse();
		imageDetailsRes.setStatusResponse(statusResponse);
		return imageDetailsRes;
	}

}
