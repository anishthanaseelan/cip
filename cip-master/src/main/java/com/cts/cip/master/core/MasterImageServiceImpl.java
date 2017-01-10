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
package com.cts.cip.master.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.constants.CIPMasterConstants;
import com.cts.cip.master.exception.ImageUploadException;
import com.cts.cip.master.exception.ImageValidationException;
import com.cts.cip.master.exception.ResourceNotAvailableException;
import com.cts.cip.master.model.ImageDetailsRequest;
import com.cts.cip.master.model.ImageDetailsResponse;
import com.cts.cip.master.repository.dao.CarrierRepository;
import com.cts.cip.master.repository.entities.Carrier;

/**
 * @author 417765
 *
 */

public class MasterImageServiceImpl implements MasterImageService{
	
	Logger logger = LoggerFactory.getLogger(MasterImageServiceImpl.class);
	
	 
	CarrierRepository carrierRepository;

	
	String pathToSaveImage;
	

	String imageServerUrl;

	/**
	 * 
	 */
	public MasterImageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.master.core.ImageService#uploadImage(com.cts.cip.master.model.ImageDetailsRequest)
	 */
	@Override
	public ImageDetailsResponse uploadImage(ImageDetailsRequest imageDetailRequest, 
			String resourceName, Integer resourceId)  throws ImageValidationException, 
				ResourceNotAvailableException, ImageUploadException {
		
		logger.debug("Started processing uploadImage()::"+imageDetailRequest);
		ImageDetailsResponse imageDetailsRes = new ImageDetailsResponse();
		
		validateRequest(imageDetailRequest, resourceName, resourceId);
		if (resourceName.equalsIgnoreCase(CIPMasterConstants.CARRIER)) 
		{
			//CarrierDetail carrierDetail = masterService.findCarrierById(resourceId);
			Carrier carrier = carrierRepository.findById(resourceId);
			if(carrier == null || !carrier.isActive()) {
				throw new ResourceNotAvailableException("The given parent resource does not exist.");
			} 
			String fileName = imageDetailRequest.getFileName();
			
			String [] fileTokens = fileName.split("\\.");
			if (fileTokens == null || fileTokens.length<2) {
				throw new ImageUploadException(resourceName+"- Image File Type is Empty.");
			} 
				
						
			String imageFilePathToSave = pathToSaveImage + fileName;
			logger.debug("imageFilePathToSave value -->" + imageFilePathToSave);
			try {
				//Decode the Base64Content to Image and save to File system.
				//String formmatedImageStr = imageDetailRequest.getBase64Content().replace("/","");	
				logger.debug("ImageFileContent: "+imageDetailRequest.getBase64Content());
				byte[] decodeByteArray = java.util.Base64.getDecoder().decode(imageDetailRequest.getBase64Content());
			    FileOutputStream fos = new FileOutputStream(new File(imageFilePathToSave)); 
			    fos.write(decodeByteArray); 
			    fos.close();
				logger.debug("Image File Save process completed");
			} catch (IOException ioe){
				ioe.printStackTrace();
				throw new ImageUploadException("Error in writing  the image file content.");
				
			}
			//Update the file path into the database.
			carrier.setIconUrl(fileName);
			
			//StatusResponse statusReponse = masterService.updateCarrier(carrierDetail);
			carrierRepository.update(carrier);
			StatusResponse statusResponse = new StatusResponse();
			statusResponse.setCode("1");
			statusResponse.setCodeDescription("SUCCESS");
			statusResponse.setDescription("Successfully Uploaded the "+ fileName + " for " + carrier.getName());
			imageDetailsRes.setStatusResponse(statusResponse);
			logger.debug("Image File name saved in the datastore");
		} 
		logger.debug("Completed processing uploadImage.");
		return imageDetailsRes;
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.master.core.ImageService#validateRequest(com.cts.cip.master.model.ImageDetailsRequest)
	 */
	public ImageDetailsRequest validateRequest(ImageDetailsRequest imageDetailsRequest, 
			String resourceName, Integer resourceId) throws ImageValidationException 
	{
		
		String  fileName = imageDetailsRequest.getFileName();
		if (fileName == null || fileName.isEmpty()) {
			throw new ImageValidationException("File Name is Empty.");
		} 
		String  imageContent = imageDetailsRequest.getBase64Content();
		if (imageContent == null) {
			throw new ImageValidationException("Image Content is Empty.");
		}
		if (resourceName == null) {
			throw new ImageValidationException("Parent Resource Name is Empty.");
		}
		if (resourceId == null) {
			throw new ImageValidationException("Parent ResourceId is Empty.");
		}
		
		return imageDetailsRequest;
	}

	public CarrierRepository getCarrierRepository() {
		return carrierRepository;
	}

	public void setCarrierRepository(CarrierRepository carrierRepository) {
		this.carrierRepository = carrierRepository;
	}

	public String getPathToSaveImage() {
		return pathToSaveImage;
	}

	public void setPathToSaveImage(String pathToSaveImage) {
		this.pathToSaveImage = pathToSaveImage;
	}

	public String getImageServerUrl() {
		return imageServerUrl;
	}

	public void setImageServerUrl(String imageServerUrl) {
		this.imageServerUrl = imageServerUrl;
	}
	
	
}
