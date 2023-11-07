package com.meta.dto;

import java.util.List;

public class PlantListDTO {

	public List<PlantDTO> Zone1List;
	public List<PlantDTO> Zone2List;
	public List<PlantDTO> Zone3List;
	
	public List<PlantDTO> getZone1List() {
		return Zone1List;
	}
	public void setZone1List(List<PlantDTO> zone1List) {
		Zone1List = zone1List;
	}
	public List<PlantDTO> getZone2List() {
		return Zone2List;
	}
	public void setZone2List(List<PlantDTO> zone2List) {
		Zone2List = zone2List;
	}
	public List<PlantDTO> getZone3List() {
		return Zone3List;
	}
	public void setZone3List(List<PlantDTO> zone3List) {
		Zone3List = zone3List;
	}
	
}
