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
package com.cts.cip.core.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.cip.common.model.Address;
import com.cts.cip.common.model.CarrierReference;
import com.cts.cip.common.model.Dimensions;
import com.cts.cip.common.model.DimensionsUOM;
import com.cts.cip.common.model.InvoiceDetails;
import com.cts.cip.common.model.Item;
import com.cts.cip.common.model.Order;
import com.cts.cip.common.model.ShipperDetails;
import com.cts.cip.common.model.ShipperServiceType;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitState;
import com.cts.cip.common.model.Weight;
import com.cts.cip.common.model.WeightAndDimensions;
import com.cts.cip.common.model.WeightUOM;
import com.cts.cip.core.repository.entities.AddressEntity;
import com.cts.cip.core.repository.entities.Invoice;
import com.cts.cip.core.repository.entities.InvoiceItem;
import com.cts.cip.core.repository.entities.ShippingOrder;
import com.cts.cip.core.repository.entities.ShippingUnitEntity;

@Service
public class ShippingUnitMapperImpl implements ShippingUnitMapper {

	@PersistenceContext
	private EntityManager em;

	Logger logger = LoggerFactory.getLogger(ShippingUnitMapperImpl.class);

	@Override
	public ShippingUnit mapToModel(ShippingUnitEntity entity) {
		// TODO : Need to expand this stub
		ShippingUnit shippingUnit = new ShippingUnit();
		ShippingUnitBase shippingUnitBase = new ShippingUnitBase();

		ShipperDetails shipperDetails = new ShipperDetails();
		ShipperServiceType serviceType = new ShipperServiceType();
		shipperDetails.setShipperServiceType(serviceType);
		shippingUnit.setShipperDetails(shipperDetails);

		shippingUnitBase.setId(entity.getId());
		shippingUnitBase.setReferenceID(entity.getReferanceId());
		shippingUnitBase.setState(ShippingUnitState.valueOf(entity.getStatus()));
		shippingUnit.setShippingUnitBase(shippingUnitBase);
		shippingUnit.setTrackingNumber(entity.getTrackingNumber());
		CarrierReference carrierReference =  new  CarrierReference();
		carrierReference.setId(String.valueOf(entity.getCarrierServiceId()));		
		shippingUnit.setCarrierReference(carrierReference);
		Dimensions dimensions = new Dimensions();
		dimensions.setHeight(entity.getHeight());
		dimensions.setLength(entity.getLength());
		dimensions.setWidth(entity.getWidth());
		dimensions.setDimensionUOM(DimensionsUOM.IN);

		Weight weight = new Weight();
		weight.setWeight(entity.getWeight());
		weight.setWeightUOM(WeightUOM.LBS);

		WeightAndDimensions weightDim = new WeightAndDimensions();
		weightDim.setDimensions(dimensions);
		weightDim.setWeight(weight);
		shippingUnit.setWeightAndDimensions(weightDim);
		shippingUnit.getShipperDetails().getShipperServiceType()
				.setShipperServiceTypeCode(entity.getCarrierServiceId().toString());
		//TBD - VALIDATION CHANGE
		shippingUnit.setLastUpdatedTimeStamp(entity.getLastUpdatedTimeStamp());
		return shippingUnit;
	}

	@Override
	public ShippingUnitEntity mapToEntity(ShippingUnit model) {

		ShippingUnitEntity suEntity = new ShippingUnitEntity();

		suEntity.setId(model.getShippingUnitBase().getId());
		suEntity.setReferanceId(model.getShippingUnitBase().getReferenceID());
		suEntity.setStatus(model.getShippingUnitBase().getState().value());
		if (model.getWeightAndDimensions() != null) {
			suEntity.setWeight(model.getWeightAndDimensions().getWeight().getWeight());
			suEntity.setLength(model.getWeightAndDimensions().getDimensions().getLength());
			suEntity.setWidth(model.getWeightAndDimensions().getDimensions().getWidth());
			suEntity.setHeight(model.getWeightAndDimensions().getDimensions().getHeight());
		}
		if (null != model.getShipperDetails())
			suEntity.setCarrierServiceId(
					new Integer(model.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode()));

		if (model.getInvoiceDetails() != null && model.getInvoiceDetails().getItems().size() > 0) {
			suEntity.setInvoice(mapToInvoiceEntity(model.getInvoiceDetails()));
		}
		if (model.getOrder() != null) {
			suEntity.setOrder(mapToSOEntity(model.getOrder()));
		}		
		suEntity.setLastUpdatedTimeStamp(new Date());

		return suEntity;
	}

