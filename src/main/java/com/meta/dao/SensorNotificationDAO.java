package com.meta.dao;

import java.util.List;

import com.meta.dto.SensorNotificaitonDTO;

public interface SensorNotificationDAO {

	public List<SensorNotificaitonDTO> warningSensor() throws Exception;

	public List<SensorNotificaitonDTO> AIwarningSensor() throws Exception;

	public List<SensorNotificaitonDTO> warningCCTV() throws Exception;
	
	public List<SensorNotificaitonDTO> warningDailyMessage() throws Exception;
	
	public void RegisterPredictNotification(SensorNotificaitonDTO dto) throws Exception;
	
	public void NotificaitonUpdate(SensorNotificaitonDTO dto) throws Exception;
	
	public int NotificationCount(String target_user_id) throws Exception;
	
	public List<SensorNotificaitonDTO> NotificationList(String target_user_id) throws Exception;
	
	public void RegisterSensorNotification(SensorNotificaitonDTO dto) throws Exception; 

}
