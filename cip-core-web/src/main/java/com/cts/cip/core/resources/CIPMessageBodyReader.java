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

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.CreateShippingUnitRequest;

/**
 * @author 
 *
 */
public class CIPMessageBodyReader implements MessageBodyReader<CreateShippingUnitRequest> {
	
	Logger logger = LoggerFactory.getLogger(CIPMessageBodyReader.class);
 
@Override
public boolean isReadable(Class<?> type, Type genericType,
    Annotation[] annotations, MediaType mediaType) {
    return type == CreateShippingUnitRequest.class;
}
 
@Override
public CreateShippingUnitRequest readFrom(Class<CreateShippingUnitRequest> type,
    Type genericType,
    Annotation[] annotations, MediaType mediaType,
    MultivaluedMap<String, String> httpHeaders,
    InputStream entityStream)
        throws IOException {
 
    try {
    	JAXBContext jaxbContext = JAXBContext.newInstance(CreateShippingUnitRequest.class);
        return (CreateShippingUnitRequest) jaxbContext.createUnmarshaller()
            .unmarshal(entityStream);       
    } catch (JAXBException jaxbException) {
    	logger.error(jaxbException.getMessage(), jaxbException);
        throw new ProcessingException("Error deserializing a MyBean.",
            jaxbException);
    }
}
}