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
package com.cts.cip.core.bo.services.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.agent.model.Address;
import com.cts.cip.agent.model.BillToAddress;
import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CarrierReferenceDetails;
import com.cts.cip.agent.model.CountryCode;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.DimensionsUOM;
import com.cts.cip.agent.model.InvoiceDetails;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.OrderDetails;
import com.cts.cip.agent.model.PackageDimensions;
import com.cts.cip.agent.model.PackageInfo;
import com.cts.cip.agent.model.PackageWeightInfo;
import com.cts.cip.agent.model.ShipToAddress;
import com.cts.cip.agent.model.ShipperAddress;
import com.cts.cip.agent.model.ShipperDetails;
import com.cts.cip.agent.model.ShipperService;
import com.cts.cip.agent.model.ShippingOptions;
import com.cts.cip.agent.model.SpecialService;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.WeightUOM;
import com.cts.cip.common.model.CarrierDetails;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.Order;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.utilities.CarrierDetail;

/**
 * 
 * @author
 *
 */
public class AgentServiceRequestMapperImpl implements AgentServiceRequestMapper {

	private CarrierDetail carrierDetail;

	Logger logger = LoggerFactory.getLogger(AgentServiceRequestMapperImpl.class);

	private ModelMapper modelMapper = new ModelMapper();

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public CarrierDetail getCarrierDetail() {
		return carrierDetail;
	}

	public void setCarrierDetail(CarrierDetail carrierDetail) {
		this.carrierDetail = carrierDetail;
	}

	@Override
	public CreateRequest mapToCreateRequest(ShippingUnit shippingUnit) {

		CreateRequest createRequest = new CreateRequest();
		createRequest.setShipperDetails(getShipperDetails(shippingUnit));
		createRequest.setShipToAddress(getShipToAddress(shippingUnit));
		createRequest.setOutputFileFormat(getOutputFileFormat(shippingUnit));
		createRequest.setOrderDetails(getOrderDetails(shippingUnit));
		if (shippingUnit.getCarrierReference() != null) {
			setReferenceDetails(shippingUnit, createRequest);
		}

		return createRequest;
	}

	private void setReferenceDetails(ShippingUnit shippingUnit, CreateRequest createRequest) {
		List<CarrierReferenceDetails> refDetails = createRequest.getReferenceDetails();
		CarrierReferenceDetails crd = new CarrierReferenceDetails();
		crd.setType(shippingUnit.getCarrierReference().getType());
		crd.setValue(shippingUnit.getCarrierReference().getId());
		refDetails.add(crd);

	}

	private OrderDetails getOrderDetails(ShippingUnit shippingUnit) {
		Order comOrder = shippingUnit.getOrder();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrderNumber(comOrder.getOrderNumber());
		orderDetails.setPoNumber(comOrder.getPoNumber());
		orderDetails.setTrasactionControlNumber(comOrder.getTrasactionControlNumber());

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sdf.parse(comOrder.getDatePlannedShipment()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage() + e.getCause());

		}
		orderDetails.setDatePlannedShipment(cal);
		orderDetails.setPackageInfo(getPackageInfo(shippingUnit));
		orderDetails.setShippingOptions(getShippingOptions(comOrder));
		orderDetails.setInvoiceDetails(getInvoiceDetails(shippingUnit));
		orderDetails.setBillToAddress(getBillToAddress(shippingUnit));

