package com.cts.cip.core.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.CarrierAgentFailure;
import com.cts.cip.common.model.Status;
import com.cts.cip.common.tracker.model.DeliveryDetails;
import com.cts.cip.common.tracker.model.ServiceStatus;
import com.cts.cip.common.tracker.model.ShipmentStatus;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.ShipmentTrackResponse;
import com.cts.cip.common.tracker.model.SpecialServiceOptions;
import com.cts.cip.common.tracker.model.TrackActivity;
import com.cts.cip.common.tracker.model.TrackActivityContainer;
import com.cts.cip.common.tracker.model.TrackResponseContainer;
import com.cts.cip.common.tracker.model.TrackUnitResponse;
import com.cts.cip.common.tracker.model.TrackerSpecialService;
import com.cts.cip.core.bo.services.CarrierAgentService;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.util.NotificationManager;

@Component
@Path("/tracker")
public class ShipmentTracker {
	static Logger logger = LoggerFactory.getLogger(ShipmentTracker.class);
	
	@Autowired
	private CarrierAgentService carrierAgentService;
	
	
	@Autowired
	NotificationManager notificationManager;
	
	

	@GET
	@Path("/test")
	@Produces({ "application/xml", "application/json" })
	public Response testResource(@QueryParam("input") String input) {
		System.out.println("Inside testResource");
		System.out.println("Input : " + input);
		return Response.ok("Hello : " + input).build();
	}

	@POST
	@Path("/track")
	@Produces({ "application/xml", "application/json" })
	public Response getTrackingDetails(@Context HttpHeaders headers, ShipmentTrackRequest trackRequest) {
		
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();	
		TrackResponseContainer trackResponse = null;
		String sampleRequest = "<shipmentTrackRequest><carrier>UPS</carrier><TrackingNumbers><TrackingNumber>123546788</TrackingNumber>"
				+ "<TrackingNumber>345</TrackingNumber> </TrackingNumbers> </shipmentTrackRequest>";

		/*if (null != trackRequest && null != trackRequest.getTrackNumberContainer()) {

			List<String> trackNumList = trackRequest.getTrackNumberContainer().getTrackingNumList();

			ShipmentTrackResponse trackResponse = new ShipmentTrackResponse();
			Status status = new Status();
			status.setResponseStatusDescription("SUCCESS");
			trackResponse.setStatus(status);
			TrackUnitResponse tuResponse1 = null;
			TrackUnitResponse tuResponse2 = null;
			TrackResponseContainer trespContainer = new TrackResponseContainer();
			int numOfElements = trackNumList.size();
			switch(numOfElements){
				case 2: 
					 tuResponse2 = getTrackUnitResponse2(trackNumList.get(1));
					 trespContainer.getTrackResponseList().add(tuResponse2);
				case 1:  	 
					 tuResponse1 = getTrackUnitResponse1(trackNumList.get(0));
					 trespContainer.getTrackResponseList().add(tuResponse1);
					 break;
				default:
					break;
			}
			
			
			trackResponse.setTrackResponseContainer(trespContainer);
			builder = Response.ok(trackResponse, headers.getMediaType());
			

		}

		if (null == builder) {
			builder = Response.ok("Please use the following input structure : " + sampleRequest,
					headers.getMediaType());
		}*/

		if (null != trackRequest && null != trackRequest.getTrackNumberContainer()) {
			try {
				trackResponse = carrierAgentService.getTrackingDetails(trackRequest);
			} catch (CarrierAgentFailure | CarrierAgentException e) {
				logger.error(e.getMessage(), e);
				notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.GET_SHIPUNIT_NOTIF_SUB );
				return responseBuilder.build(e, headers.getMediaType(),"");
			}
			
		}
		return responseBuilder.build(trackResponse, headers.getMediaType(),"");

	}

	private TrackUnitResponse getTrackUnitResponse1(String tNum) {
		TrackUnitResponse tuResponse1 = new TrackUnitResponse();
		tuResponse1.setCarrier("UPS");
		tuResponse1.setTrackingNumber(tNum);
		tuResponse1.setCurrentStatus(ShipmentStatus.DELIVERED);
		tuResponse1.setSpecialServicePresent(ServiceStatus.YES);
		SpecialServiceOptions spOptions = new SpecialServiceOptions();
		spOptions.setCode(TrackerSpecialService.DELIVERY_CONFIRM_SIGN_REQD);
		spOptions.setDescription(TrackerSpecialService.DELIVERY_CONFIRM_SIGN_REQD.getDescription());
		tuResponse1.setSpecialServiceOptions(spOptions);
		
		
		List<TrackActivity> activities = new ArrayList<>();
		TrackActivity trackActivity1 = new TrackActivity();
		trackActivity1.setActivityDate("2016-11-07 10:00:00 AM");
		trackActivity1.setActivityLocation("TAMPA, FL");
		trackActivity1.setActivityStatusCode(ShipmentStatus.PICKED_UP);
		trackActivity1.setStatusDescription("The Item was picked up from warehouse");

		
		activities.add(trackActivity1);
		TrackActivity trackActivity2 = new TrackActivity();
		trackActivity2.setActivityDate("2016-11-08 10:00:00 AM");
		trackActivity2.setActivityLocation("ATLANTA, GA");
		trackActivity2.setActivityStatusCode(ShipmentStatus.DELIVERED);
		trackActivity2.setStatusDescription("ITEM has been Delivered");
		activities.add(trackActivity2);
		TrackActivityContainer activityContainer = new TrackActivityContainer();
		activityContainer.setAcvityDetails(activities);

		tuResponse1.setActivityContainer(activityContainer);
		
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		deliveryDetails.setDeliveredDate("2016-11-08 10:00:00 AM");
		deliveryDetails.setDeliveryLocation("ATLANTA, GA");
		deliveryDetails.setReceivedBy("FRONT DESK");
		tuResponse1.setDeliveryDetails(deliveryDetails);
		return tuResponse1;
	}
	
	private TrackUnitResponse getTrackUnitResponse2(String tNum) {
		TrackUnitResponse tuResponse1 = new TrackUnitResponse();
		tuResponse1.setCarrier("UPS");
		tuResponse1.setCurrentStatus(ShipmentStatus.IN_TRANSIT);
		tuResponse1.setTrackingNumber(tNum);
		

		
		List<TrackActivity> activities = new ArrayList<>();
		TrackActivity trackActivity = new TrackActivity();
		trackActivity.setActivityDate("2016-11-05 10:00:00 AM");
		trackActivity.setActivityLocation("TAMPA, FL");
		trackActivity.setActivityStatusCode(ShipmentStatus.PICKED_UP);
		trackActivity.setStatusDescription("The Item was picked up from warehouse");
		trackActivity.setNextActivityDate("2016-11-17 10:00:00 AM");
		activities.add(trackActivity);
		
		TrackActivity trackActivity2 = new TrackActivity();
		trackActivity2.setActivityDate("2016-11-08 10:00:00 AM");
		trackActivity2.setActivityLocation("EDISON, NJ");
		trackActivity2.setActivityStatusCode(ShipmentStatus.IN_TRANSIT);
		trackActivity2.setStatusDescription("ITEM is in Transit");
		activities.add(trackActivity2);
		TrackActivityContainer activityContainer = new TrackActivityContainer();
		activityContainer.setAcvityDetails(activities);

		tuResponse1.setActivityContainer(activityContainer);
		return tuResponse1;
	}

	public CarrierAgentService getCarrierAgentService() {
		return carrierAgentService;
	}

	public void setCarrierAgentService(CarrierAgentService carrierAgentService) {
		this.carrierAgentService = carrierAgentService;
	}

}
