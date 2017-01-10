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
package com.cts.cip.agent.model;



public class PackageInfo {
	 public PackageDimensions getPackageDimensions() {
		return packageDimensions;
	}
	public void setPackageDimensions(PackageDimensions packageDimensions) {
		this.packageDimensions = packageDimensions;
	}

	public PackageWeightInfo getPackageWeightInfo() {
		return packageWeightInfo;
	}
	public void setPackageWeightInfo(PackageWeightInfo packageWeightInfo) {
		this.packageWeightInfo = packageWeightInfo;
	}
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	public String getPackageDesc() {
		return packageDesc;
	}
	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	private String packageID;
    private String packageDesc;
	private String packageType;
	private PackageDimensions packageDimensions;
	private PackageWeightInfo packageWeightInfo;
}
