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
package com.cts.cip.core.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.LoadState;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.repository.entities.LoadEntity;
import com.cts.cip.core.repository.entities.LoadShippingUnit;


/**
 * @author 417765
 *
 */
@Service
public class LoadMapperImpl implements LoadMapper {

	/**
	 * 
	 */
	public LoadMapperImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cts.cip.core.repository.mappers.LoadMapper#mapEntityToModel(com.cts.
	 * cip.core.repository.entities.LoadUnit)
	 */
	@Override
	public Load mapToModel(LoadEntity entity) {

		Load model = new Load();
		model.setLoadDetails(new LoadBase());
		model.getLoadDetails().setId(entity.getId());
		model.getLoadDetails().setLoadReferenceId(entity.getReferenceId());

		model.getLoadDetails().setLoadState(LoadState.valueOf(entity.getStatus()));

		if (entity.getLoadShippingUnits() != null && !entity.getLoadShippingUnits().isEmpty()) {
			for (LoadShippingUnit loadSUEntity : entity.getLoadShippingUnits()) {

				ShippingUnitBase shippingUnit = new ShippingUnitBase();
				shippingUnit.setReferenceID(loadSUEntity.getShippingUnitRefId());
				model.getShippingUnits().add(shippingUnit);

			}
		}
		
		model.getLoadDetails().setCarrierID(String.valueOf(entity.getCarrier_id()));
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cts.cip.core.repository.mappers.LoadMapper#mapModelToEntity(com.cts.
	 * cip.common.model.Load)
	 */
	@Override
	public LoadEntity mapToEntity(Load load) {

		LoadEntity loadEntity = new LoadEntity();
		loadEntity.setCreateDate(new Date());
		loadEntity.setReferenceId(load.getLoadDetails().getLoadReferenceId());

		loadEntity.setStatus(load.getLoadDetails().getLoadState().value());
		if (load.getLoadDetails().getId() != null) {
			// Do we really need this? .... Hmm... Need to decide
			loadEntity.setId(load.getLoadDetails().getId());
		}

		loadEntity.setCreateDate(new Date());
		loadEntity.setUpdateTime(new Date());
		
		loadEntity.setCarrier_id(Integer.parseInt(load.getLoadDetails().getCarrierID()));
		
		return loadEntity;
	}

	@Override
	public LoadInfo mapToLoadInfo(LoadEntity loadEntity) {
		LoadInfo loadInfo = new LoadInfo();
	
		loadInfo.setLoadBase(new LoadBase());
		loadInfo.getLoadBase().setId(loadEntity.getId());
		loadInfo.getLoadBase().setLoadReferenceId(loadEntity.getReferenceId());
		loadInfo.getLoadBase().setLoadState(LoadState.valueOf(loadEntity.getStatus()));
		if(loadEntity.getLoadShippingUnits()!=null){
			loadInfo.getLoadBase().setTotalUnitCount(loadEntity.getLoadShippingUnits().size());
		}
		List<ShipmentLoadInfo> shipmentLoadInfoList = new ArrayList<>();
		
		for (LoadShippingUnit loadShippingUnit: loadEntity.getLoadShippingUnits()) {
			ShipmentLoadInfo shipmentLoadInfo =  new ShipmentLoadInfo();
			shipmentLoadInfo.setShipReferenceId(loadShippingUnit.getShippingUnitRefId());
			shipmentLoadInfo.setLoadStatus(loadShippingUnit.getLoad().getStatus());
			shipmentLoadInfo.setLoadReferenceId(loadShippingUnit.getLoad().getReferenceId());			
			shipmentLoadInfoList.add(shipmentLoadInfo);
		}
		loadInfo.setShipmentLoadInfoList(shipmentLoadInfoList);
		return loadInfo;
		
		
	}

	@Override
	public ShipmentLoadInfo mapToLoadShipmentInfo(ShippingUnit shippingUnit,ShipmentLoadInfo paritalShipmentLoadInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ShipmentLoadInfo  shipmentLoadInfo =  new ShipmentLoadInfo();
		shipmentLoadInfo.setShipReferenceId(shippingUnit.getShippingUnitBase().getReferenceID());	
		shipmentLoadInfo.setTrackingNumber(shippingUnit.getTrackingNumber());
		shipmentLoadInfo.setShipperServiceId(shippingUnit.getShipperDetails().getShipperServiceType().getShipperServiceTypeID());
		shipmentLoadInfo.setShipmentStatus(shippingUnit.getShippingUnitBase().getState().value());		
		shipmentLoadInfo.setLoadReferenceId(paritalShipmentLoadInfo.getLoadReferenceId());
		shipmentLoadInfo.setLoadStatus(paritalShipmentLoadInfo.getLoadStatus());		
		shipmentLoadInfo.setLastUpdatedTmstmp(sdf.format(shippingUnit.getLastUpdatedTimeStamp()));
		return shipmentLoadInfo;
	}

}
