package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.SensorNotificaitonDTO;



public class SensorNotificationDAOImpl implements SensorNotificationDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.sensor_notificationMapper";
	
	@Override
	public List<SensorNotificaitonDTO> warningSensor() throws Exception {
		return sqlsession.selectOne(namespace+".warningSensor");
	}

	@Override
	public List<SensorNotificaitonDTO> AIwarningSensor() throws Exception {
		return sqlsession.selectOne(namespace+".AIwarningSensor");
	}

	@Override
	public List<SensorNotificaitonDTO> warningCCTV() throws Exception {
		return sqlsession.selectOne(namespace+".warningCCTV");
	}
	
	@Override
	public List<SensorNotificaitonDTO> warningDailyMessage() throws Exception {
		return sqlsession.selectOne(namespace+".warningDailyMessage");
	}
	
	@Override
	public void NotificaitonUpdate(SensorNotificaitonDTO dto) throws Exception {
		sqlsession.update(namespace+".NotificaitonUpdate", dto);
	}
	
	@Override
	public int NotificationCount(String target_user_id) throws Exception {
		return sqlsession.selectOne(namespace+".NotificationCount", target_user_id);
	}
	
	@Override
	public List<SensorNotificaitonDTO> NotificationList(String target_user_id) throws Exception {
		return sqlsession.selectList(namespace+".NotificationList", target_user_id);
	}
	
	@Override
	public void RegisterPredictNotification(SensorNotificaitonDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterPredictNotification", dto);
	}
	
	@Override
	public void RegisterSensorNotification(SensorNotificaitonDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterSensorNotification", dto);
	}

}