	private ShippingOrder mapToSOEntity(Order oModel) {
		ShippingOrder oEntity = new ShippingOrder();
		Date date = new Date();

		if (oModel.getBillingAddress() != null) {
			oEntity.setBillingAddress(mapToAddressEntity(oModel.getBillingAddress()));
		}
		if (oModel.getDeliveryToAddress() != null) {
			oEntity.setDeliveryAddress(mapToAddressEntity(oModel.getDeliveryToAddress()));
		}
		if (oModel.getReturnAddress() != null) {
			oEntity.setReturnAddress(mapToAddressEntity(oModel.getReturnAddress()));
		}
		oEntity.setReferenceId(oModel.getOrderNumber());
		oEntity.setCreateTime(date);
		oEntity.setParentReferenceId(oModel.getPoNumber());
		oEntity.setTcn(oModel.getTrasactionControlNumber());
		oEntity.setUpdateTime(date);
		oEntity.setBusinessUnitId(oModel.getBusinessUnitID());
		oEntity.setNodeId(oModel.getDistributionCenterID());

		// TODO : Shipping from Address ??

		return oEntity;
	}

	private AddressEntity mapToAddressEntity(Address aModel) {
		AddressEntity lEntity = new AddressEntity();
		lEntity.setBusinessName(aModel.getCompanyName());
		lEntity.setFirstName(aModel.getIndividualName());
		lEntity.setAddressLine1(aModel.getAddressLine1());
		lEntity.setAddressLine2(aModel.getAddressLine2());
		lEntity.setAddressLine3(aModel.getAddressLine3());
		lEntity.setAddressLine4(aModel.getAddressLine4());
		lEntity.setCity(aModel.getCity());
		lEntity.setCountry(aModel.getCountry());
		lEntity.setStateCode(aModel.getState());
		lEntity.setZipCode(aModel.getZipCode());
		lEntity.setEmail(aModel.getEmailAddress());
		lEntity.setPrimaryPhoneNumber(aModel.getPhoneNumber());

		lEntity.setFax(aModel.getFaxNumber());

		return lEntity;
	}

	private Invoice mapToInvoiceEntity(InvoiceDetails ivModel) {
		Invoice ivEntity = new Invoice();

		if (ivModel.getItems().size() > 0) {
			mapToInvoiceItemEntity(ivModel.getItems(), ivEntity);

		}

		ivEntity.setSalesTax(ivModel.getSalesTax().getAmount());
		ivEntity.setShipmentTotal(ivModel.getShipmentTotalCost().getAmount());
		ivEntity.setShippingHandling(ivModel.getShippingAndHandlingCharges().getAmount());
		ivEntity.setSubTotal(ivModel.getTotalOrderItemsCost().getAmount());

		return ivEntity;
	}

	private List<InvoiceItem> mapToInvoiceItemEntity(List<Item> iviModelList, Invoice ivEntity) {

		List<InvoiceItem> invoiceItems = new ArrayList<>();

		for (Item invoiceItem : iviModelList) {
			StringBuilder builder = new StringBuilder();
			InvoiceItem iviEntity = new InvoiceItem();
			iviEntity.setInvoice(ivEntity);
			iviEntity.setDescription(invoiceItem.getDescription());
			iviEntity.setQuantity(invoiceItem.getQuantity());
			iviEntity.setTotalPrice(invoiceItem.getTotalPrice().getAmount());
			iviEntity.setUnitPrice(invoiceItem.getUnitPrice().getAmount());

			if(invoiceItem.getSecondaryDescriptions()!=null) {
				if(invoiceItem.getSecondaryDescriptions().getDescription()!=null) {
					for(String desc : invoiceItem.getSecondaryDescriptions().getDescription()){
						builder.append(desc + ",");
					}
				}
			}

			if(builder.length()>0) {
				builder.delete(builder.length()-1, builder.length());
				iviEntity.setSecondaryDescription(builder.toString());
				builder.delete(0, builder.length());
			}
			invoiceItems.add(iviEntity);
		}
		return invoiceItems;
	}
}
