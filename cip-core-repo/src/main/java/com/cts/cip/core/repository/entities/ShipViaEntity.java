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
package com.cts.cip.core.repository.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ship_via")
public class ShipViaEntity {	

	@Id	
	@Column(name="ship_via")
	private  String shipVia;
	
	@Column(name="carrier_service_code")
	private String carrierServiecCode;	
	
	
	public String getShipVia() {
		return shipVia;
	}
	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}
	public String getCarrierServiecCode() {
		return carrierServiecCode;
	}
	public void setCarrierServiecCode(String carrierServiecCode) {
		this.carrierServiecCode = carrierServiecCode;
	}	
		
}