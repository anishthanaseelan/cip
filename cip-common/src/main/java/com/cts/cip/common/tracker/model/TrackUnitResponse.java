package com.cts.cip.common.tracker.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TrackUnit")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackUnitResponse {
	private String carrier;
	private String trackingNumber;
	private String service;
	private String shipmentWeight;
	private String shipmentWeightUOM;
	private List<CustomerReferenceDetail> referenceDetails;
	
	private ShipmentStatus currentStatus;
	//private String currentStatus;
	private ServiceStatus isShipmentRedirected;
	private ServiceStatus isSpecialServicePresent;
	@XmlElement
	private DeliveryDetails deliveryDetails;
	private String estimatedDeliveryDate;
	@XmlElement
	private ShipmentRedirectDetails shipmentRedirectDetails;
	@XmlElement
	private SpecialServiceOptions specialServiceOptions;
	@XmlElement(name="AcvityDetails")
	private TrackActivityContainer activityContainer;
	@XmlElement(name="AdditionalDetails")
	private AdditionalTrackDetails additionalDetails;
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getShipmentWeight() {
		return shipmentWeight;
	}
	public void setShipmentWeight(String shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}
	public String getShipmentWeightUOM() {
		return shipmentWeightUOM;
	}
	public void setShipmentWeightUOM(String shipmentWeightUOM) {
		this.shipmentWeightUOM = shipmentWeightUOM;
	}
	public List<CustomerReferenceDetail> getReferenceDetails() {
		return referenceDetails;
	}
	public void setReferenceDetails(List<CustomerReferenceDetail> referenceDetails) {
		this.referenceDetails = referenceDetails;
	}

	public ServiceStatus isShipmentRedirected() {
		return isShipmentRedirected;
	}
	public void setShipmentRedirected(ServiceStatus isShipmentRedirected) {
		this.isShipmentRedirected = isShipmentRedirected;
	}
	public ServiceStatus isSpecialServicePresent() {
		return isSpecialServicePresent;
	}
	public void setSpecialServicePresent(ServiceStatus isSpecialServicePresent) {
		this.isSpecialServicePresent = isSpecialServicePresent;
	}
	public DeliveryDetails getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public String getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}
	public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}
	public ShipmentRedirectDetails getShipmentRedirectDetails() {
		return shipmentRedirectDetails;
	}
	public void setShipmentRedirectDetails(ShipmentRedirectDetails shipmentRedirectDetails) {
		this.shipmentRedirectDetails = shipmentRedirectDetails;
	}
	public SpecialServiceOptions getSpecialServiceOptions() {
		return specialServiceOptions;
	}
	public void setSpecialServiceOptions(SpecialServiceOptions specialServiceOptions) {
		this.specialServiceOptions = specialServiceOptions;
	}
	
	public AdditionalTrackDetails getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(AdditionalTrackDetails additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public TrackActivityContainer getActivityContainer() {
		return activityContainer;
	}
	public void setActivityContainer(TrackActivityContainer activityContainer) {
		this.activityContainer = activityContainer;
	}
	public void setCurrentStatus(ShipmentStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	public ShipmentStatus getCurrentStatus() {
		return currentStatus;
	}


}
