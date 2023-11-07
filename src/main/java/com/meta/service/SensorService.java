package com.meta.service;

import org.springframework.http.ResponseEntity;

import com.meta.dto.CSensorDTO;
import com.meta.dto.DefaultSensorDTO;
import com.meta.dto.Result_SensorDTO;
import com.meta.dto.SensorNotificaitonDTO;
import com.meta.dto.Sensor_pinDTO;
import com.meta.vo.Message;

public interface SensorService {

	public ResponseEntity<Message> AdminSensorSearch(DefaultSensorDTO dto) throws Exception; 
	
	public ResponseEntity<Message> AdminSensorList() throws Exception;
	
	public ResponseEntity<Message> warningSensor() throws Exception;
	
	public ResponseEntity<Message> AIwarningSensor() throws Exception;
	
	public ResponseEntity<Message> warningCCTV() throws Exception;
	
	public ResponseEntity<Message> warningDailyMessage() throws Exception;
	
	public ResponseEntity<Message> SensorInventory2(CSensorDTO dto) throws Exception;
	
	public void SensorDelete() throws Exception;
	
	public ResponseEntity<Message> AISensorInsert(String sensor_name[]) throws Exception;
	
	public void AssginTest() throws Exception;
	
	public ResponseEntity<Message> GasList(Integer number_of_data) throws Exception;
	
	public void SensorTest() throws Exception;
	
	public void SensorTest2() throws Exception;
	
	public void SensorReusltTest() throws Exception;
	
	public ResponseEntity<Message> SelectListPin(Sensor_pinDTO dto) throws Exception;
	
	public ResponseEntity<Message> OneSensorList(Result_SensorDTO dto) throws Exception;
	
	public ResponseEntity<Message> SensorGraphList(Result_SensorDTO dto) throws Exception;
	
	public ResponseEntity<Message> SensorInventory() throws Exception;
	
	public ResponseEntity<Message> DefaultSensorList() throws Exception;
	
	public ResponseEntity<Message> ValueSensorList() throws Exception;
	
	public ResponseEntity<Message> RegisterPinSensor(Sensor_pinDTO dto) throws Exception;
	
	public ResponseEntity<Message> DeletePinSensor(Sensor_pinDTO dto) throws Exception;
	
	public ResponseEntity<Message> BasicSensorDataList() throws Exception;

}
