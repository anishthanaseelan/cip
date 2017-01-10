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
package com.cts.cip.master.resource;

import org.glassfish.jersey.jackson.JacksonFeature;
//import org.glassfish.jersey.moxy.xml.MoxyXmlFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;


public class RestApplicationController extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public RestApplicationController() {
        register(RequestContextFilter.class);
        register(CORSFilter.class);
        register(CarrierResource.class);
        register(NodeResource.class);
        register(JacksonFeature.class);       
        packages("com.cts.cip");
        
        
    }
}