		return orderDetails;
	}

	/**
	 * Get BillToAddress
	 * 
	 * @param shippingUnit
	 * @return
	 */
	private BillToAddress getBillToAddress(ShippingUnit shippingUnit) {
		BillToAddress billToAddress = new BillToAddress();
		Address address = new Address();
		billToAddress.setAddress(setAddressDetails(shippingUnit.getOrder().getBillingAddress(), address));
		return billToAddress;
	}

	/**
	 * Get Invoice Details
	 * 
	 * @param shippingUnit
	 * @return
	 */
	private InvoiceDetails getInvoiceDetails(ShippingUnit shippingUnit) {
		InvoiceDetails invoiceDetails = new InvoiceDetails();
		invoiceDetails.setTotalOrderItemsCost(
				shippingUnit.getInvoiceDetails().getTotalOrderItemsCost().getAmount().doubleValue());
		invoiceDetails.setCurrency(shippingUnit.getInvoiceDetails().getTotalOrderItemsCost().getCurrency().toString());
		return invoiceDetails;
	}

	/**
	 * Get Shipping Options
	 * 
	 * @param comOrder
	 * @return
	 */
	private ShippingOptions getShippingOptions(Order comOrder) {
		ShippingOptions so = new ShippingOptions();
		com.cts.cip.common.model.ShippingOptions coreSO = comOrder.getShippingOptions();
		if (coreSO != null && coreSO.getSpecialServiceType() != null) {
			switch (coreSO.getSpecialServiceType()) {
			case SIGNATURE_ON_CONFIRMATION:
				so.setSpecialServiceType(SpecialService.DELIVERY_CONFIRM_SIGN_REQD);
				break;
			case RESIDENTIAL:
				so.setSpecialServiceType(SpecialService.RESIDENTIAL);
				break;
			case CASH_ON_DELIVERY:
				so.setSpecialServiceType(SpecialService.CASH_ON_DELIVERY);
				break;
			case HOLD_AT_LOCATION:
				so.setSpecialServiceType(SpecialService.HOLD_AT_LOCATION);
				break;
			case HAZARDOUS_MATERIAL:
				so.setSpecialServiceType(SpecialService.HAZARDOUS_MATERIAL);
				break;
			default:
				break;

			}

			so.setSpecialInstructions(coreSO.getSpecialInstructions());
		}

		return so;
	}

	/**
	 * Get Package Info
	 * 
	 * @param shippingUnit
	 * @return
	 */
	private PackageInfo getPackageInfo(ShippingUnit shippingUnit) {

		PackageInfo packageInfo = new PackageInfo();
		packageInfo.setPackageID(shippingUnit.getShippingUnitBase().getReferenceID());
		packageInfo.setPackageDesc(shippingUnit.getShippingUnitBase().getDescription());
		packageInfo.setPackageType(shippingUnit.getShippingUnitBase().getType().toString());

		PackageDimensions pkgDimensions = new PackageDimensions();
		pkgDimensions.setDimensionUOM(DimensionsUOM
				.fromValue(shippingUnit.getWeightAndDimensions().getDimensions().getDimensionUOM().toString()));
		pkgDimensions.setHeight(shippingUnit.getWeightAndDimensions().getDimensions().getHeight().floatValue());
		pkgDimensions.setLength(shippingUnit.getWeightAndDimensions().getDimensions().getLength().floatValue());
		pkgDimensions.setWidth(shippingUnit.getWeightAndDimensions().getDimensions().getWidth().floatValue());

		PackageWeightInfo packageWeightInfo = new PackageWeightInfo();
		packageWeightInfo.setPackageWeight(shippingUnit.getWeightAndDimensions().getWeight().getWeight().doubleValue());
		packageWeightInfo.setWeightUOM(
				WeightUOM.fromValue(shippingUnit.getWeightAndDimensions().getWeight().getWeightUOM().toString()));

		packageInfo.setPackageDimensions(pkgDimensions);
		packageInfo.setPackageWeightInfo(packageWeightInfo);
		return packageInfo;
	}

	private PackageInfo getPackageInfo(ShippingUnitBase shippingUnit) {
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.setPackageID(shippingUnit.getReferenceID());
		return packageInfo;
	}

	/**
	 * Get OutputFile Format
	 * 
	 * @param shippingUnit
	 * @return
	 */
	private String getOutputFileFormat(ShippingUnit shippingUnit) {
		return shippingUnit.getRequestedOutputFileFormat().toString();

	}

	/**
	 * Get ShipToAddress
	 * 
	 * @param shippingUnit
	 * @return
	 */
	private ShipToAddress getShipToAddress(ShippingUnit shippingUnit) {
		ShipToAddress shipToAddress = new ShipToAddress();
		Address address = new Address();
		shipToAddress.setAddress(setAddressDetails(shippingUnit.getOrder().getDeliveryToAddress(), address));
		return shipToAddress;
	}

	private Address setAddressDetails(com.cts.cip.common.model.Address deliveryToAddress, Address address) {
		address.setAddress1(deliveryToAddress.getAddressLine1());
		address.setAddress2(deliveryToAddress.getAddressLine2());
		address.setAddress3(deliveryToAddress.getAddressLine3());
		address.setAddress4(deliveryToAddress.getAddressLine4());
		address.setCity(deliveryToAddress.getCity());
		address.setCompanyName(deliveryToAddress.getCompanyName());
		address.setCountry(deliveryToAddress.getCountry());
		if(deliveryToAddress.getCountryCode()!=null) {
			address.setCountryCode(CountryCode.fromValue(deliveryToAddress.getCountryCode().toString()));
		} else {
			address.setCountryCode(CountryCode.fromValue("US"));
		}
		address.setEmailAddress(deliveryToAddress.getEmailAddress());
		address.setFaxNumber(deliveryToAddress.getFaxNumber());
		address.setIndividualName(deliveryToAddress.getIndividualName());
		address.setPhoneNumber(deliveryToAddress.getPhoneNumber());
		address.setState(deliveryToAddress.getState());
		address.setZipCode(deliveryToAddress.getZipCode());
		return address;
	}

	/**
	 * Get Shipper Details
	 * 
	 * @param comShipUnit
	 * @return
	 */
	private ShipperDetails getShipperDetails(ShippingUnit comShipUnit) {

		ShipperDetails shipperDetails = new ShipperDetails();
		shipperDetails.setShipperServiceType(getServiceType(comShipUnit));
		shipperDetails.setShipperAddress(getShipperAddress(comShipUnit));
		CarrierDetails cd = carrierDetail.getServiceDetailsMap().get(getServiceType(comShipUnit).getServiceTypeCode());
		logger.debug("carrierDetailsMap : " + cd.getAccountID());
		if (cd != null) {
			shipperDetails.setNodeId(cd.getNodeId());
			shipperDetails.setBillingAccountID(cd.getAccountID());
		}
		return shipperDetails;
	}

	/**
	 * Get Shipper Address
	 * 
	 * @param comShipUnit
	 * @return
	 */
	private ShipperAddress getShipperAddress(ShippingUnit comShipUnit) {
		ShipperAddress shipperAddress = new ShipperAddress();
		Address address = new Address();
		shipperAddress.setAddress(setAddressDetails(comShipUnit.getShipperDetails().getShipperAddress(), address));
		return shipperAddress;
	}

	/**
	 * Get the Service Type
	 * 
	 * @param comShipUnit
	 * @return
	 */
	public ShipperService getServiceType(ShippingUnit comShipUnit) {
		ShipperService serviceType = new ShipperService();
		serviceType.setServiceTypeCode(
				comShipUnit.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode());
		serviceType.setServiceTypeName(
				comShipUnit.getShipperDetails().getShipperServiceType().getShipperServiceTypeName());
		return serviceType;
	}

	@Override
	public CancelRequest mapToCancelRequest(ShippingUnit shippingUnit) {
		CancelRequest cancelRequest = new CancelRequest();
		List<String> trackingNumList = new ArrayList<>();
		trackingNumList.add(shippingUnit.getTrackingNumber());
		cancelRequest.setTrackingNumberList(trackingNumList);
		cancelRequest.setShipperServiceType(getServiceType(shippingUnit));
		return cancelRequest;
	}

	/*
	 * @Override public ManifestRequest
	 * mapToManifestRequest(List<ShippingUnitBase> shippingUnitList, String
	 * carrierName) { ManifestRequest manifestRequest = new ManifestRequest();
	 * List<PackageInfo> packageList = new ArrayList<>(); for (ShippingUnitBase
	 * shippingUnit : shippingUnitList) {
	 * packageList.add(getPackageInfo(shippingUnit)); }
	 * manifestRequest.setPackageList(packageList);
	 * manifestRequest.setShipperServiceType(getManifestShipperServiceType(
	 * carrierName)); return manifestRequest; }
	 */
	public ManifestRequest mapToManifestRequest(Load load) {
		ManifestRequest manifestRequest = new ManifestRequest();
		List<PackageInfo> packageList = new ArrayList<>();
		for (ShippingUnitBase shippingUnit : load.getShippingUnits()) {
			packageList.add(getPackageInfo(shippingUnit));
		}
		LoadBase loadBase = load.getLoadDetails();
		manifestRequest.setPackageList(packageList);
		manifestRequest.setShipperServiceType(
				getManifestShipperServiceType(loadBase.getCarrierID(), loadBase.getCarrierName()));
		CarrierDetails cd = carrierDetail.getCarrierDetailsMap().get(loadBase.getCarrierName());
		manifestRequest.setBillingAccountID(cd.getAccountID());
		return manifestRequest;
	}

	private ShipperService getManifestShipperServiceType(String carrierId, String carrierName) {
		ShipperService shipperService = new ShipperService();
		shipperService.setCarrierId(carrierId);
		shipperService.setCarrierName(carrierName);
		return shipperService;
	}

	@Override
	public LoadRequest mapToLoadRequest(ShippingUnit shippingUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnLoadRequest mapToUnLoadRequest(ShippingUnit shippingUnit) {
		// TODO Auto-generated method stub
		return null;
	}
}
