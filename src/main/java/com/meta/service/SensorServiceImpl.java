package com.meta.service;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.dao.AssignmentDAO;
import com.meta.dao.CSensorDAO;
import com.meta.dao.SensorNotificationDAO;
import com.meta.dao.Sensor_pinDAO;
import com.meta.dao.UserDAO;
import com.meta.dto.AssignListDTO;
import com.meta.dto.CSenorDataDTO;
import com.meta.dto.CSensorDTO;
import com.meta.dto.DbSensor_InsertDTO;
import com.meta.dto.DefaultSensorDTO;
import com.meta.dto.GSSensorDTO;
import com.meta.dto.GasDTO;
import com.meta.dto.GasSendDTO;
import com.meta.dto.GasSensorDTO;
import com.meta.dto.Mv_SensorDTO;
import com.meta.dto.Object10DTO;
import com.meta.dto.Object11DTO;
import com.meta.dto.Object12DTO;
import com.meta.dto.Object13DTO;
import com.meta.dto.Object14DTO;
import com.meta.dto.Object15DTO;
import com.meta.dto.Object16DTO;
import com.meta.dto.Object17DTO;
import com.meta.dto.Object18DTO;
import com.meta.dto.Object1DTO;
import com.meta.dto.Object2DTO;
import com.meta.dto.Object3DTO;
import com.meta.dto.Object4DTO;
import com.meta.dto.Object5DTO;
import com.meta.dto.Object6DTO;
import com.meta.dto.Object7DTO;
import com.meta.dto.Object8DTO;
import com.meta.dto.Object9DTO;
import com.meta.dto.RSensorDTO;
import com.meta.dto.Result_SensorDTO;
import com.meta.dto.SensorDTO;
import com.meta.dto.SensorDataDTO;
import com.meta.dto.SensorNotificaitonDTO;
import com.meta.dto.Sensor_ObjectDTO;
import com.meta.dto.Sensor_pinDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.Zone1_SectionlistDTO;
import com.meta.dto.Zone2_SectionlistDTO;
import com.meta.dto.Zone3_SectionlistDTO;
import com.meta.dto.Zone4_SectionlistDTO;
import com.meta.dto.ZoneDTO;
import com.meta.dto.ZonelistDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;
import com.sensor.dao.SensorDAO;

@Service
public class SensorServiceImpl implements SensorService {

	@Autowired
	SensorDAO sensorDAO;
	
	@Autowired
	CSensorDAO csensorDAO;
	
	@Autowired
	Sensor_pinDAO sensor_pinDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	AssignmentDAO assgindao;
	
	@Autowired
	SensorNotificationDAO sensornotidao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();

	String time1 = format1.format(time);
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Message> AdminSensorSearch(DefaultSensorDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<DefaultSensorDTO> result = csensorDAO.AdminSensorSearch(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("센서 정보 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("센서 정보 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
		
	}
	
	@Override
	public ResponseEntity<Message> AdminSensorList() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<DefaultSensorDTO> result = csensorDAO.AdminSensorList();
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("센서 정보 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("센서 정보 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	@Override
	public ResponseEntity<Message> SensorInventory2(CSensorDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		if(dto.getSection_name().equals("ALL")) {
			dto.setSection_name("");
		}
		if(dto.getZone_name().equals("ALL")) {
			dto.setZone_name("");
		}
		
		List<CSensorDTO> Zone_Section_List = csensorDAO.Zone_Secetion_Sensor_List(dto);
		
		System.out.println(Zone_Section_List.size());
		
		List<Result_SensorDTO> result = new ArrayList<Result_SensorDTO>();
		
		if(Zone_Section_List.size() == 0) {
			
			message.setResult("fail");
			message.setMessage("해당 값 존재하지 않음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
		
		for(int i = 0; i < Zone_Section_List.size(); i++) {
			
			List<Result_SensorDTO> Data_Result = csensorDAO.Read_Sensor_Data(Zone_Section_List.get(i).sensor_name);
			
			result.add(i, Data_Result.get(0));
		}
		
		message.setResult("success");
		message.setMessage("센서 조회 성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(result);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
	}
	
	@Override
	public void SensorDelete() throws Exception {
		
		csensorDAO.SensorDelete();
		
	}
	
	@Override
	public ResponseEntity<Message> AISensorInsert(String[] sensor_name) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		AssignListDTO assgindto = new AssignListDTO();
		
		assgindto.setArea_code("mk_factory_ansan_1");
		
		List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
		
		SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
		
		for(int i = 0; i < result.size(); i++) {
			
			for(int j = 0; j < sensor_name.length; j++) {
				
				
				notificationdto.setTarget_user_id(result.get(i).user_id);
				notificationdto.setTitle(sensor_name[j] + "센서 이상 징후 감지");
				notificationdto.setContents(sensor_name[j] + "센서의 이상징후를 감지 하였습니다."
						+ "실시간 계측값 기반으로 이상 징후를 감지하였으며 센서 주변 시설물의 주의가 필요로 합니다.");
				notificationdto.setSensor_name(sensor_name[j]);
			
				sensornotidao.RegisterPredictNotification(notificationdto);
				
			}
			
		}
		
		message.setResult("success");
		message.setMessage("센서 이상감지 데이터 등록완료");
		message.setStatus(StatusEnum.OK);
		message.setData(null);
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	@Override
	public void AssginTest() throws Exception {
		
		AssignListDTO assgindto = new AssignListDTO();
		
		assgindto.setArea_code("mk_factory_ansan_1");
		
		List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
		
		System.out.println(result.size());
		
		for(int i = 0; i < result.size(); i++) {
			
			System.out.println(result.get(i).user_id);
		}
	}
	
	@Override
	public ResponseEntity<Message> warningSensor() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(sensornotidao.warningSensor());
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("센서 조회 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	@Override
	public ResponseEntity<Message> AIwarningSensor() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(sensornotidao.AIwarningSensor());
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("센서 조회 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
		
	}
	
	@Override
	public ResponseEntity<Message> warningCCTV() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(sensornotidao.warningCCTV());
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("센서 조회 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	@Override
	public ResponseEntity<Message> warningDailyMessage() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(sensornotidao.warningDailyMessage());
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("센서 조회 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	
	@Override
	public ResponseEntity<Message> GasList(Integer number_of_data) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		GasDTO gasobj = new GasDTO();
		
		if(number_of_data == null) {
			
			number_of_data = 10;
			
			List<GasSensorDTO> Gas_AC01G04 = csensorDAO.Gas_AC01G04(number_of_data);
			List<GasSensorDTO> Gas_AC01G01 = csensorDAO.Gas_AC01G01(number_of_data);
			List<GasSensorDTO> Gas_AC01G02 = csensorDAO.Gas_AC01G02(number_of_data);
			List<GasSensorDTO> Gas_AA10G03 = csensorDAO.Gas_AA10G03(number_of_data);
			List<GasSensorDTO> Gas_AA10G04 = csensorDAO.Gas_AA10G04(number_of_data);
			List<GasSensorDTO> Gas_AA12G03 = csensorDAO.Gas_AA12G03(number_of_data);
			List<GasSensorDTO> Gas_AA12G05 = csensorDAO.Gas_AA12G05(number_of_data);
			List<GasSensorDTO> Gas_AA12G06 = csensorDAO.Gas_AA12G06(number_of_data);
			List<GasSensorDTO> Gas_AA12G07 = csensorDAO.Gas_AA12G07(number_of_data);
			List<GasSensorDTO> Gas_AA12G01 = csensorDAO.Gas_AA12G01(number_of_data);
			List<GasSensorDTO> Gas_AA12G02 = csensorDAO.Gas_AA12G02(number_of_data);
			List<GasSensorDTO> Gas_AA14G05 = csensorDAO.Gas_AA14G05(number_of_data);
			List<GasSensorDTO> Gas_AA14G06 = csensorDAO.Gas_AA14G06(number_of_data);
			List<GasSensorDTO> Gas_AA14G07 = csensorDAO.Gas_AA14G07(number_of_data);
			List<GasSensorDTO> Gas_AA14G08 = csensorDAO.Gas_AA14G08(number_of_data);
			List<GasSensorDTO> Gas_AA14G09 = csensorDAO.Gas_AA14G09(number_of_data);
			List<GasSensorDTO> Gas_AA15G01 = csensorDAO.Gas_AA15G01(number_of_data);
			List<GasSensorDTO> Gas_AA15G02 = csensorDAO.Gas_AA15G02(number_of_data);
			List<GasSensorDTO> Gas_AA16G09 = csensorDAO.Gas_AA16G09(number_of_data);
			List<GasSensorDTO> Gas_AA16G10 = csensorDAO.Gas_AA16G10(number_of_data);
			List<GasSensorDTO> Gas_AA16G11 = csensorDAO.Gas_AA16G11(number_of_data);
			List<GasSensorDTO> Gas_AA16G12 = csensorDAO.Gas_AA16G12(number_of_data);
			List<GasSensorDTO> Gas_AB28G01 = csensorDAO.Gas_AB28G01(number_of_data);
			List<GasSensorDTO> Gas_AB28G02 = csensorDAO.Gas_AB28G02(number_of_data);
			
			gasobj.setAC01G04(Gas_AC01G04);
			gasobj.setAC01G01(Gas_AC01G01);
			gasobj.setAC01G02(Gas_AC01G02);
			gasobj.setAA10G03(Gas_AA10G03);
			gasobj.setAA10G04(Gas_AA10G04);
			gasobj.setAA12G03(Gas_AA12G03);
			gasobj.setAA12G05(Gas_AA12G05);
			gasobj.setAA12G06(Gas_AA12G06);
			gasobj.setAA12G07(Gas_AA12G07);
			gasobj.setAA12G01(Gas_AA12G01);
			gasobj.setAA12G02(Gas_AA12G02);
			gasobj.setAA14G05(Gas_AA14G05);
			gasobj.setAA14G06(Gas_AA14G06);
			gasobj.setAA14G07(Gas_AA14G07);
			gasobj.setAA14G08(Gas_AA14G08);
			gasobj.setAA14G09(Gas_AA14G09);
			gasobj.setAA15G01(Gas_AA15G01);
			gasobj.setAA15G02(Gas_AA15G02);
			gasobj.setAA16G09(Gas_AA16G09);
			gasobj.setAA16G10(Gas_AA16G10);
			gasobj.setAA16G11(Gas_AA16G11);
			gasobj.setAA16G12(Gas_AA16G12);
			gasobj.setAB28G01(Gas_AB28G01);
			gasobj.setAB28G02(Gas_AB28G02);
			
		}else {
			
			List<GasSensorDTO> Gas_AC01G04 = csensorDAO.Gas_AC01G04(number_of_data);
			List<GasSensorDTO> Gas_AC01G01 = csensorDAO.Gas_AC01G01(number_of_data);
			List<GasSensorDTO> Gas_AC01G02 = csensorDAO.Gas_AC01G02(number_of_data);
			List<GasSensorDTO> Gas_AA10G03 = csensorDAO.Gas_AA10G03(number_of_data);
			List<GasSensorDTO> Gas_AA10G04 = csensorDAO.Gas_AA10G04(number_of_data);
			List<GasSensorDTO> Gas_AA12G03 = csensorDAO.Gas_AA12G03(number_of_data);
			List<GasSensorDTO> Gas_AA12G05 = csensorDAO.Gas_AA12G05(number_of_data);
			List<GasSensorDTO> Gas_AA12G06 = csensorDAO.Gas_AA12G06(number_of_data);
			List<GasSensorDTO> Gas_AA12G07 = csensorDAO.Gas_AA12G07(number_of_data);
			List<GasSensorDTO> Gas_AA12G01 = csensorDAO.Gas_AA12G01(number_of_data);
			List<GasSensorDTO> Gas_AA12G02 = csensorDAO.Gas_AA12G02(number_of_data);
			List<GasSensorDTO> Gas_AA14G05 = csensorDAO.Gas_AA14G05(number_of_data);
			List<GasSensorDTO> Gas_AA14G06 = csensorDAO.Gas_AA14G06(number_of_data);
			List<GasSensorDTO> Gas_AA14G07 = csensorDAO.Gas_AA14G07(number_of_data);
			List<GasSensorDTO> Gas_AA14G08 = csensorDAO.Gas_AA14G08(number_of_data);
			List<GasSensorDTO> Gas_AA14G09 = csensorDAO.Gas_AA14G09(number_of_data);
			List<GasSensorDTO> Gas_AA15G01 = csensorDAO.Gas_AA15G01(number_of_data);
			List<GasSensorDTO> Gas_AA15G02 = csensorDAO.Gas_AA15G02(number_of_data);
			List<GasSensorDTO> Gas_AA16G09 = csensorDAO.Gas_AA16G09(number_of_data);
			List<GasSensorDTO> Gas_AA16G10 = csensorDAO.Gas_AA16G10(number_of_data);
			List<GasSensorDTO> Gas_AA16G11 = csensorDAO.Gas_AA16G11(number_of_data);
			List<GasSensorDTO> Gas_AA16G12 = csensorDAO.Gas_AA16G12(number_of_data);
			List<GasSensorDTO> Gas_AB28G01 = csensorDAO.Gas_AB28G01(number_of_data);
			List<GasSensorDTO> Gas_AB28G02 = csensorDAO.Gas_AB28G02(number_of_data);
			
			gasobj.setAC01G04(Gas_AC01G04);
			gasobj.setAC01G01(Gas_AC01G01);
			gasobj.setAC01G02(Gas_AC01G02);
			gasobj.setAA10G03(Gas_AA10G03);
			gasobj.setAA10G04(Gas_AA10G04);
			gasobj.setAA12G03(Gas_AA12G03);
			gasobj.setAA12G05(Gas_AA12G05);
			gasobj.setAA12G06(Gas_AA12G06);
			gasobj.setAA12G07(Gas_AA12G07);
			gasobj.setAA12G01(Gas_AA12G01);
			gasobj.setAA12G02(Gas_AA12G02);
			gasobj.setAA14G05(Gas_AA14G05);
			gasobj.setAA14G06(Gas_AA14G06);
			gasobj.setAA14G07(Gas_AA14G07);
			gasobj.setAA14G08(Gas_AA14G08);
			gasobj.setAA14G09(Gas_AA14G09);
			gasobj.setAA15G01(Gas_AA15G01);
			gasobj.setAA15G02(Gas_AA15G02);
			gasobj.setAA16G09(Gas_AA16G09);
			gasobj.setAA16G10(Gas_AA16G10);
			gasobj.setAA16G11(Gas_AA16G11);
			gasobj.setAA16G12(Gas_AA16G12);
			gasobj.setAB28G01(Gas_AB28G01);
			gasobj.setAB28G02(Gas_AB28G02);
		}
		
		try {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(gasobj);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("센서 조회 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
		

		
	}
	
	@Override
	public ResponseEntity<Message> OneSensorList(Result_SensorDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<Result_SensorDTO> result = csensorDAO.OneSensorList(dto);
		
		if(result.size() > 0) {
		
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else {
			
			message.setResult("fail");
			message.setMessage("해당 값 존재하지 않음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> SensorGraphList(Result_SensorDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<Result_SensorDTO> result = csensorDAO.SensorGraphList(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("센서 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else {
			
			message.setResult("fail");
			message.setMessage("해당 값 존재하지 않음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	
	@Override
	public void SensorReusltTest() throws Exception {
		
	}
	
	@Override
	public ResponseEntity<Message> SensorInventory() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ZonelistDTO zonedto = new ZonelistDTO();
		
		Zone1_SectionlistDTO zone1List = new Zone1_SectionlistDTO();
		
		Zone2_SectionlistDTO zone2List = new Zone2_SectionlistDTO();
		
		Zone3_SectionlistDTO zone3List = new Zone3_SectionlistDTO();
		
		Zone4_SectionlistDTO zone4List = new Zone4_SectionlistDTO();
		
		Result_SensorDTO AD37C01 = new Result_SensorDTO();
		Result_SensorDTO AA10C01 = new Result_SensorDTO();
		Result_SensorDTO AD37B05 = new Result_SensorDTO();
		Result_SensorDTO AD37B03 = new Result_SensorDTO();
		Result_SensorDTO AD37B01 = new Result_SensorDTO();
		Result_SensorDTO AD37B02 = new Result_SensorDTO();
		Result_SensorDTO AD37B04 = new Result_SensorDTO();
		Result_SensorDTO AA12G01 = new Result_SensorDTO();
		Result_SensorDTO AB28G01 = new Result_SensorDTO();
		Result_SensorDTO AA12G02 = new Result_SensorDTO();
		Result_SensorDTO AB28G02 = new Result_SensorDTO();
		Result_SensorDTO AA10G03 = new Result_SensorDTO();
		Result_SensorDTO AA10G04 = new Result_SensorDTO();
		Result_SensorDTO AA14G05 = new Result_SensorDTO();
		Result_SensorDTO AA14G06 = new Result_SensorDTO();
		Result_SensorDTO AA14G07 = new Result_SensorDTO();
		Result_SensorDTO AA14G08 = new Result_SensorDTO();
		Result_SensorDTO AA16G09 = new Result_SensorDTO();
		Result_SensorDTO AA16G10 = new Result_SensorDTO();
		Result_SensorDTO AA16G11 = new Result_SensorDTO();
		Result_SensorDTO AA16G12 = new Result_SensorDTO();
		Result_SensorDTO AA12G03 = new Result_SensorDTO();
		Result_SensorDTO AA12G05 = new Result_SensorDTO();
		Result_SensorDTO AA12G06 = new Result_SensorDTO();
		Result_SensorDTO AA12G07 = new Result_SensorDTO();
		Result_SensorDTO AA14G09 = new Result_SensorDTO();
		Result_SensorDTO AA15G01 = new Result_SensorDTO();
		Result_SensorDTO AA15G02 = new Result_SensorDTO();
		Result_SensorDTO AC01G04 = new Result_SensorDTO();
		Result_SensorDTO AD35G01 = new Result_SensorDTO();
		Result_SensorDTO AD35G02 = new Result_SensorDTO();
		Result_SensorDTO AD35G03 = new Result_SensorDTO();
		Result_SensorDTO AD35G04 = new Result_SensorDTO();
		Result_SensorDTO AD36G01 = new Result_SensorDTO();
		Result_SensorDTO AD36G02 = new Result_SensorDTO();
		Result_SensorDTO AD36G03 = new Result_SensorDTO();
		Result_SensorDTO AD36G04 = new Result_SensorDTO();
		Result_SensorDTO AD37G01 = new Result_SensorDTO();
		Result_SensorDTO AD37G02 = new Result_SensorDTO();
		Result_SensorDTO AD37G03 = new Result_SensorDTO();
		Result_SensorDTO AD37G04 = new Result_SensorDTO();
		Result_SensorDTO AD37A06 = new Result_SensorDTO();
		Result_SensorDTO AD37A05 = new Result_SensorDTO();
		Result_SensorDTO AA12L01 = new Result_SensorDTO();
		Result_SensorDTO AB23L01 = new Result_SensorDTO();
		Result_SensorDTO AC01L01 = new Result_SensorDTO();
		Result_SensorDTO AA12L02 = new Result_SensorDTO();
		Result_SensorDTO AB23L02 = new Result_SensorDTO();
		Result_SensorDTO AC01L02 = new Result_SensorDTO();
		Result_SensorDTO AA14L03 = new Result_SensorDTO();
		Result_SensorDTO AB23L03 = new Result_SensorDTO();
		Result_SensorDTO AC01L03 = new Result_SensorDTO();
		Result_SensorDTO AA16L04 = new Result_SensorDTO();
		Result_SensorDTO AB23L04 = new Result_SensorDTO();
		Result_SensorDTO AC01L04 = new Result_SensorDTO();
		Result_SensorDTO AA16L05 = new Result_SensorDTO();
		Result_SensorDTO AB23L12 = new Result_SensorDTO();
		Result_SensorDTO AB24L05 = new Result_SensorDTO();
		Result_SensorDTO AA16L06 = new Result_SensorDTO();
		Result_SensorDTO AB23L13 = new Result_SensorDTO();
		Result_SensorDTO AB24L06 = new Result_SensorDTO();
		Result_SensorDTO AA16L07 = new Result_SensorDTO();
		Result_SensorDTO AB24L07 = new Result_SensorDTO();
		Result_SensorDTO AA16L08 = new Result_SensorDTO();
		Result_SensorDTO AB24L08 = new Result_SensorDTO();
		Result_SensorDTO AA16L09 = new Result_SensorDTO();
		Result_SensorDTO AB24L09 = new Result_SensorDTO();
		Result_SensorDTO AA16L10 = new Result_SensorDTO();
		Result_SensorDTO AB31L10 = new Result_SensorDTO();
		Result_SensorDTO AA16L11 = new Result_SensorDTO();
		Result_SensorDTO AB31L11 = new Result_SensorDTO();
		Result_SensorDTO AA16L12 = new Result_SensorDTO();
		Result_SensorDTO AA15L13 = new Result_SensorDTO();
		Result_SensorDTO AA15L14 = new Result_SensorDTO();
		Result_SensorDTO AB27L14 = new Result_SensorDTO();
		Result_SensorDTO AA15L15 = new Result_SensorDTO();
		Result_SensorDTO AB27L15 = new Result_SensorDTO();
		Result_SensorDTO AA15L16 = new Result_SensorDTO();
		Result_SensorDTO AB32L16 = new Result_SensorDTO();
		Result_SensorDTO AA15L17 = new Result_SensorDTO();
		Result_SensorDTO AB32L17 = new Result_SensorDTO();
		Result_SensorDTO AA20L18 = new Result_SensorDTO();
		Result_SensorDTO AB28L18 = new Result_SensorDTO();
		Result_SensorDTO AA14L19 = new Result_SensorDTO();
		Result_SensorDTO AB28L19 = new Result_SensorDTO();
		Result_SensorDTO AA12L20 = new Result_SensorDTO();
		Result_SensorDTO AB28L20 = new Result_SensorDTO();
		Result_SensorDTO AB28L21 = new Result_SensorDTO();
		Result_SensorDTO AB27L22 = new Result_SensorDTO();
		Result_SensorDTO AB27L23 = new Result_SensorDTO();
		Result_SensorDTO AB27L24 = new Result_SensorDTO();
		Result_SensorDTO AB27L25 = new Result_SensorDTO();
		Result_SensorDTO AB22L01 = new Result_SensorDTO();
		Result_SensorDTO AB22L02 = new Result_SensorDTO();
		Result_SensorDTO AB22L03 = new Result_SensorDTO();
		Result_SensorDTO AB22L04 = new Result_SensorDTO();
		Result_SensorDTO AB22L05 = new Result_SensorDTO();
		Result_SensorDTO AB27L21 = new Result_SensorDTO();
		Result_SensorDTO AC08L01 = new Result_SensorDTO();
		Result_SensorDTO AC08L02 = new Result_SensorDTO();
		Result_SensorDTO AA10L01 = new Result_SensorDTO();
		Result_SensorDTO AA10L02 = new Result_SensorDTO();
		Result_SensorDTO AA10F06 = new Result_SensorDTO();
		Result_SensorDTO AD37A02 = new Result_SensorDTO();
		Result_SensorDTO AD37A01 = new Result_SensorDTO();
		Result_SensorDTO AA12P01 = new Result_SensorDTO();
		Result_SensorDTO AB31P01 = new Result_SensorDTO();
		Result_SensorDTO AC01G01 = new Result_SensorDTO();
		Result_SensorDTO AA12P02 = new Result_SensorDTO();
		Result_SensorDTO AB27P02 = new Result_SensorDTO();
		Result_SensorDTO AC01G02 = new Result_SensorDTO();
		Result_SensorDTO AA10P03 = new Result_SensorDTO();
		Result_SensorDTO AD37A04 = new Result_SensorDTO();
		Result_SensorDTO AD37A03 = new Result_SensorDTO();
		
	
		Object1DTO obj1dto = new Object1DTO();
		Object2DTO obj2dto = new Object2DTO();
		Object3DTO obj3dto = new Object3DTO();
		Object4DTO obj4dto = new Object4DTO();
		Object5DTO obj5dto = new Object5DTO();
		Object6DTO obj6dto = new Object6DTO();
		Object7DTO obj7dto = new Object7DTO();
		Object8DTO obj8dto = new Object8DTO();
		Object9DTO obj9dto = new Object9DTO();
		Object10DTO obj10dto = new Object10DTO();
		Object11DTO obj11dto = new Object11DTO();
		Object12DTO obj12dto = new Object12DTO();
		Object13DTO obj13dto = new Object13DTO();
		Object14DTO obj14dto = new Object14DTO();
		Object15DTO obj15dto = new Object15DTO();
		Object16DTO obj16dto = new Object16DTO();
		Object17DTO obj17dto = new Object17DTO();
		Object18DTO obj18dto = new Object18DTO();
		
		
		List<Mv_SensorDTO> Read_AA12G01 = csensorDAO.Read_AA12G01();
		
		
		//1
		//없는 거
		//List<Mv_SensorDTO> Read_AD37C01 = csensorDAO.Read_AD37C01();
		
		AD37C01.setArea_code("mk_factory_ansan_1");
		AD37C01.setSensor_name("CCTV(#4-37-01)");
		AD37C01.setSensor_status("-9999");
		AD37C01.setDetection_target("CCTV");
		AD37C01.setTime(Read_AA12G01.get(0).time);
		AD37C01.setSs_full_id("AD37C01");
		AD37C01.setValue("-9999");
		
		obj18dto.setAD37C01(AD37C01);
		
		//2
		
		//List<Mv_SensorDTO> Read_AA10C01 = csensorDAO.Read_AA10C01();
		
		AA10C01.setArea_code("mk_factory_ansan_1");
		AA10C01.setSensor_name("CCTV(불꽃감지(ZONE #1))");
		AA10C01.setSensor_status("-9999");
		AA10C01.setDetection_target("불꽃");
		AA10C01.setTime(Read_AA12G01.get(0).time);
		AA10C01.setSs_full_id("AA10C01");
		AA10C01.setValue("-9999");
		
		obj3dto.setAA10C01(AA10C01);
		
		//3
		
		//List<Mv_SensorDTO> Read_AD37B05 = csensorDAO.Read_AD37B05();
		
		AD37B05.setArea_code("mk_factory_ansan_1");
		AD37B05.setSensor_name("H2S-복합대기(#4-37-05)");
		AD37B05.setSensor_status("-9999");
		AD37B05.setDetection_target("H2S(외부)");
		AD37B05.setTime(Read_AA12G01.get(0).time);
		AD37B05.setSs_full_id("AD37B05");
		AD37B05.setValue("-9999");
		
		obj18dto.setAD37B05(AD37B05);
		
		//4
		
		//List<Mv_SensorDTO> Read_AD37B03 = csensorDAO.Read_AD37B03();
		
		AD37B03.setArea_code("mk_factory_ansan_1");
		AD37B03.setSensor_name("NH3-복합대기(#4-37-03)");
		AD37B03.setSensor_status("-9999");
		AD37B03.setDetection_target("NH3(외부)");
		AD37B03.setTime(Read_AA12G01.get(0).time);
		AD37B03.setSs_full_id("AD37B03");
		AD37B03.setValue("-9999");
		
		obj18dto.setAD37B03(AD37B03);
		
		//5
		
		//List<Mv_SensorDTO> Read_AD37B01 = csensorDAO.Read_AD37B01();
		
		AD37B01.setArea_code("mk_factory_ansan_1");
		AD37B01.setSensor_name("PM10-복합PM(#4-37-01)");
		AD37B01.setSensor_status("-9999");
		AD37B01.setDetection_target("PM10");
		AD37B01.setTime(Read_AA12G01.get(0).time);
		AD37B01.setSs_full_id("AD37B01");
		AD37B01.setValue("-9999");
		
		obj18dto.setAD37B01(AD37B01);
		
		//6
		
		//List<Mv_SensorDTO> Read_AD37B02 = csensorDAO.Read_AD37B02();
		
		AD37B02.setArea_code("mk_factory_ansan_1");
		AD37B02.setSensor_name("PM2.5-복합PM(#4-37-02)");
		AD37B02.setSensor_status("-9999");
		AD37B02.setDetection_target("PM2.5");
		AD37B02.setTime(Read_AA12G01.get(0).time);
		AD37B02.setSs_full_id("AD37B02");
		AD37B02.setValue("-9999");
		
		obj18dto.setAD37B02(AD37B02);
		
		//7
		
		//List<Mv_SensorDTO> Read_AD37B04 = csensorDAO.Read_AD37B04();
		
		AD37B04.setArea_code("mk_factory_ansan_1");
		AD37B04.setSensor_name("VOC-복합대기(#4-37-04)");
		AD37B04.setSensor_status("-9999");
		AD37B04.setDetection_target("VOC(외부)");
		AD37B04.setTime(Read_AA12G01.get(0).time);
		AD37B04.setSs_full_id("AD37B04");
		AD37B04.setValue("-9999");
		
		obj18dto.setAD37B04(AD37B04);
		
		//8
		//없는 게 아니라 대타용
		//List<Mv_SensorDTO> Read_AA12G01 = csensorDAO.Read_AA12G01();
		
		AA12G01.setArea_code(Read_AA12G01.get(0).area_code);
		AA12G01.setSensor_name(Read_AA12G01.get(0).sensor_name);
		AA12G01.setSensor_status(Read_AA12G01.get(0).sensor_status);
		AA12G01.setDetection_target("암모니아");
		AA12G01.setTime(Read_AA12G01.get(0).time);
		AA12G01.setSs_full_id(Read_AA12G01.get(0).ss_full_id);
		AA12G01.setValue(Read_AA12G01.get(0).value);
		
		obj4dto.setAA12G01(AA12G01);
		
		//9
		
		List<Mv_SensorDTO> Read_AB28G01 = csensorDAO.Read_AB28G01();
		
		AB28G01.setArea_code(Read_AB28G01.get(0).area_code);
		AB28G01.setSensor_name(Read_AB28G01.get(0).sensor_name);
		AB28G01.setSensor_status(Read_AB28G01.get(0).sensor_status);
		AB28G01.setDetection_target("염산");
		AB28G01.setTime(Read_AB28G01.get(0).time);
		AB28G01.setSs_full_id(Read_AB28G01.get(0).ss_full_id);
		AB28G01.setValue(Read_AB28G01.get(0).value);
		
		obj13dto.setAB28G01(AB28G01);
		
		//10
		
		List<Mv_SensorDTO> Read_AA12G02 = csensorDAO.Read_AA12G02();
		
		AA12G02.setArea_code(Read_AA12G02.get(0).area_code);
		AA12G02.setSensor_name(Read_AA12G02.get(0).sensor_name);
		AA12G02.setSensor_status(Read_AA12G02.get(0).sensor_status);
		AA12G02.setDetection_target("에틸렌디아민");
		AA12G02.setTime(Read_AA12G02.get(0).time);
		AA12G02.setSs_full_id(Read_AA12G02.get(0).ss_full_id);
		AA12G02.setValue(Read_AA12G02.get(0).value);
		
		obj4dto.setAA12G02(AA12G02);
		
		//11
		
		List<Mv_SensorDTO> Read_AB28G02 = csensorDAO.Read_AB28G02();
		
		AB28G02.setArea_code(Read_AB28G02.get(0).area_code);
		AB28G02.setSensor_name(Read_AB28G02.get(0).sensor_name);
		AB28G02.setSensor_status(Read_AB28G02.get(0).sensor_status);
		AB28G02.setDetection_target("암모니아");
		AB28G02.setTime(Read_AB28G02.get(0).time);
		AB28G02.setSs_full_id(Read_AB28G02.get(0).ss_full_id);
		AB28G02.setValue(Read_AB28G02.get(0).value);
		
		obj13dto.setAB28G02(AB28G02);
		
		//12
		
		List<Mv_SensorDTO> Read_AA10G03 = csensorDAO.Read_AA10G03();
		
		AA10G03.setArea_code(Read_AA10G03.get(0).area_code);
		AA10G03.setSensor_name(Read_AA10G03.get(0).sensor_name);
		AA10G03.setSensor_status(Read_AA10G03.get(0).sensor_status);
		AA10G03.setDetection_target("에틸렌디아민");
		AA10G03.setTime(Read_AA10G03.get(0).time);
		AA10G03.setSs_full_id(Read_AA10G03.get(0).ss_full_id);
		AA10G03.setValue(Read_AA10G03.get(0).value);
		
		obj3dto.setAA10G03(AA10G03);
		
		//13
		
		List<Mv_SensorDTO> Read_AA10G04 = csensorDAO.Read_AA10G04();
		
		AA10G04.setArea_code(Read_AA10G04.get(0).area_code);
		AA10G04.setSensor_name(Read_AA10G04.get(0).sensor_name);
		AA10G04.setSensor_status(Read_AA10G04.get(0).sensor_status);
		AA10G04.setDetection_target("인화성");
		AA10G04.setTime(Read_AA10G04.get(0).time);
		AA10G04.setSs_full_id(Read_AA10G04.get(0).ss_full_id);
		AA10G04.setValue(Read_AA10G04.get(0).value);
		
		obj3dto.setAA10G04(AA10G04);
		
		//14
		
		List<Mv_SensorDTO> Read_AA14G05 = csensorDAO.Read_AA14G05();
		
		AA14G05.setArea_code(Read_AA14G05.get(0).area_code);
		AA14G05.setSensor_name(Read_AA14G05.get(0).sensor_name);
		AA14G05.setSensor_status(Read_AA14G05.get(0).sensor_status);
		AA14G05.setDetection_target("포르말린");
		AA14G05.setTime(Read_AA14G05.get(0).time);
		AA14G05.setSs_full_id(Read_AA14G05.get(0).ss_full_id);
		AA14G05.setValue(Read_AA14G05.get(0).value);
		
		obj5dto.setAA14G05(AA14G05);
		
		//15
		
		List<Mv_SensorDTO> Read_AA14G06 = csensorDAO.Read_AA14G06();
		
		AA14G06.setArea_code(Read_AA14G06.get(0).area_code);
		AA14G06.setSensor_name(Read_AA14G06.get(0).sensor_name);
		AA14G06.setSensor_status(Read_AA14G06.get(0).sensor_status);
		AA14G06.setDetection_target("염산");
		AA14G06.setTime(Read_AA14G06.get(0).time);
		AA14G06.setSs_full_id(Read_AA14G06.get(0).ss_full_id);
		AA14G06.setValue(Read_AA14G06.get(0).value);
		
		obj5dto.setAA14G06(AA14G06);
		
		//16
		
		List<Mv_SensorDTO> Read_AA14G07 = csensorDAO.Read_AA14G07();
		
		AA14G07.setArea_code(Read_AA14G07.get(0).area_code);
		AA14G07.setSensor_name(Read_AA14G07.get(0).sensor_name);
		AA14G07.setSensor_status(Read_AA14G07.get(0).sensor_status);
		AA14G07.setDetection_target("에틸렌디아민");
		AA14G07.setTime(Read_AA14G07.get(0).time);
		AA14G07.setSs_full_id(Read_AA14G07.get(0).ss_full_id);
		AA14G07.setValue(Read_AA14G07.get(0).value);
		
		obj5dto.setAA14G07(AA14G07);
		
		//17
		
		List<Mv_SensorDTO> Read_AA14G08 = csensorDAO.Read_AA14G08();
		
		AA14G08.setArea_code(Read_AA14G08.get(0).area_code);
		AA14G08.setSensor_name(Read_AA14G08.get(0).sensor_name);
		AA14G08.setSensor_status(Read_AA14G08.get(0).sensor_status);
		AA14G08.setDetection_target("포르말린");
		AA14G08.setTime(Read_AA14G08.get(0).time);
		AA14G08.setSs_full_id(Read_AA14G08.get(0).ss_full_id);
		AA14G08.setValue(Read_AA14G08.get(0).value);
		
		obj5dto.setAA14G08(AA14G08);
		
		//18
		
		List<Mv_SensorDTO> Read_AA16G09 = csensorDAO.Read_AA16G09();
		
		AA16G09.setArea_code(Read_AA16G09.get(0).area_code);
		AA16G09.setSensor_name(Read_AA16G09.get(0).sensor_name);
		AA16G09.setSensor_status(Read_AA16G09.get(0).sensor_status);
		AA16G09.setDetection_target("브롬산");
		AA16G09.setTime(Read_AA16G09.get(0).time);
		AA16G09.setSs_full_id(Read_AA16G09.get(0).ss_full_id);
		AA16G09.setValue(Read_AA16G09.get(0).value);
		
		obj7dto.setAA16G09(AA16G09);
		
		//19
		
		List<Mv_SensorDTO> Read_AA16G10 = csensorDAO.Read_AA16G10();
		
		AA16G10.setArea_code(Read_AA16G10.get(0).area_code);
		AA16G10.setSensor_name(Read_AA16G10.get(0).sensor_name);
		AA16G10.setSensor_status(Read_AA16G10.get(0).sensor_status);
		AA16G10.setDetection_target("염산");
		AA16G10.setTime(Read_AA16G10.get(0).time);
		AA16G10.setSs_full_id(Read_AA16G10.get(0).ss_full_id);
		AA16G10.setValue(Read_AA16G10.get(0).value);
		
		obj7dto.setAA16G10(AA16G10);
		
		//20
		
		List<Mv_SensorDTO> Read_AA16G11 = csensorDAO.Read_AA16G11();
		
		AA16G11.setArea_code(Read_AA16G11.get(0).area_code);
		AA16G11.setSensor_name(Read_AA16G11.get(0).sensor_name);
		AA16G11.setSensor_status(Read_AA16G11.get(0).sensor_status);
		AA16G11.setDetection_target("암모니아수");
		AA16G11.setTime(Read_AA16G11.get(0).time);
		AA16G11.setSs_full_id(Read_AA16G11.get(0).ss_full_id);
		AA16G11.setValue(Read_AA16G11.get(0).value);
		
		obj7dto.setAA16G11(AA16G11);
		
		//21
		
		List<Mv_SensorDTO> Read_AA16G12 = csensorDAO.Read_AA16G12();
		
		AA16G12.setArea_code(Read_AA16G12.get(0).area_code);
		AA16G12.setSensor_name(Read_AA16G12.get(0).sensor_name);
		AA16G12.setSensor_status(Read_AA16G12.get(0).sensor_status);
		AA16G12.setDetection_target("질산");
		AA16G12.setTime(Read_AA16G12.get(0).time);
		AA16G12.setSs_full_id(Read_AA16G12.get(0).ss_full_id);
		AA16G12.setValue(Read_AA16G12.get(0).value);
		
		obj7dto.setAA16G12(AA16G12);
		
		//22
		
		List<Mv_SensorDTO> Read_AA12G03 = csensorDAO.Read_AA12G03();
		
		AA12G03.setArea_code(Read_AA12G03.get(0).area_code);
		AA12G03.setSensor_name(Read_AA12G03.get(0).sensor_name);
		AA12G03.setSensor_status(Read_AA12G03.get(0).sensor_status);
		AA12G03.setDetection_target("암모니아");
		AA12G03.setTime(Read_AA12G03.get(0).time);
		AA12G03.setSs_full_id(Read_AA12G03.get(0).ss_full_id);
		AA12G03.setValue(Read_AA12G03.get(0).value);
		
		obj4dto.setAA12G03(AA12G03);
		
		//23
		
		List<Mv_SensorDTO> Read_AA12G05 = csensorDAO.Read_AA12G05();
		
		AA12G05.setArea_code(Read_AA12G05.get(0).area_code);
		AA12G05.setSensor_name(Read_AA12G05.get(0).sensor_name);
		AA12G05.setSensor_status(Read_AA12G05.get(0).sensor_status);
		AA12G05.setDetection_target("암모니아");
		AA12G05.setTime(Read_AA12G05.get(0).time);
		AA12G05.setSs_full_id(Read_AA12G05.get(0).ss_full_id);
		AA12G05.setValue(Read_AA12G05.get(0).value);
		
		obj4dto.setAA12G05(AA12G05);
		
		//24
		
		List<Mv_SensorDTO> Read_AA12G06 = csensorDAO.Read_AA12G06();
		
		AA12G06.setArea_code(Read_AA12G06.get(0).area_code);
		AA12G06.setSensor_name(Read_AA12G06.get(0).sensor_name);
		AA12G06.setSensor_status(Read_AA12G06.get(0).sensor_status);
		AA12G06.setDetection_target("포르말린");
		AA12G06.setTime(Read_AA12G06.get(0).time);
		AA12G06.setSs_full_id(Read_AA12G06.get(0).ss_full_id);
		AA12G06.setValue(Read_AA12G06.get(0).value);
		
		obj4dto.setAA12G06(AA12G06);
		
		//25
		
		List<Mv_SensorDTO> Read_AA12G07 = csensorDAO.Read_AA12G07();
		
		AA12G07.setArea_code(Read_AA12G07.get(0).area_code);
		AA12G07.setSensor_name(Read_AA12G07.get(0).sensor_name);
		AA12G07.setSensor_status(Read_AA12G07.get(0).sensor_status);
		AA12G07.setDetection_target("에틸렌디아민");
		AA12G07.setTime(Read_AA12G07.get(0).time);
		AA12G07.setSs_full_id(Read_AA12G07.get(0).ss_full_id);
		AA12G07.setValue(Read_AA12G07.get(0).value);
		
		obj4dto.setAA12G07(AA12G07);
		
		//26
		
		List<Mv_SensorDTO> Read_AA14G09 = csensorDAO.Read_AA14G09();
		
		AA14G09.setArea_code(Read_AA14G09.get(0).area_code);
		AA14G09.setSensor_name(Read_AA14G09.get(0).sensor_name);
		AA14G09.setSensor_status(Read_AA14G09.get(0).sensor_status);
		AA14G09.setDetection_target("포르말린");
		AA14G09.setTime(Read_AA14G09.get(0).time);
		AA14G09.setSs_full_id(Read_AA14G09.get(0).ss_full_id);
		AA14G09.setValue(Read_AA14G09.get(0).value);
		
		obj5dto.setAA14G09(AA14G09);
		
		//27
		
		List<Mv_SensorDTO> Read_AA15G01 = csensorDAO.Read_AA15G01();
		
		AA15G01.setArea_code(Read_AA15G01.get(0).area_code);
		AA15G01.setSensor_name(Read_AA15G01.get(0).sensor_name);
		AA15G01.setSensor_status(Read_AA15G01.get(0).sensor_status);
		AA15G01.setDetection_target("염산");
		AA15G01.setTime(Read_AA15G01.get(0).time);
		AA15G01.setSs_full_id(Read_AA15G01.get(0).ss_full_id);
		AA15G01.setValue(Read_AA15G01.get(0).value);
		
		obj6dto.setAA15G01(AA15G01);
		
		//28
		
		List<Mv_SensorDTO> Read_AA15G02 = csensorDAO.Read_AA15G02();
		
		AA15G02.setArea_code(Read_AA15G02.get(0).area_code);
		AA15G02.setSensor_name(Read_AA15G02.get(0).sensor_name);
		AA15G02.setSensor_status(Read_AA15G02.get(0).sensor_status);
		AA15G02.setDetection_target("에틸렌디아민");
		AA15G02.setTime(Read_AA15G02.get(0).time);
		AA15G02.setSs_full_id(Read_AA15G02.get(0).ss_full_id);
		AA15G02.setValue(Read_AA15G02.get(0).value);
		
		obj6dto.setAA15G02(AA15G02);
		
		//29
		
		List<Mv_SensorDTO> Read_AC01G04 = csensorDAO.Read_AC01G04();
		
		AC01G04.setArea_code(Read_AC01G04.get(0).area_code);
		AC01G04.setSensor_name(Read_AC01G04.get(0).sensor_name);
		AC01G04.setSensor_status(Read_AC01G04.get(0).sensor_status);
		AC01G04.setDetection_target("염산");
		AC01G04.setTime(Read_AC01G04.get(0).time);
		AC01G04.setSs_full_id(Read_AC01G04.get(0).ss_full_id);
		AC01G04.setValue(Read_AC01G04.get(0).value);
		
		obj1dto.setAC01G04(AC01G04);
		
		//30
		
		List<Mv_SensorDTO> Read_AD35G01 = csensorDAO.Read_AD35G01();
		
		AD35G01.setArea_code(Read_AD35G01.get(0).area_code);
		AD35G01.setSensor_name(Read_AD35G01.get(0).sensor_name);
		AD35G01.setSensor_status(Read_AD35G01.get(0).sensor_status);
		AD35G01.setDetection_target("염산");
		AD35G01.setTime(Read_AD35G01.get(0).time);
		AD35G01.setSs_full_id(Read_AD35G01.get(0).ss_full_id);
		AD35G01.setValue(Read_AD35G01.get(0).value);
		
		obj16dto.setAD35G01(AD35G01);
		
		//31
		
		List<Mv_SensorDTO> Read_AD35G02 = csensorDAO.Read_AD35G02();
		
		AD35G02.setArea_code(Read_AD35G02.get(0).area_code);
		AD35G02.setSensor_name(Read_AD35G02.get(0).sensor_name);
		AD35G02.setSensor_status(Read_AD35G02.get(0).sensor_status);
		AD35G02.setDetection_target("암모니아");
		AD35G02.setTime(Read_AD35G02.get(0).time);
		AD35G02.setSs_full_id(Read_AD35G02.get(0).ss_full_id);
		AD35G02.setValue(Read_AD35G02.get(0).value);
		
		obj16dto.setAD35G02(AD35G02);
		
		//32
		
		List<Mv_SensorDTO> Read_AD35G03 = csensorDAO.Read_AD35G03();
		
		AD35G03.setArea_code(Read_AD35G03.get(0).area_code);
		AD35G03.setSensor_name(Read_AD35G03.get(0).sensor_name);
		AD35G03.setSensor_status(Read_AD35G03.get(0).sensor_status);
		AD35G03.setDetection_target("포르말린");
		AD35G03.setTime(Read_AD35G03.get(0).time);
		AD35G03.setSs_full_id(Read_AD35G03.get(0).ss_full_id);
		AD35G03.setValue(Read_AD35G03.get(0).value);
		
		obj16dto.setAD35G03(AD35G03);
		
		//33
		
		List<Mv_SensorDTO> Read_AD35G04 = csensorDAO.Read_AD35G04();
		
		AD35G04.setArea_code(Read_AD35G04.get(0).area_code);
		AD35G04.setSensor_name(Read_AD35G04.get(0).sensor_name);
		AD35G04.setSensor_status(Read_AD35G04.get(0).sensor_status);
		AD35G04.setDetection_target("에틸렌디아민");
		AD35G04.setTime(Read_AD35G04.get(0).time);
		AD35G04.setSs_full_id(Read_AD35G04.get(0).ss_full_id);
		AD35G04.setValue(Read_AD35G04.get(0).value);
		
		obj16dto.setAD35G04(AD35G04);
		
		//34
		
		List<Mv_SensorDTO> Read_AD36G01 = csensorDAO.Read_AD36G01();
		
		AD36G01.setArea_code(Read_AD36G01.get(0).area_code);
		AD36G01.setSensor_name(Read_AD36G01.get(0).sensor_name);
		AD36G01.setSensor_status(Read_AD36G01.get(0).sensor_status);
		AD36G01.setDetection_target("염산");
		AD36G01.setTime(Read_AD36G01.get(0).time);
		AD36G01.setSs_full_id(Read_AD36G01.get(0).ss_full_id);
		AD36G01.setValue(Read_AD36G01.get(0).value);
		
		obj17dto.setAD36G01(AD36G01);
		
		//35
		
		List<Mv_SensorDTO> Read_AD36G02 = csensorDAO.Read_AD36G02();
		
		AD36G02.setArea_code(Read_AD36G02.get(0).area_code);
		AD36G02.setSensor_name(Read_AD36G02.get(0).sensor_name);
		AD36G02.setSensor_status(Read_AD36G02.get(0).sensor_status);
		AD36G02.setDetection_target("암모니아");
		AD36G02.setTime(Read_AD36G02.get(0).time);
		AD36G02.setSs_full_id(Read_AD36G02.get(0).ss_full_id);
		AD36G02.setValue(Read_AD36G02.get(0).value);
		
		obj17dto.setAD36G02(AD36G02);
		
		//36
		
		List<Mv_SensorDTO> Read_AD36G03 = csensorDAO.Read_AD36G03();
		
		AD36G03.setArea_code(Read_AD36G03.get(0).area_code);
		AD36G03.setSensor_name(Read_AD36G03.get(0).sensor_name);
		AD36G03.setSensor_status(Read_AD36G03.get(0).sensor_status);
		AD36G03.setDetection_target("포르말린");
		AD36G03.setTime(Read_AD36G03.get(0).time);
		AD36G03.setSs_full_id(Read_AD36G03.get(0).ss_full_id);
		AD36G03.setValue(Read_AD36G03.get(0).value);
		
		obj17dto.setAD36G03(AD36G03);
		
		//37
		
		List<Mv_SensorDTO> Read_AD36G04 = csensorDAO.Read_AD36G04();
		
		AD36G04.setArea_code(Read_AD36G04.get(0).area_code);
		AD36G04.setSensor_name(Read_AD36G04.get(0).sensor_name);
		AD36G04.setSensor_status(Read_AD36G04.get(0).sensor_status);
		AD36G04.setDetection_target("에틸렌디아민");
		AD36G04.setTime(Read_AD36G04.get(0).time);
		AD36G04.setSs_full_id(Read_AD36G04.get(0).ss_full_id);
		AD36G04.setValue(Read_AD36G04.get(0).value);
		
		obj17dto.setAD36G04(AD36G04);
		
		//38
		
		List<Mv_SensorDTO> Read_AD37G01 = csensorDAO.Read_AD37G01();
		
		AD37G01.setArea_code(Read_AD37G01.get(0).area_code);
		AD37G01.setSensor_name(Read_AD37G01.get(0).sensor_name);
		AD37G01.setSensor_status(Read_AD37G01.get(0).sensor_status);
		AD37G01.setDetection_target("염산");
		AD37G01.setTime(Read_AD37G01.get(0).time);
		AD37G01.setSs_full_id(Read_AD37G01.get(0).ss_full_id);
		AD37G01.setValue(Read_AD37G01.get(0).value);
		
		obj18dto.setAD37G01(AD37G01);
		
		//39
		
		List<Mv_SensorDTO> Read_AD37G02 = csensorDAO.Read_AD37G02();
		
		AD37G02.setArea_code(Read_AD37G02.get(0).area_code);
		AD37G02.setSensor_name(Read_AD37G02.get(0).sensor_name);
		AD37G02.setSensor_status(Read_AD37G02.get(0).sensor_status);
		AD37G02.setDetection_target("암모니아");
		AD37G02.setTime(Read_AD37G02.get(0).time);
		AD37G02.setSs_full_id(Read_AD37G02.get(0).ss_full_id);
		AD37G02.setValue(Read_AD37G02.get(0).value);
		
		obj18dto.setAD37G02(AD37G02);
		
		//40
		
		List<Mv_SensorDTO> Read_AD37G03 = csensorDAO.Read_AD37G03();
		
		AD37G03.setArea_code(Read_AD37G03.get(0).area_code);
		AD37G03.setSensor_name(Read_AD37G03.get(0).sensor_name);
		AD37G03.setSensor_status(Read_AD37G03.get(0).sensor_status);
		AD37G03.setDetection_target("포르말린");
		AD37G03.setTime(Read_AD37G03.get(0).time);
		AD37G03.setSs_full_id(Read_AD37G03.get(0).ss_full_id);
		AD37G03.setValue(Read_AD37G03.get(0).value);
		
		obj18dto.setAD37G03(AD37G03);
		
		//41
		
		List<Mv_SensorDTO> Read_AD37G04 = csensorDAO.Read_AD37G04();
		
		AD37G04.setArea_code(Read_AD37G04.get(0).area_code);
		AD37G04.setSensor_name(Read_AD37G04.get(0).sensor_name);
		AD37G04.setSensor_status(Read_AD37G04.get(0).sensor_status);
		AD37G04.setDetection_target("에틸렌디아민");
		AD37G04.setTime(Read_AD37G04.get(0).time);
		AD37G04.setSs_full_id(Read_AD37G04.get(0).ss_full_id);
		AD37G04.setValue(Read_AD37G04.get(0).value);
		
		obj18dto.setAD37G04(AD37G04);
		
		//42
		
		//List<Mv_SensorDTO> Read_AD37A06 = csensorDAO.Read_AD37A06();
		
		AD37A06.setArea_code("mk_factory_ansan_1");
		AD37A06.setSensor_name("강우-복합기상(#4-37-06)");
		AD37A06.setSensor_status("-9999");
		AD37A06.setDetection_target("강우(외부)");
		AD37A06.setTime(Read_AD37G04.get(0).time);
		AD37A06.setSs_full_id("AD37A06");
		AD37A06.setValue("-9999");
		
		obj18dto.setAD37A06(AD37A06);
		
		//43
		
		//List<Mv_SensorDTO> Read_AD37A05 = csensorDAO.Read_AD37A05();
		
		AD37A05.setArea_code("mk_factory_ansan_1");
		AD37A05.setSensor_name("기압-복합기상(#4-37-05)");
		AD37A05.setSensor_status("-9999");
		AD37A05.setDetection_target("기압(외부)");
		AD37A05.setTime(Read_AD37G04.get(0).time);
		AD37A05.setSs_full_id("AD37A05");
		AD37A05.setValue("-9999");
		
		obj18dto.setAD37A05(AD37A05);
		
		//44
		
		List<Mv_SensorDTO> Read_AA12L01 = csensorDAO.Read_AA12L01();
		
		AA12L01.setArea_code(Read_AA12L01.get(0).area_code);
		AA12L01.setSensor_name(Read_AA12L01.get(0).sensor_name);
		AA12L01.setSensor_status(Read_AA12L01.get(0).sensor_status);
		AA12L01.setDetection_target("누액");
		AA12L01.setTime(Read_AA12L01.get(0).time);
		AA12L01.setSs_full_id(Read_AA12L01.get(0).ss_full_id);
		AA12L01.setValue(Read_AA12L01.get(0).value);
		
		obj4dto.setAA12L01(AA12L01);
		
		//45
		
		List<Mv_SensorDTO> Read_AB23L01 = csensorDAO.Read_AB23L01();
		
		AB23L01.setArea_code(Read_AB23L01.get(0).area_code);
		AB23L01.setSensor_name(Read_AB23L01.get(0).sensor_name);
		AB23L01.setSensor_status(Read_AB23L01.get(0).sensor_status);
		AB23L01.setDetection_target("누액");
		AB23L01.setTime(Read_AB23L01.get(0).time);
		AB23L01.setSs_full_id(Read_AB23L01.get(0).ss_full_id);
		AB23L01.setValue(Read_AB23L01.get(0).value);
		
		obj10dto.setAB23L01(AB23L01);
		
		//46
		
		List<Mv_SensorDTO> Read_AC01L01 = csensorDAO.Read_AC01L01();
		
		AC01L01.setArea_code(Read_AC01L01.get(0).area_code);
		AC01L01.setSensor_name(Read_AC01L01.get(0).sensor_name);
		AC01L01.setSensor_status(Read_AC01L01.get(0).sensor_status);
		AC01L01.setDetection_target("누액");
		AC01L01.setTime(Read_AC01L01.get(0).time);
		AC01L01.setSs_full_id(Read_AC01L01.get(0).ss_full_id);
		AC01L01.setValue(Read_AC01L01.get(0).value);
		
		obj1dto.setAC01L01(AC01L01);
		
		//47
		
		List<Mv_SensorDTO> Read_AA12L02 = csensorDAO.Read_AA12L02();
		
		AA12L02.setArea_code(Read_AA12L02.get(0).area_code);
		AA12L02.setSensor_name(Read_AA12L02.get(0).sensor_name);
		AA12L02.setSensor_status(Read_AA12L02.get(0).sensor_status);
		AA12L02.setDetection_target("누액");
		AA12L02.setTime(Read_AA12L02.get(0).time);
		AA12L02.setSs_full_id(Read_AA12L02.get(0).ss_full_id);
		AA12L02.setValue(Read_AA12L02.get(0).value);
		
		obj4dto.setAA12L02(AA12L02);
		
		//48
		
		List<Mv_SensorDTO> Read_AB23L02 = csensorDAO.Read_AB23L02();
		
		AB23L02.setArea_code(Read_AB23L02.get(0).area_code);
		AB23L02.setSensor_name(Read_AB23L02.get(0).sensor_name);
		AB23L02.setSensor_status(Read_AB23L02.get(0).sensor_status);
		AB23L02.setDetection_target("누액");
		AB23L02.setTime(Read_AB23L02.get(0).time);
		AB23L02.setSs_full_id(Read_AB23L02.get(0).ss_full_id);
		AB23L02.setValue(Read_AB23L02.get(0).value);
		
		obj10dto.setAB23L02(AB23L02);
		
		//49
		
		List<Mv_SensorDTO> Read_AC01L02 = csensorDAO.Read_AC01L02();
		
		AC01L02.setArea_code(Read_AC01L02.get(0).area_code);
		AC01L02.setSensor_name(Read_AC01L02.get(0).sensor_name);
		AC01L02.setSensor_status(Read_AC01L02.get(0).sensor_status);
		AC01L02.setDetection_target("누액");
		AC01L02.setTime(Read_AC01L02.get(0).time);
		AC01L02.setSs_full_id(Read_AC01L02.get(0).ss_full_id);
		AC01L02.setValue(Read_AC01L02.get(0).value);
		
		obj1dto.setAC01L02(AC01L02);
		
		//50
		
		List<Mv_SensorDTO> Read_AA14L03 = csensorDAO.Read_AA14L03();
		
		AA14L03.setArea_code(Read_AA14L03.get(0).area_code);
		AA14L03.setSensor_name(Read_AA14L03.get(0).sensor_name);
		AA14L03.setSensor_status(Read_AA14L03.get(0).sensor_status);
		AA14L03.setDetection_target("누액");
		AA14L03.setTime(Read_AA14L03.get(0).time);
		AA14L03.setSs_full_id(Read_AA14L03.get(0).ss_full_id);
		AA14L03.setValue(Read_AA14L03.get(0).value);
		
		obj5dto.setAA14L03(AA14L03);
		
		//51
		
		List<Mv_SensorDTO> Read_AB23L03 = csensorDAO.Read_AB23L03();
		
		AB23L03.setArea_code(Read_AB23L03.get(0).area_code);
		AB23L03.setSensor_name(Read_AB23L03.get(0).sensor_name);
		AB23L03.setSensor_status(Read_AB23L03.get(0).sensor_status);
		AB23L03.setDetection_target("누액");
		AB23L03.setTime(Read_AB23L03.get(0).time);
		AB23L03.setSs_full_id(Read_AB23L03.get(0).ss_full_id);
		AB23L03.setValue(Read_AB23L03.get(0).value);
		
		obj10dto.setAB23L03(AB23L03);
		
		//52
		
		List<Mv_SensorDTO> Read_AC01L03 = csensorDAO.Read_AC01L03();
		
		AC01L03.setArea_code(Read_AC01L03.get(0).area_code);
		AC01L03.setSensor_name(Read_AC01L03.get(0).sensor_name);
		AC01L03.setSensor_status(Read_AC01L03.get(0).sensor_status);
		AC01L03.setDetection_target("누액");
		AC01L03.setTime(Read_AC01L03.get(0).time);
		AC01L03.setSs_full_id(Read_AC01L03.get(0).ss_full_id);
		AC01L03.setValue(Read_AC01L03.get(0).value);
		
		obj1dto.setAC01L03(AC01L03);
		
		//53
		
		List<Mv_SensorDTO> Read_AA16L04 = csensorDAO.Read_AA16L04();
		
		AA16L04.setArea_code(Read_AA16L04.get(0).area_code);
		AA16L04.setSensor_name(Read_AA16L04.get(0).sensor_name);
		AA16L04.setSensor_status(Read_AA16L04.get(0).sensor_status);
		AA16L04.setDetection_target("누액");
		AA16L04.setTime(Read_AA16L04.get(0).time);
		AA16L04.setSs_full_id(Read_AA16L04.get(0).ss_full_id);
		AA16L04.setValue(Read_AA16L04.get(0).value);
		
		obj7dto.setAA16L04(AA16L04);
		
		//54
		
		List<Mv_SensorDTO> Read_AB23L04 = csensorDAO.Read_AB23L04();
		
		AB23L04.setArea_code(Read_AB23L04.get(0).area_code);
		AB23L04.setSensor_name(Read_AB23L04.get(0).sensor_name);
		AB23L04.setSensor_status(Read_AB23L04.get(0).sensor_status);
		AB23L04.setDetection_target("누액");
		AB23L04.setTime(Read_AB23L04.get(0).time);
		AB23L04.setSs_full_id(Read_AB23L04.get(0).ss_full_id);
		AB23L04.setValue(Read_AB23L04.get(0).value);
		
		obj10dto.setAB23L04(AB23L04);
		
		//55
		
		List<Mv_SensorDTO> Read_AC01L04 = csensorDAO.Read_AC01L04();
		
		AC01L04.setArea_code(Read_AC01L04.get(0).area_code);
		AC01L04.setSensor_name(Read_AC01L04.get(0).sensor_name);
		AC01L04.setSensor_status(Read_AC01L04.get(0).sensor_status);
		AC01L04.setDetection_target("누액");
		AC01L04.setTime(Read_AC01L04.get(0).time);
		AC01L04.setSs_full_id(Read_AC01L04.get(0).ss_full_id);
		AC01L04.setValue(Read_AC01L04.get(0).value);
		
		obj1dto.setAC01L04(AC01L04);
		
		//56
		
		List<Mv_SensorDTO> Read_AA16L05 = csensorDAO.Read_AA16L05();
		
		AA16L05.setArea_code(Read_AA16L05.get(0).area_code);
		AA16L05.setSensor_name(Read_AA16L05.get(0).sensor_name);
		AA16L05.setSensor_status(Read_AA16L05.get(0).sensor_status);
		AA16L05.setDetection_target("누액");
		AA16L05.setTime(Read_AA16L05.get(0).time);
		AA16L05.setSs_full_id(Read_AA16L05.get(0).ss_full_id);
		AA16L05.setValue(Read_AA16L05.get(0).value);
		
		obj7dto.setAA16L05(AA16L05);
		
		//57
		
		List<Mv_SensorDTO> Read_AB23L12 = csensorDAO.Read_AB23L12();
		
		AB23L12.setArea_code(Read_AB23L12.get(0).area_code);
		AB23L12.setSensor_name(Read_AB23L12.get(0).sensor_name);
		AB23L12.setSensor_status(Read_AB23L12.get(0).sensor_status);
		AB23L12.setDetection_target("누액");
		AB23L12.setTime(Read_AB23L12.get(0).time);
		AB23L12.setSs_full_id(Read_AB23L12.get(0).ss_full_id);
		AB23L12.setValue(Read_AB23L12.get(0).value);
		
		obj10dto.setAB23L12(AB23L12);
		
		//58
		
		List<Mv_SensorDTO> Read_AB24L05 = csensorDAO.Read_AB24L05();
		
		AB24L05.setArea_code(Read_AB24L05.get(0).area_code);
		AB24L05.setSensor_name(Read_AB24L05.get(0).sensor_name);
		AB24L05.setSensor_status(Read_AB24L05.get(0).sensor_status);
		AB24L05.setDetection_target("누액");
		AB24L05.setTime(Read_AB24L05.get(0).time);
		AB24L05.setSs_full_id(Read_AB24L05.get(0).ss_full_id);
		AB24L05.setValue(Read_AB24L05.get(0).value);
		
		obj11dto.setAB24L05(AB24L05);
		
		//59
		
		List<Mv_SensorDTO> Read_AA16L06 = csensorDAO.Read_AA16L06();
		
		AA16L06.setArea_code(Read_AA16L06.get(0).area_code);
		AA16L06.setSensor_name(Read_AA16L06.get(0).sensor_name);
		AA16L06.setSensor_status(Read_AA16L06.get(0).sensor_status);
		AA16L06.setDetection_target("누액");
		AA16L06.setTime(Read_AA16L06.get(0).time);
		AA16L06.setSs_full_id(Read_AA16L06.get(0).ss_full_id);
		AA16L06.setValue(Read_AA16L06.get(0).value);
		
		obj7dto.setAA16L06(AA16L06);
		
		//60
		
		List<Mv_SensorDTO> Read_AB23L13 = csensorDAO.Read_AB23L13();
		
		AB23L13.setArea_code(Read_AB23L13.get(0).area_code);
		AB23L13.setSensor_name(Read_AB23L13.get(0).sensor_name);
		AB23L13.setSensor_status(Read_AB23L13.get(0).sensor_status);
		AB23L13.setDetection_target("누액");
		AB23L13.setTime(Read_AB23L13.get(0).time);
		AB23L13.setSs_full_id(Read_AB23L13.get(0).ss_full_id);
		AB23L13.setValue(Read_AB23L13.get(0).value);
		
		obj10dto.setAB23L13(AB23L13);
		
		//61
		
		List<Mv_SensorDTO> Read_AB24L06 = csensorDAO.Read_AB24L06();
		
		AB24L06.setArea_code(Read_AB24L06.get(0).area_code);
		AB24L06.setSensor_name(Read_AB24L06.get(0).sensor_name);
		AB24L06.setSensor_status(Read_AB24L06.get(0).sensor_status);
		AB24L06.setDetection_target("누액");
		AB24L06.setTime(Read_AB24L06.get(0).time);
		AB24L06.setSs_full_id(Read_AB24L06.get(0).ss_full_id);
		AB24L06.setValue(Read_AB24L06.get(0).value);
		
		obj11dto.setAB24L06(AB24L06);
		
		//62
		
		List<Mv_SensorDTO> Read_AA16L07 = csensorDAO.Read_AA16L07();
		
		AA16L07.setArea_code(Read_AA16L07.get(0).area_code);
		AA16L07.setSensor_name(Read_AA16L07.get(0).sensor_name);
		AA16L07.setSensor_status(Read_AA16L07.get(0).sensor_status);
		AA16L07.setDetection_target("누액");
		AA16L07.setTime(Read_AA16L07.get(0).time);
		AA16L07.setSs_full_id(Read_AA16L07.get(0).ss_full_id);
		AA16L07.setValue(Read_AA16L07.get(0).value);
		
		obj7dto.setAA16L07(AA16L07);
		
		//63
		
		List<Mv_SensorDTO> Read_AB24L07 = csensorDAO.Read_AB24L07();
		
		AB24L07.setArea_code(Read_AB24L07.get(0).area_code);
		AB24L07.setSensor_name(Read_AB24L07.get(0).sensor_name);
		AB24L07.setSensor_status(Read_AB24L07.get(0).sensor_status);
		AB24L07.setDetection_target("누액");
		AB24L07.setTime(Read_AB24L07.get(0).time);
		AB24L07.setSs_full_id(Read_AB24L07.get(0).ss_full_id);
		AB24L07.setValue(Read_AB24L07.get(0).value);
		
		obj11dto.setAB24L07(AB24L07);
		
		//64
		
		List<Mv_SensorDTO> Read_AA16L08 = csensorDAO.Read_AA16L08();
		
		AA16L08.setArea_code(Read_AA16L08.get(0).area_code);
		AA16L08.setSensor_name(Read_AA16L08.get(0).sensor_name);
		AA16L08.setSensor_status(Read_AA16L08.get(0).sensor_status);
		AA16L08.setDetection_target("누액");
		AA16L08.setTime(Read_AA16L08.get(0).time);
		AA16L08.setSs_full_id(Read_AA16L08.get(0).ss_full_id);
		AA16L08.setValue(Read_AA16L08.get(0).value);
		
		obj7dto.setAA16L08(AA16L08);
		
		//65
		
		List<Mv_SensorDTO> Read_AB24L08 = csensorDAO.Read_AB24L08();
		
		AB24L08.setArea_code(Read_AB24L08.get(0).area_code);
		AB24L08.setSensor_name(Read_AB24L08.get(0).sensor_name);
		AB24L08.setSensor_status(Read_AB24L08.get(0).sensor_status);
		AB24L08.setDetection_target("누액");
		AB24L08.setTime(Read_AB24L08.get(0).time);
		AB24L08.setSs_full_id(Read_AB24L08.get(0).ss_full_id);
		AB24L08.setValue(Read_AB24L08.get(0).value);
		
		obj11dto.setAB24L08(AB24L08);
		
		//66
		
		List<Mv_SensorDTO> Read_AA16L09 = csensorDAO.Read_AA16L09();
		
		AA16L09.setArea_code(Read_AA16L09.get(0).area_code);
		AA16L09.setSensor_name(Read_AA16L09.get(0).sensor_name);
		AA16L09.setSensor_status(Read_AA16L09.get(0).sensor_status);
		AA16L09.setDetection_target("누액");
		AA16L09.setTime(Read_AA16L09.get(0).time);
		AA16L09.setSs_full_id(Read_AA16L09.get(0).ss_full_id);
		AA16L09.setValue(Read_AA16L09.get(0).value);
		
		obj7dto.setAA16L09(AA16L09);
		
		//67
		
		List<Mv_SensorDTO> Read_AB24L09 = csensorDAO.Read_AB24L09();
		
		AB24L09.setArea_code(Read_AB24L09.get(0).area_code);
		AB24L09.setSensor_name(Read_AB24L09.get(0).sensor_name);
		AB24L09.setSensor_status(Read_AB24L09.get(0).sensor_status);
		AB24L09.setDetection_target("누액");
		AB24L09.setTime(Read_AB24L09.get(0).time);
		AB24L09.setSs_full_id(Read_AB24L09.get(0).ss_full_id);
		AB24L09.setValue(Read_AB24L09.get(0).value);
		
		obj11dto.setAB24L09(AB24L09);
		
		//68
		
		List<Mv_SensorDTO> Read_AA16L10 = csensorDAO.Read_AA16L10();
		
		AA16L10.setArea_code(Read_AA16L10.get(0).area_code);
		AA16L10.setSensor_name(Read_AA16L10.get(0).sensor_name);
		AA16L10.setSensor_status(Read_AA16L10.get(0).sensor_status);
		AA16L10.setDetection_target("누액");
		AA16L10.setTime(Read_AA16L10.get(0).time);
		AA16L10.setSs_full_id(Read_AA16L10.get(0).ss_full_id);
		AA16L10.setValue(Read_AA16L10.get(0).value);
		
		obj7dto.setAA16L10(AA16L10);
		
		//69
		
		List<Mv_SensorDTO> Read_AB31L10 = csensorDAO.Read_AB31L10();
		
		AB31L10.setArea_code(Read_AB31L10.get(0).area_code);
		AB31L10.setSensor_name(Read_AB31L10.get(0).sensor_name);
		AB31L10.setSensor_status(Read_AB31L10.get(0).sensor_status);
		AB31L10.setDetection_target("누액");
		AB31L10.setTime(Read_AB31L10.get(0).time);
		AB31L10.setSs_full_id(Read_AB31L10.get(0).ss_full_id);
		AB31L10.setValue(Read_AB31L10.get(0).value);
		
		obj14dto.setAB31L10(AB31L10);
		
		//70
		
		List<Mv_SensorDTO> Read_AA16L11 = csensorDAO.Read_AA16L11();
		
		AA16L11.setArea_code(Read_AA16L11.get(0).area_code);
		AA16L11.setSensor_name(Read_AA16L11.get(0).sensor_name);
		AA16L11.setSensor_status(Read_AA16L11.get(0).sensor_status);
		AA16L11.setDetection_target("누액");
		AA16L11.setTime(Read_AA16L11.get(0).time);
		AA16L11.setSs_full_id(Read_AA16L11.get(0).ss_full_id);
		AA16L11.setValue(Read_AA16L11.get(0).value);
		
		obj7dto.setAA16L11(AA16L11);
		
		//71
		
		List<Mv_SensorDTO> Read_AB31L11 = csensorDAO.Read_AB31L11();
		
		AB31L11.setArea_code(Read_AB31L11.get(0).area_code);
		AB31L11.setSensor_name(Read_AB31L11.get(0).sensor_name);
		AB31L11.setSensor_status(Read_AB31L11.get(0).sensor_status);
		AB31L11.setDetection_target("누액");
		AB31L11.setTime(Read_AB31L11.get(0).time);
		AB31L11.setSs_full_id(Read_AB31L11.get(0).ss_full_id);
		AB31L11.setValue(Read_AB31L11.get(0).value);
		
		obj14dto.setAB31L11(AB31L11);
		
		//72
		
		List<Mv_SensorDTO> Read_AA16L12 = csensorDAO.Read_AA16L12();
		
		AA16L12.setArea_code(Read_AA16L12.get(0).area_code);
		AA16L12.setSensor_name(Read_AA16L12.get(0).sensor_name);
		AA16L12.setSensor_status(Read_AA16L12.get(0).sensor_status);
		AA16L12.setDetection_target("누액");
		AA16L12.setTime(Read_AA16L12.get(0).time);
		AA16L12.setSs_full_id(Read_AA16L12.get(0).ss_full_id);
		AA16L12.setValue(Read_AA16L12.get(0).value);
		
		obj7dto.setAA16L12(AA16L12);
		
		//73
		
		List<Mv_SensorDTO> Read_AA15L13 = csensorDAO.Read_AA15L13();
		
		AA15L13.setArea_code(Read_AA15L13.get(0).area_code);
		AA15L13.setSensor_name(Read_AA15L13.get(0).sensor_name);
		AA15L13.setSensor_status(Read_AA15L13.get(0).sensor_status);
		AA15L13.setDetection_target("누액");
		AA15L13.setTime(Read_AA15L13.get(0).time);
		AA15L13.setSs_full_id(Read_AA15L13.get(0).ss_full_id);
		AA15L13.setValue(Read_AA15L13.get(0).value);
		
		obj6dto.setAA15L13(AA15L13);
		
		//74
		
		List<Mv_SensorDTO> Read_AA15L14 = csensorDAO.Read_AA15L14();
		
		AA15L14.setArea_code(Read_AA15L14.get(0).area_code);
		AA15L14.setSensor_name(Read_AA15L14.get(0).sensor_name);
		AA15L14.setSensor_status(Read_AA15L14.get(0).sensor_status);
		AA15L14.setDetection_target("누액");
		AA15L14.setTime(Read_AA15L14.get(0).time);
		AA15L14.setSs_full_id(Read_AA15L14.get(0).ss_full_id);
		AA15L14.setValue(Read_AA15L14.get(0).value);
		
		obj6dto.setAA15L14(AA15L14);
		
		//75
		
		List<Mv_SensorDTO> Read_AB27L14 = csensorDAO.Read_AB27L14();
		
		AB27L14.setArea_code(Read_AB27L14.get(0).area_code);
		AB27L14.setSensor_name(Read_AB27L14.get(0).sensor_name);
		AB27L14.setSensor_status(Read_AB27L14.get(0).sensor_status);
		AB27L14.setDetection_target("누액");
		AB27L14.setTime(Read_AB27L14.get(0).time);
		AB27L14.setSs_full_id(Read_AB27L14.get(0).ss_full_id);
		AB27L14.setValue(Read_AB27L14.get(0).value);
		
		obj12dto.setAB27L14(AB27L14);
		
		//76
		
		List<Mv_SensorDTO> Read_AA15L15 = csensorDAO.Read_AA15L15();
		
		AA15L15.setArea_code(Read_AA15L15.get(0).area_code);
		AA15L15.setSensor_name(Read_AA15L15.get(0).sensor_name);
		AA15L15.setSensor_status(Read_AA15L15.get(0).sensor_status);
		AA15L15.setDetection_target("누액");
		AA15L15.setTime(Read_AA15L15.get(0).time);
		AA15L15.setSs_full_id(Read_AA15L15.get(0).ss_full_id);
		AA15L15.setValue(Read_AA15L15.get(0).value);
		
		obj6dto.setAA15L15(AA15L15);
		
		//77
		
		List<Mv_SensorDTO> Read_AB27L15 = csensorDAO.Read_AB27L15();
		
		AB27L15.setArea_code(Read_AB27L15.get(0).area_code);
		AB27L15.setSensor_name(Read_AB27L15.get(0).sensor_name);
		AB27L15.setSensor_status(Read_AB27L15.get(0).sensor_status);
		AB27L15.setDetection_target("누액");
		AB27L15.setTime(Read_AB27L15.get(0).time);
		AB27L15.setSs_full_id(Read_AB27L15.get(0).ss_full_id);
		AB27L15.setValue(Read_AB27L15.get(0).value);
		
		obj12dto.setAB27L15(AB27L15);
		
		//78
		
		List<Mv_SensorDTO> Read_AA15L16 = csensorDAO.Read_AA15L16();
		
		AA15L16.setArea_code(Read_AA15L16.get(0).area_code);
		AA15L16.setSensor_name(Read_AA15L16.get(0).sensor_name);
		AA15L16.setSensor_status(Read_AA15L16.get(0).sensor_status);
		AA15L16.setDetection_target("누액");
		AA15L16.setTime(Read_AA15L16.get(0).time);
		AA15L16.setSs_full_id(Read_AA15L16.get(0).ss_full_id);
		AA15L16.setValue(Read_AA15L16.get(0).value);
		
		obj6dto.setAA15L16(AA15L16);
		
		//79
		
		List<Mv_SensorDTO> Read_AB32L16 = csensorDAO.Read_AB32L16();
		
		AB32L16.setArea_code(Read_AB32L16.get(0).area_code);
		AB32L16.setSensor_name(Read_AB32L16.get(0).sensor_name);
		AB32L16.setSensor_status(Read_AB32L16.get(0).sensor_status);
		AB32L16.setDetection_target("누액");
		AB32L16.setTime(Read_AB32L16.get(0).time);
		AB32L16.setSs_full_id(Read_AB32L16.get(0).ss_full_id);
		AB32L16.setValue(Read_AB32L16.get(0).value);
		
		obj15dto.setAB32L16(AB32L16);
		
		//80
		
		List<Mv_SensorDTO> Read_AA15L17 = csensorDAO.Read_AA15L17();
		
		AA15L17.setArea_code(Read_AA15L17.get(0).area_code);
		AA15L17.setSensor_name(Read_AA15L17.get(0).sensor_name);
		AA15L17.setSensor_status(Read_AA15L17.get(0).sensor_status);
		AA15L17.setDetection_target("누액");
		AA15L17.setTime(Read_AA15L17.get(0).time);
		AA15L17.setSs_full_id(Read_AA15L17.get(0).ss_full_id);
		AA15L17.setValue(Read_AA15L17.get(0).value);
		
		obj6dto.setAA15L17(AA15L17);
		
		//81
		
		List<Mv_SensorDTO> Read_AB32L17 = csensorDAO.Read_AB32L17();
		
		AB32L17.setArea_code(Read_AB32L17.get(0).area_code);
		AB32L17.setSensor_name(Read_AB32L17.get(0).sensor_name);
		AB32L17.setSensor_status(Read_AB32L17.get(0).sensor_status);
		AB32L17.setDetection_target("누액");
		AB32L17.setTime(Read_AB32L17.get(0).time);
		AB32L17.setSs_full_id(Read_AB32L17.get(0).ss_full_id);
		AB32L17.setValue(Read_AB32L17.get(0).value);
		
		obj15dto.setAB32L17(AB32L17);
		
		//82
		
		List<Mv_SensorDTO> Read_AA20L18 = csensorDAO.Read_AA20L18();
		
		AA20L18.setArea_code(Read_AA20L18.get(0).area_code);
		AA20L18.setSensor_name(Read_AA20L18.get(0).sensor_name);
		AA20L18.setSensor_status(Read_AA20L18.get(0).sensor_status);
		AA20L18.setDetection_target("누액");
		AA20L18.setTime(Read_AA20L18.get(0).time);
		AA20L18.setSs_full_id(Read_AA20L18.get(0).ss_full_id);
		AA20L18.setValue(Read_AA20L18.get(0).value);
		
		obj8dto.setAA20L18(AA20L18);
		
		//83
		
		List<Mv_SensorDTO> Read_AB28L18 = csensorDAO.Read_AB28L18();
		
		AB28L18.setArea_code(Read_AB28L18.get(0).area_code);
		AB28L18.setSensor_name(Read_AB28L18.get(0).sensor_name);
		AB28L18.setSensor_status(Read_AB28L18.get(0).sensor_status);
		AB28L18.setDetection_target("누액");
		AB28L18.setTime(Read_AB28L18.get(0).time);
		AB28L18.setSs_full_id(Read_AB28L18.get(0).ss_full_id);
		AB28L18.setValue(Read_AB28L18.get(0).value);
		
		obj13dto.setAB28L18(AB28L18);
		
		//84
		
		List<Mv_SensorDTO> Read_AA14L19 = csensorDAO.Read_AA14L19();
		
		AA14L19.setArea_code(Read_AA14L19.get(0).area_code);
		AA14L19.setSensor_name(Read_AA14L19.get(0).sensor_name);
		AA14L19.setSensor_status(Read_AA14L19.get(0).sensor_status);
		AA14L19.setDetection_target("누액");
		AA14L19.setTime(Read_AA14L19.get(0).time);
		AA14L19.setSs_full_id(Read_AA14L19.get(0).ss_full_id);
		AA14L19.setValue(Read_AA14L19.get(0).value);
		
		obj5dto.setAA14L19(AA14L19);
		
		//85
		
		List<Mv_SensorDTO> Read_AB28L19 = csensorDAO.Read_AB28L19();
		
		AB28L19.setArea_code(Read_AB28L19.get(0).area_code);
		AB28L19.setSensor_name(Read_AB28L19.get(0).sensor_name);
		AB28L19.setSensor_status(Read_AB28L19.get(0).sensor_status);
		AB28L19.setDetection_target("누액");
		AB28L19.setTime(Read_AB28L19.get(0).time);
		AB28L19.setSs_full_id(Read_AB28L19.get(0).ss_full_id);
		AB28L19.setValue(Read_AB28L19.get(0).value);
		
		obj13dto.setAB28L19(AB28L19);
		
		//86
		
		List<Mv_SensorDTO> Read_AA12L20 = csensorDAO.Read_AA12L20();
		
		AA12L20.setArea_code(Read_AA12L20.get(0).area_code);
		AA12L20.setSensor_name(Read_AA12L20.get(0).sensor_name);
		AA12L20.setSensor_status(Read_AA12L20.get(0).sensor_status);
		AA12L20.setDetection_target("누액");
		AA12L20.setTime(Read_AA12L20.get(0).time);
		AA12L20.setSs_full_id(Read_AA12L20.get(0).ss_full_id);
		AA12L20.setValue(Read_AA12L20.get(0).value);
		
		obj4dto.setAA12L20(AA12L20);
		
		//87
		
		List<Mv_SensorDTO> Read_AB28L20 = csensorDAO.Read_AB28L20();
		
		AB28L20.setArea_code(Read_AB28L20.get(0).area_code);
		AB28L20.setSensor_name(Read_AB28L20.get(0).sensor_name);
		AB28L20.setSensor_status(Read_AB28L20.get(0).sensor_status);
		AB28L20.setDetection_target("누액");
		AB28L20.setTime(Read_AB28L20.get(0).time);
		AB28L20.setSs_full_id(Read_AB28L20.get(0).ss_full_id);
		AB28L20.setValue(Read_AB28L20.get(0).value);
		
		obj13dto.setAB28L20(AB28L20);
		
		//88
		
		List<Mv_SensorDTO> Read_AB28L21 = csensorDAO.Read_AB28L21();
		
		AB28L21.setArea_code(Read_AB28L21.get(0).area_code);
		AB28L21.setSensor_name(Read_AB28L21.get(0).sensor_name);
		AB28L21.setSensor_status(Read_AB28L21.get(0).sensor_status);
		AB28L21.setDetection_target("누액");
		AB28L21.setTime(Read_AB28L21.get(0).time);
		AB28L21.setSs_full_id(Read_AB28L21.get(0).ss_full_id);
		AB28L21.setValue(Read_AB28L21.get(0).value);
		
		obj13dto.setAB28L21(AB28L21);
		
		//89
		
		List<Mv_SensorDTO> Read_AB27L22 = csensorDAO.Read_AB27L22();
		
		AB27L22.setArea_code(Read_AB27L22.get(0).area_code);
		AB27L22.setSensor_name(Read_AB27L22.get(0).sensor_name);
		AB27L22.setSensor_status(Read_AB27L22.get(0).sensor_status);
		AB27L22.setDetection_target("누액");
		AB27L22.setTime(Read_AB27L22.get(0).time);
		AB27L22.setSs_full_id(Read_AB27L22.get(0).ss_full_id);
		AB27L22.setValue(Read_AB27L22.get(0).value);
		
		obj12dto.setAB27L22(AB27L22);
		
		//90
		
		List<Mv_SensorDTO> Read_AB27L23 = csensorDAO.Read_AB27L23();
		
		AB27L23.setArea_code(Read_AB27L23.get(0).area_code);
		AB27L23.setSensor_name(Read_AB27L23.get(0).sensor_name);
		AB27L23.setSensor_status(Read_AB27L23.get(0).sensor_status);
		AB27L23.setDetection_target("누액");
		AB27L23.setTime(Read_AB27L23.get(0).time);
		AB27L23.setSs_full_id(Read_AB27L23.get(0).ss_full_id);
		AB27L23.setValue(Read_AB27L23.get(0).value);
		
		obj12dto.setAB27L23(AB27L23);
		
		//91
		
		List<Mv_SensorDTO> Read_AB27L24 = csensorDAO.Read_AB27L24();
		
		AB27L24.setArea_code(Read_AB27L24.get(0).area_code);
		AB27L24.setSensor_name(Read_AB27L24.get(0).sensor_name);
		AB27L24.setSensor_status(Read_AB27L24.get(0).sensor_status);
		AB27L24.setDetection_target("누액");
		AB27L24.setTime(Read_AB27L24.get(0).time);
		AB27L24.setSs_full_id(Read_AB27L24.get(0).ss_full_id);
		AB27L24.setValue(Read_AB27L24.get(0).value);
		
		obj12dto.setAB27L24(AB27L24);
		
		//92
		
		List<Mv_SensorDTO> Read_AB27L25 = csensorDAO.Read_AB27L25();
		
		AB27L25.setArea_code(Read_AB27L25.get(0).area_code);
		AB27L25.setSensor_name(Read_AB27L25.get(0).sensor_name);
		AB27L25.setSensor_status(Read_AB27L25.get(0).sensor_status);
		AB27L25.setDetection_target("누액");
		AB27L25.setTime(Read_AB27L25.get(0).time);
		AB27L25.setSs_full_id(Read_AB27L25.get(0).ss_full_id);
		AB27L25.setValue(Read_AB27L25.get(0).value);
		
		obj12dto.setAB27L25(AB27L25);
		
		//93
		
		List<Mv_SensorDTO> Read_AB22L01 = csensorDAO.Read_AB22L01();
		
		AB22L01.setArea_code(Read_AB22L01.get(0).area_code);
		AB22L01.setSensor_name(Read_AB22L01.get(0).sensor_name);
		AB22L01.setSensor_status(Read_AB22L01.get(0).sensor_status);
		AB22L01.setDetection_target("누액");
		AB22L01.setTime(Read_AB22L01.get(0).time);
		AB22L01.setSs_full_id(Read_AB22L01.get(0).ss_full_id);
		AB22L01.setValue(Read_AB22L01.get(0).value);
		
		obj9dto.setAB22L01(AB22L01);
		
		//94
		
		List<Mv_SensorDTO> Read_AB22L02 = csensorDAO.Read_AB22L02();
		
		AB22L02.setArea_code(Read_AB22L02.get(0).area_code);
		AB22L02.setSensor_name(Read_AB22L02.get(0).sensor_name);
		AB22L02.setSensor_status(Read_AB22L02.get(0).sensor_status);
		AB22L02.setDetection_target("누액");
		AB22L02.setTime(Read_AB22L02.get(0).time);
		AB22L02.setSs_full_id(Read_AB22L02.get(0).ss_full_id);
		AB22L02.setValue(Read_AB22L02.get(0).value);
		
		obj9dto.setAB22L02(AB22L02);
		
		//95
		
		List<Mv_SensorDTO> Read_AB22L03 = csensorDAO.Read_AB22L03();
		
		AB22L03.setArea_code(Read_AB22L03.get(0).area_code);
		AB22L03.setSensor_name(Read_AB22L03.get(0).sensor_name);
		AB22L03.setSensor_status(Read_AB22L03.get(0).sensor_status);
		AB22L03.setDetection_target("누액");
		AB22L03.setTime(Read_AB22L03.get(0).time);
		AB22L03.setSs_full_id(Read_AB22L03.get(0).ss_full_id);
		AB22L03.setValue(Read_AB22L03.get(0).value);
		
		obj9dto.setAB22L03(AB22L03);
		
		//96
		
		List<Mv_SensorDTO> Read_AB22L04 = csensorDAO.Read_AB22L04();
		
		AB22L04.setArea_code(Read_AB22L04.get(0).area_code);
		AB22L04.setSensor_name(Read_AB22L04.get(0).sensor_name);
		AB22L04.setSensor_status(Read_AB22L04.get(0).sensor_status);
		AB22L04.setDetection_target("누액");
		AB22L04.setTime(Read_AB22L04.get(0).time);
		AB22L04.setSs_full_id(Read_AB22L04.get(0).ss_full_id);
		AB22L04.setValue(Read_AB22L04.get(0).value);
		
		obj9dto.setAB22L04(AB22L04);
		
		//97
		
		List<Mv_SensorDTO> Read_AB22L05 = csensorDAO.Read_AB22L05();
		
		AB22L05.setArea_code(Read_AB22L05.get(0).area_code);
		AB22L05.setSensor_name(Read_AB22L05.get(0).sensor_name);
		AB22L05.setSensor_status(Read_AB22L05.get(0).sensor_status);
		AB22L05.setDetection_target("누액");
		AB22L05.setTime(Read_AB22L05.get(0).time);
		AB22L05.setSs_full_id(Read_AB22L05.get(0).ss_full_id);
		AB22L05.setValue(Read_AB22L05.get(0).value);
		
		obj9dto.setAB22L05(AB22L05);
		
		//98
		
		List<Mv_SensorDTO> Read_AB27L21 = csensorDAO.Read_AB27L21();
		
		AB27L21.setArea_code(Read_AB27L21.get(0).area_code);
		AB27L21.setSensor_name(Read_AB27L21.get(0).sensor_name);
		AB27L21.setSensor_status(Read_AB27L21.get(0).sensor_status);
		AB27L21.setDetection_target("누액");
		AB27L21.setTime(Read_AB27L21.get(0).time);
		AB27L21.setSs_full_id(Read_AB27L21.get(0).ss_full_id);
		AB27L21.setValue(Read_AB27L21.get(0).value);
		
		obj12dto.setAB27L21(AB27L21);
		
		//99
		
		List<Mv_SensorDTO> Read_AC08L01 = csensorDAO.Read_AC08L01();
		
		AC08L01.setArea_code(Read_AC08L01.get(0).area_code);
		AC08L01.setSensor_name(Read_AC08L01.get(0).sensor_name);
		AC08L01.setSensor_status(Read_AC08L01.get(0).sensor_status);
		AC08L01.setDetection_target("누액");
		AC08L01.setTime(Read_AC08L01.get(0).time);
		AC08L01.setSs_full_id(Read_AC08L01.get(0).ss_full_id);
		AC08L01.setValue(Read_AC08L01.get(0).value);
		
		obj2dto.setAC08L01(AC08L01);
		
		//100
		
		List<Mv_SensorDTO> Read_AC08L02 = csensorDAO.Read_AC08L02();
		
		AC08L02.setArea_code(Read_AC08L02.get(0).area_code);
		AC08L02.setSensor_name(Read_AC08L02.get(0).sensor_name);
		AC08L02.setSensor_status(Read_AC08L02.get(0).sensor_status);
		AC08L02.setDetection_target("누액");
		AC08L02.setTime(Read_AC08L02.get(0).time);
		AC08L02.setSs_full_id(Read_AC08L02.get(0).ss_full_id);
		AC08L02.setValue(Read_AC08L02.get(0).value);
		
		obj2dto.setAC08L02(AC08L02);
		
		//101
		
		List<Mv_SensorDTO> Read_AA10L01 = csensorDAO.Read_AA10L01();
		
		AA10L01.setArea_code(Read_AA10L01.get(0).area_code);
		AA10L01.setSensor_name(Read_AA10L01.get(0).sensor_name);
		AA10L01.setSensor_status(Read_AA10L01.get(0).sensor_status);
		AA10L01.setDetection_target("방폭누액");
		AA10L01.setTime(Read_AA10L01.get(0).time);
		AA10L01.setSs_full_id(Read_AA10L01.get(0).ss_full_id);
		AA10L01.setValue(Read_AA10L01.get(0).value);
		
		obj3dto.setAA10L01(AA10L01);
		
		//102
		
		List<Mv_SensorDTO> Read_AA10L02 = csensorDAO.Read_AA10L02();
		
		AA10L02.setArea_code(Read_AA10L02.get(0).area_code);
		AA10L02.setSensor_name(Read_AA10L02.get(0).sensor_name);
		AA10L02.setSensor_status(Read_AA10L02.get(0).sensor_status);
		AA10L02.setDetection_target("방폭누액");
		AA10L02.setTime(Read_AA10L02.get(0).time);
		AA10L02.setSs_full_id(Read_AA10L02.get(0).ss_full_id);
		AA10L02.setValue(Read_AA10L02.get(0).value);
		
		obj3dto.setAA10L02(AA10L02);
		
		//103
		
		//List<Mv_SensorDTO> Read_AA10F06 = csensorDAO.Read_AA10F06();
		
		AA10F06.setArea_code("mk_factory_ansan_1");
		AA10F06.setSensor_name("불꽃-W(#1-10-06)");
		AA10F06.setSensor_status("-9999");
		AA10F06.setDetection_target("불꽃");
		AA10F06.setTime(Read_AA10L02.get(0).time);
		AA10F06.setSs_full_id("AA10F06");
		AA10F06.setValue("-9999");
		
		obj3dto.setAA10F06(AA10F06);
		
		//104
		
		//List<Mv_SensorDTO> Read_AD37A02 = csensorDAO.Read_AD37A02();
		
		AD37A02.setArea_code("mk_factory_ansan_1");
		AD37A02.setSensor_name("습도-복합기상(#4-37-02)");
		AD37A02.setSensor_status("-9999");
		AD37A02.setDetection_target("습도(외부)");
		AD37A02.setTime(Read_AA10L02.get(0).time);
		AD37A02.setSs_full_id("AD37A02");
		AD37A02.setValue("-9999");
		
		obj18dto.setAD37A02(AD37A02);
		
		//105
		
		//List<Mv_SensorDTO> Read_AD37A01 = csensorDAO.Read_AD37A01();
		
		AD37A01.setArea_code("mk_factory_ansan_1");
		AD37A01.setSensor_name("온도-복합기상(#4-37-01)");
		AD37A01.setSensor_status("-9999");
		AD37A01.setDetection_target("온도(외부)");
		AD37A01.setTime(Read_AA10L02.get(0).time);
		AD37A01.setSs_full_id("AD37A01");
		AD37A01.setValue("-9999");
		
		obj18dto.setAD37A01(AD37A01);
		
		//106
		
		List<Mv_SensorDTO> Read_AA12P01 = csensorDAO.Read_AA12P01();
		
		AA12P01.setArea_code(Read_AA12P01.get(0).area_code);
		AA12P01.setSensor_name(Read_AA12P01.get(0).sensor_name);
		AA12P01.setSensor_status(Read_AA12P01.get(0).sensor_status);
		AA12P01.setDetection_target("암모니아(외부)");
		AA12P01.setTime(Read_AA12P01.get(0).time);
		AA12P01.setSs_full_id(Read_AA12P01.get(0).ss_full_id);
		AA12P01.setValue(Read_AA12P01.get(0).value);
		
		obj4dto.setAA12P01(AA12P01);
		
		//107
		
		//List<Mv_SensorDTO> Read_AB31P01 = csensorDAO.Read_AB31P01();
		
		AB31P01.setArea_code("mk_factory_ansan_1");
		AB31P01.setSensor_name("외부-01(#2-31-01)");
		AB31P01.setSensor_status("-9999");
		AB31P01.setDetection_target("염산(외부)");
		AB31P01.setTime(Read_AA12P01.get(0).time);
		AB31P01.setSs_full_id("AB31P01");
		AB31P01.setValue("-9999");
		
		obj14dto.setAB31P01(AB31P01);
		
		//108
		
		List<Mv_SensorDTO> Read_AC01G01 = csensorDAO.Read_AC01G01();
		
		AC01G01.setArea_code(Read_AC01G01.get(0).area_code);
		AC01G01.setSensor_name(Read_AC01G01.get(0).sensor_name);
		AC01G01.setSensor_status(Read_AC01G01.get(0).sensor_status);
		AC01G01.setDetection_target("브롬산");
		AC01G01.setTime(Read_AC01G01.get(0).time);
		AC01G01.setSs_full_id(Read_AC01G01.get(0).ss_full_id);
		AC01G01.setValue(Read_AC01G01.get(0).value);
		
		obj1dto.setAC01G01(AC01G01);
		
		//109
		
		List<Mv_SensorDTO> Read_AA12P02 = csensorDAO.Read_AA12P02();
		
		AA12P02.setArea_code(Read_AA12P02.get(0).area_code);
		AA12P02.setSensor_name(Read_AA12P02.get(0).sensor_name);
		AA12P02.setSensor_status(Read_AA12P02.get(0).sensor_status);
		AA12P02.setDetection_target("포르말린(외부)");
		AA12P02.setTime(Read_AA12P02.get(0).time);
		AA12P02.setSs_full_id(Read_AA12P02.get(0).ss_full_id);
		AA12P02.setValue(Read_AA12P02.get(0).value);
		
		obj4dto.setAA12P02(AA12P02);
		
		//110
		
		//List<Mv_SensorDTO> Read_AB27P02 = csensorDAO.Read_AB27P02();
		
		AB27P02.setArea_code("mk_factory_ansan_1");
		AB27P02.setSensor_name("외부-02(#2-27-02)");
		AB27P02.setSensor_status("-9999");
		AB27P02.setDetection_target("인화성(외부)");
		AB27P02.setTime(Read_AA12P02.get(0).time);
		AB27P02.setSs_full_id("AB27P02");
		AB27P02.setValue("-9999");
		
		obj12dto.setAB27P02(AB27P02);
		
		//111
		
		List<Mv_SensorDTO> Read_AC01G02 = csensorDAO.Read_AC01G02();
		
		AC01G02.setArea_code(Read_AC01G02.get(0).area_code);
		AC01G02.setSensor_name(Read_AC01G02.get(0).sensor_name);
		AC01G02.setSensor_status(Read_AC01G02.get(0).sensor_status);
		AC01G02.setDetection_target("염산");
		AC01G02.setTime(Read_AC01G02.get(0).time);
		AC01G02.setSs_full_id(Read_AC01G02.get(0).ss_full_id);
		AC01G02.setValue(Read_AC01G02.get(0).value);
		
		obj1dto.setAC01G02(AC01G02);
		
		//112
		
		List<Mv_SensorDTO> Read_AA10P03 = csensorDAO.Read_AA10P03();
		
		AA10P03.setArea_code(Read_AA10P03.get(0).area_code);
		AA10P03.setSensor_name(Read_AA10P03.get(0).sensor_name);
		AA10P03.setSensor_status(Read_AA10P03.get(0).sensor_status);
		AA10P03.setDetection_target("인화성(외부)");
		AA10P03.setTime(Read_AA10P03.get(0).time);
		AA10P03.setSs_full_id(Read_AA10P03.get(0).ss_full_id);
		AA10P03.setValue(Read_AA10P03.get(0).value);
		
		obj3dto.setAA10P03(AA10P03);
		
		//113
		
		//List<Mv_SensorDTO> Read_AD37A04 = csensorDAO.Read_AD37A04();
		
		AD37A04.setArea_code("mk_factory_ansan_1");
		AD37A04.setSensor_name("풍속-복합기상(#4-37-04)");
		AD37A04.setSensor_status("-9999");
		AD37A04.setDetection_target("풍속(외부)");
		AD37A04.setTime(Read_AA10P03.get(0).time);
		AD37A04.setSs_full_id("AD37A04");
		AD37A04.setValue("-9999");
		
		obj18dto.setAD37A04(AD37A04);
		
		//114
		
		//List<Mv_SensorDTO> Read_AD37A03 = csensorDAO.Read_AD37A03();
		
		AD37A03.setArea_code("mk_factory_ansan_1");
		AD37A03.setSensor_name("풍향-복합기상(#4-37-03)");
		AD37A03.setSensor_status("-9999");
		AD37A03.setDetection_target("풍향(외부)");
		AD37A03.setTime(Read_AA10P03.get(0).time);
		AD37A03.setSs_full_id("AD37A03");
		AD37A03.setValue("-9999");
		
		obj18dto.setAD37A03(AD37A03);
		
		zone1List.setSECTION_10(obj3dto);
		zone1List.setSECTION_12(obj4dto);
		zone1List.setSECTION_14(obj5dto);
		zone1List.setSECTION_15(obj6dto);
		zone1List.setSECTION_16(obj7dto);
		zone1List.setSECTION_20(obj8dto);
		
		zone2List.setSECTION_22(obj9dto);
		zone2List.setSECTION_23(obj10dto);
		zone2List.setSECTION_24(obj11dto);
		zone2List.setSECTION_27(obj12dto);
		zone2List.setSECTION_28(obj13dto);
		zone2List.setSECTION_31(obj14dto);
		zone2List.setSECTION_32(obj15dto);
		
		zone3List.setSECTION_01(obj1dto);
		zone3List.setSECTION_08(obj2dto);
		
		zone4List.setSECTION_35(obj16dto);
		zone4List.setSECTION_36(obj17dto);
		zone4List.setSECTION_37(obj18dto);
		
		zonedto.setZONE_1(zone1List);
		zonedto.setZONE_2(zone2List);
		zonedto.setZONE_3(zone3List);
		zonedto.setZONE_4(zone4List);
		
		message.setResult("success");
		message.setMessage("성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(zonedto);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);	
		
	}

	
	
	public void SensorTest() throws Exception {
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date Sensortime = new Date();

		logger.info(Sensortime.toGMTString());
		
		//센서밸류
		SensorDTO sensordto = new SensorDTO();
		
		Calendar cal1 = Calendar.getInstance();
		
		//Default 시간 + 9
		Calendar cal2 = Calendar.getInstance();
		
		cal2.setTime(Sensortime);
		
		cal1.setTime(Sensortime);
		
		cal2.add(Calendar.HOUR_OF_DAY, 9);
		
		//AWS 시간
		//cal1.add(Calendar.HOUR_OF_DAY , 5);
		
		//로컬용 시간
		cal1.add(Calendar.HOUR_OF_DAY , -5);
		
		String OriginTime = format1.format(new Date(cal2.getTimeInMillis()));
		
		String time2 = format1.format(new Date(cal1.getTimeInMillis()));
		
		logger.info("After Time : " + time2);
		
		logger.info("now Time" + OriginTime);
		
		sensordto.setHour1time(time2);
		
		//현재시간
		sensordto.setHour2time(OriginTime);
		
		List<SensorDTO> AllSensorList = sensorDAO.ValueSensorList(sensordto);
		
		DbSensor_InsertDTO Insertdto = new DbSensor_InsertDTO();
		
		List<SensorDTO> AD37C01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA10C01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37B05 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37B03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37B01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37B02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37B04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12G01 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB28G01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12G02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB28G02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA10G03 = new ArrayList<SensorDTO>();
	
		List<SensorDTO> AA10G04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA14G05 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA14G06 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA14G07 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA14G08 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16G09 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16G10 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16G11 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA16G12 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12G03 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12G05 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA12G06 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12G07 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA14G09 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA15G01 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA15G02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AC01G04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD35G01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD35G02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD35G03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD35G04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD36G01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD36G02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD36G03 = new ArrayList<SensorDTO>();
	
		List<SensorDTO> AD36G04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37G01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37G02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37G03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37G04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37A06 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37A05 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12L01 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB23L01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AC01L01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12L02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB23L02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AC01L02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA14L03 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB23L03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AC01L03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA16L04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB23L04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AC01L04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L05 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB23L12 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB24L05 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L06 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB23L13 = new ArrayList<SensorDTO>();
	
		List<SensorDTO> AB24L06 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L07 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB24L07 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA16L08 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB24L08 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L09 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB24L09 = new ArrayList<SensorDTO>();
		
		//분류 작업
		for(int i = 0; i < AllSensorList.size(); i++) {
			
			if(AllSensorList.get(i).ss_id.equals("AD37C01")) {
				
				AD37C01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10C01")) {
				
				AA10C01.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AD37B05")) {
				
				AD37B05.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37B03")) {
				
				AD37B03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37B01")) {
				
				AD37B01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37B02")) {
				
				AD37B02.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AD37B04")) {
				
				AD37B04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G01")) {
				
				AA12G01.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB28G01")) {
				
				AB28G01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G02")) {
				
				AA12G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB28G02")) {
				
				AB28G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10G03")) {
				
				AA10G03.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AA10G04")) {
				
				AA10G04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14G05")) {
				
				AA14G05.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AA14G06")) {
				
				AA14G06.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14G07")) {
				
				AA14G07.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14G08")) {
				
				AA14G08.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16G09")) {
				
				AA16G09.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AA16G10")) {
				
				AA16G10.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16G11")) {
				
				AA16G11.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AA16G12")) {
				
				AA16G12.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G03")) {
				
				AA12G03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G05")) {
				
				AA12G05.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G06")) {
				
				AA12G06.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12G07")) {
				
				AA12G07.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14G09")) {
				
				AA14G09.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AA15G01")) {
				
				AA15G01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA15G02")) {
				
				AA15G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC01G04")) {
				
				AC01G04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD35G01")) {
				
				AD35G01.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AD35G02")) {
				
				AD35G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD35G03")) {
				
				AD35G03.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AD35G04")) {
				
				AD35G04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD36G01")) {
				
				AD36G01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD36G02")) {
				
				AD36G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD36G03")) {
				
				AD36G03.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AD36G04")) {
				
				AD36G04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37G01")) {
				
				AD37G01.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AD37G02")) {
				
				AD37G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37G03")) {
				
				AD37G03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37G04")) {
				
				AD37G04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37A06")) {
				
				AD37A06.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AD37A05")) {
				
				AD37A05.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12L01")) {
				
				AA12L01.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB23L01")) {
				
				AB23L01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC01L01")) {
				
				AC01L01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12L02")) {
				
				AA12L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB23L02")) {
				
				AB23L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC01L02")) {
				
				AC01L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14L03")) {
				
				AA14L03.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB23L03")) {
				
				AB23L03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC01L03")) {
				
				AC01L03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L04")) {
				
				AA16L04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB23L04")) {
				
				AB23L04.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AC01L04")) {
				
				AC01L04.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L05")) {
				
				AA16L05.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB23L12")) {
				
				AB23L12.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB24L05")) {
				
				AB24L05.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L06")) {
				
				AA16L06.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB23L13")) {
				
				AB23L13.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AB24L06")) {
				
				AB24L06.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L07")) {
				
				AA16L07.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB24L07")) {
				
				AB24L07.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L08")) {
				
				AA16L08.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB24L08")) {
				
				AB24L08.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L09")) {
				
				AA16L09.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AB24L09")) {
				
				AB24L09.add(AllSensorList.get(i));
			}
			
		
	
		}
		
		//null의 경우
		if(AD37C01.size() == 0) {
			
			Insertdto.setSensor_name("CCTV(#4-37-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37C01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37C01.get(AD37C01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(#4-37-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("CCTV(#4-37-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37C01.get(AD37C01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(#4-37-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("CCTV(#4-37-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37C01.get(AD37C01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(#4-37-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("CCTV(#4-37-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37C01.get(AD37C01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(#4-37-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("CCTV(#4-37-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("CCTV(#4-37-01)");
			Insertdto.setSensor_status(AD37C01.get(AD37C01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37C01");
			Insertdto.setTime(AD37C01.get(AD37C01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37C01.get(AD37C01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10C01.size() == 0) {
			
			Insertdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10C01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		//null이 아닌 경우
		else {
			
			if(AA10C01.get(AA10C01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(불꽃감지(ZONE #1)) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("CCTV(불꽃감지(ZONE #1)) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10C01.get(AA10C01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(불꽃감지(ZONE #1)) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("CCTV(불꽃감지(ZONE #1))) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(불꽃감지(ZONE #1)))");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10C01.get(AA10C01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(불꽃감지(ZONE #1)) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("CCTV(불꽃감지(ZONE #1)) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10C01.get(AA10C01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("CCTV(불꽃감지(ZONE #1)) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("CCTV(불꽃감지(ZONE #1)) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
			Insertdto.setSensor_status(AA10C01.get(AA10C01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10C01");
			Insertdto.setTime(AA10C01.get(AA10C01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10C01.get(AA10C01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37B05.size() == 0) {
			
			Insertdto.setSensor_name("H2S-복합대기(#4-37-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37B05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37B05.get(AD37B05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("H2S-복합대기(#4-37-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("H2S-복합대기(#4-37-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("H2S-복합대기(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37B05.get(AD37B05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("H2S-복합대기(#4-37-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("H2S-복합대기(#4-37-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("H2S-복합대기(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B05.get(AD37B05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("H2S-복합대기(#4-37-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("H2S-복합대기(#4-37-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("H2S-복합대기(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B05.get(AD37B05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("H2S-복합대기(#4-37-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("H2S-복합대기(#4-37-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("H2S-복합대기(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("H2S-복합대기(#4-37-05)");
			Insertdto.setSensor_status(AD37B05.get(AD37B05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37B05");
			Insertdto.setTime(AD37B05.get(AD37B05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37B05.get(AD37B05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37B03.size() == 0) {
			
			Insertdto.setSensor_name("NH3-복합대기(#4-37-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37B03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			



			if(AD37B03.get(AD37B03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("NH3-복합대기(#4-37-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("NH3-복합대기(#4-37-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("NH3-복합대기(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37B03.get(AD37B03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("NH3-복합대기(#4-37-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("NH3-복합대기(#4-37-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("NH3-복합대기(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B03.get(AD37B03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("NH3-복합대기(#4-37-03)");
					notificationdto.setContents("NH3-복합대기(#4-37-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("NH3-복합대기(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B03.get(AD37B03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("NH3-복합대기(#4-37-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("NH3-복합대기(#4-37-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("NH3-복합대기(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("NH3-복합대기(#4-37-03)");
			Insertdto.setSensor_status(AD37B03.get(AD37B03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37B03");
			Insertdto.setTime(AD37B03.get(AD37B03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37B03.get(AD37B03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37B01.size() == 0) {
			
			Insertdto.setSensor_name("PM10-복합PM(#4-37-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37B01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			if(AD37B01.get(AD37B01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM10-복합PM(#4-37-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("PM10-복합PM(#4-37-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM10-복합PM(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37B01.get(AD37B01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM10-복합PM(#4-37-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("PM10-복합PM(#4-37-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM10-복합PM(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B01.get(AD37B01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM10-복합PM(#4-37-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("PM10-복합PM(#4-37-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM10-복합PM(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B01.get(AD37B01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM10-복합PM(#4-37-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("PM10-복합PM(#4-37-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM10-복합PM(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("PM10-복합PM(#4-37-01)");
			Insertdto.setSensor_status(AD37B01.get(AD37B01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37B01");
			Insertdto.setTime(AD37B01.get(AD37B01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37B01.get(AD37B01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37B02.size() == 0) {
			
			Insertdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37B02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37B02.get(AD37B02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM2.5-복합PM(#4-37-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("PM2.5-복합PM(#4-37-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37B02.get(AD37B02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM2.5-복합PM(#4-37-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("PM2.5-복합PM(#4-37-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B02.get(AD37B02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM2.5-복합PM(#4-37-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("PM2.5-복합PM(#4-37-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B02.get(AD37B02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("PM2.5-복합PM(#4-37-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("PM2.5-복합PM(#4-37-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("PM2.5-복합PM(#4-37-02)");
			Insertdto.setSensor_status(AD37B02.get(AD37B02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37B02");
			Insertdto.setTime(AD37B02.get(AD37B02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37B02.get(AD37B02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37B04.size() == 0) {
			
			Insertdto.setSensor_name("VOC-복합대기(#4-37-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37B04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37B04.get(AD37B04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("VOC-복합대기(#4-37-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("VOC-복합대기(#4-37-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("VOC-복합대기(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37B04.get(AD37B04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("VOC-복합대기(#4-37-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("VOC-복합대기(#4-37-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("VOC-복합대기(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B04.get(AD37B04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("VOC-복합대기(#4-37-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("VOC-복합대기(#4-37-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("VOC-복합대기(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37B04.get(AD37B04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("VOC-복합대기(#4-37-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("VOC-복합대기(#4-37-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("VOC-복합대기(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("VOC-복합대기(#4-37-04)");
			Insertdto.setSensor_status(AD37B04.get(AD37B04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37B04");
			Insertdto.setTime(AD37B04.get(AD37B04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37B04.get(AD37B04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-01(#1-12-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");

			csensorDAO.RSensorDataInsert(Insertdto);
		}
		//null이 아닌 경우
		else {
			
			if(AA12G01.get(AA12G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#1-12-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-01(#1-12-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G01.get(AA12G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#1-12-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-01(#1-12-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G01.get(AA12G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#1-12-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-01(#1-12-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G01.get(AA12G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#1-12-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-01(#1-12-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-01(#1-12-01)");
			Insertdto.setSensor_status(AA12G01.get(AA12G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G01");
			Insertdto.setTime(AA12G01.get(AA12G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G01.get(AA12G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-01(#2-28-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB28G01.get(AB28G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#2-28-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-01(#2-28-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#2-28-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB28G01.get(AB28G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#2-28-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-01(#2-28-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#2-28-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28G01.get(AB28G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#2-28-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-01(#2-28-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#2-28-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28G01.get(AB28G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-01(#2-28-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-01(#2-28-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-01(#2-28-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-01(#2-28-01)");
			Insertdto.setSensor_status(AB28G01.get(AB28G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28G01");
			Insertdto.setTime(AB28G01.get(AB28G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28G01.get(AB28G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-02(#1-12-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12G02.get(AA12G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#1-12-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-02(#1-12-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G02.get(AA12G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#1-12-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-02(#1-12-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G02.get(AA12G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#1-12-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-02(#1-12-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G02.get(AA12G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#1-12-02) 센서 상태 알림 - CCTV 이상 감지"); 
					notificationdto.setContents("가스-02(#1-12-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-02(#1-12-02)");
			Insertdto.setSensor_status(AA12G02.get(AA12G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G02");
			Insertdto.setTime(AA12G02.get(AA12G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G02.get(AA12G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-02(#2-28-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB28G02.get(AB28G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#2-28-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-02(#2-28-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#2-28-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB28G02.get(AB28G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#2-28-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-02(#2-28-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#2-28-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28G02.get(AB28G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#2-28-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-02(#2-28-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#2-28-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28G02.get(AB28G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-02(#2-28-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-02(#2-28-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-02(#2-28-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-02(#2-28-02)");
			Insertdto.setSensor_status(AB28G02.get(AB28G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28G02");
			Insertdto.setTime(AB28G02.get(AB28G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28G02.get(AB28G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10G03.size() == 0) {
			
			Insertdto.setSensor_name("가스-03(#1-10-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10G03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10G03.get(AA10G03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-03(#1-10-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-03(#1-10-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10G03.get(AA10G03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-03(#1-10-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-03(#1-10-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10G03.get(AA10G03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-03(#1-10-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-03(#1-10-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10G03.get(AA10G03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-03(#1-10-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-03(#1-10-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-03(#1-10-03)");
			Insertdto.setSensor_status(AA10G03.get(AA10G03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10G03");
			Insertdto.setTime(AA10G03.get(AA10G03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10G03.get(AA10G03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10G04.size() == 0) {
			
			Insertdto.setSensor_name("가스-04(#1-10-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10G04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10G04.get(AA10G04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-04(#1-10-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-04(#1-10-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-04(#1-10-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10G04.get(AA10G04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-04(#1-10-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-04(#1-10-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-04(#1-10-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10G04.get(AA10G04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-04(#1-10-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-04(#1-10-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-04(#1-10-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10G04.get(AA10G04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-04(#1-10-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-04(#1-10-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-04(#1-10-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-04(#1-10-04)");
			Insertdto.setSensor_status(AA10G04.get(AA10G04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10G04");
			Insertdto.setTime(AA10G04.get(AA10G04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10G04.get(AA10G04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14G05.size() == 0) {
			
			Insertdto.setSensor_name("가스-05(#1-14-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14G05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14G05.get(AA14G05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-05(#1-14-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-05(#1-14-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-05(#1-14-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14G05.get(AA14G05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-05(#1-14-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-05(#1-14-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-05(#1-14-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G05.get(AA14G05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-05(#1-14-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-05(#1-14-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-05(#1-14-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G05.get(AA14G05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-05(#1-14-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-05(#1-14-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-05(#1-14-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-05(#1-14-05)");
			Insertdto.setSensor_status(AA14G05.get(AA14G05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14G05");
			Insertdto.setTime(AA14G05.get(AA14G05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14G05.get(AA14G05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14G06.size() == 0) {
			
			Insertdto.setSensor_name("가스-06(#1-14-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14G06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14G06.get(AA14G06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-06(#1-14-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-06(#1-14-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-06(#1-14-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14G06.get(AA14G06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-06(#1-14-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-06(#1-14-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-06(#1-14-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G06.get(AA14G06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-06(#1-14-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-06(#1-14-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-06(#1-14-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G06.get(AA14G06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-06(#1-14-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-06(#1-14-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-06(#1-14-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-06(#1-14-06)");
			Insertdto.setSensor_status(AA14G06.get(AA14G06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14G06");
			Insertdto.setTime(AA14G06.get(AA14G06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14G06.get(AA14G06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14G07.size() == 0) {
			
			Insertdto.setSensor_name("가스-07(ZONE #1)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14G07");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14G07.get(AA14G07.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-07(ZONE #1) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-07(ZONE #1) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-07(ZONE #1)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14G07.get(AA14G07.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-07(ZONE #1) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-07(ZONE #1) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-07(ZONE #1)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G07.get(AA14G07.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-07(ZONE #1) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-07(ZONE #1) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-07(ZONE #1)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G07.get(AA14G07.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-07(ZONE #1) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-07(ZONE #1) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-07(ZONE #1)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-07(ZONE #1)");
			Insertdto.setSensor_status(AA14G07.get(AA14G07.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14G07");
			Insertdto.setTime(AA14G07.get(AA14G07.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14G07.get(AA14G07.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14G08.size() == 0) {
			
			Insertdto.setSensor_name("가스-08(#1-14-08)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14G08");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14G08.get(AA14G08.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-08(#1-14-08) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-08(#1-14-08) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-08(#1-14-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14G08.get(AA14G08.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-08(#1-14-08) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-08(#1-14-08) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("가스-08(#1-14-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G08.get(AA14G08.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-08(#1-14-08) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-08(#1-14-08) 센서 상태가 경고3단계 입니다."
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-08(#1-14-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G08.get(AA14G08.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-08(#1-14-08) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-08(#1-14-08) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-08(#1-14-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-08(#1-14-08)");
			Insertdto.setSensor_status(AA14G08.get(AA14G08.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14G08");
			Insertdto.setTime(AA14G08.get(AA14G08.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14G08.get(AA14G08.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16G09.size() == 0) {
			
			Insertdto.setSensor_name("가스-09(#1-16-09)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16G09");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16G09.get(AA16G09.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-09(#1-16-09) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-09(#1-16-09) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16G09.get(AA16G09.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-09(#1-16-09) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-09(#1-16-09) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G09.get(AA16G09.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-09(#1-16-09) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-09(#1-16-09) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G09.get(AA16G09.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-09(#1-16-09) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-09(#1-16-09) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-09(#1-16-09)");
			Insertdto.setSensor_status(AA16G09.get(AA16G09.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16G09");
			Insertdto.setTime(AA16G09.get(AA16G09.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16G09.get(AA16G09.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16G10.size() == 0) {
			
			Insertdto.setSensor_name("가스-10(#1-16-10)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16G10");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16G10.get(AA16G10.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-10(#1-16-10) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-10(#1-16-10) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16G10.get(AA16G10.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-10(#1-16-10) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-10(#1-16-10) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G10.get(AA16G10.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-10(#1-16-10) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-10(#1-16-10) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G10.get(AA16G10.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-10(#1-16-10) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-10(#1-16-10)) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-10(#1-16-10)");
			Insertdto.setSensor_status(AA16G10.get(AA16G10.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16G10");
			Insertdto.setTime(AA16G10.get(AA16G10.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16G10.get(AA16G10.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16G11.size() == 0) {
			
			Insertdto.setSensor_name("가스-11(#1-16-11)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16G11");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16G11.get(AA16G11.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-11(#1-16-11) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-11(#1-16-11) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16G11.get(AA16G11.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-11(#1-16-11) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-11(#1-16-11) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G11.get(AA16G11.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-11(#1-16-11) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-11(#1-16-11) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G11.get(AA16G11.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-11(#1-16-11) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-11(#1-16-11) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-11(#1-16-11)");
			Insertdto.setSensor_status(AA16G11.get(AA16G11.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16G11");
			Insertdto.setTime(AA16G11.get(AA16G11.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16G11.get(AA16G11.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16G12.size() == 0) {
			
			Insertdto.setSensor_name("가스-12(#1-16-12)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16G12");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16G12.get(AA16G12.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-12(#1-16-12) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-12(#1-16-12) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16G12.get(AA16G12.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-12(#1-16-12) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-12(#1-16-12) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G12.get(AA16G12.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-12(#1-16-12) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-12(#1-16-12) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16G12.get(AA16G12.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-12(#1-16-12) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-12(#1-16-12) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-12(#1-16-12)");
			Insertdto.setSensor_status(AA16G12.get(AA16G12.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16G12");
			Insertdto.setTime(AA16G12.get(AA16G12.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16G12.get(AA16G12.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G03.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-12-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12G03.get(AA12G03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-12-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G03.get(AA12G03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-12-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G03.get(AA12G03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-12-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G03.get(AA12G03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-12-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-12-03)");
			Insertdto.setSensor_status(AA12G03.get(AA12G03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G03");
			Insertdto.setTime(AA12G03.get(AA12G03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G03.get(AA12G03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G05.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-12-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12G05.get(AA12G05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-12-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G05.get(AA12G05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-12-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G05.get(AA12G05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-12-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G05.get(AA12G05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-12-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-12-05)");
			Insertdto.setSensor_status(AA12G05.get(AA12G05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G05");
			Insertdto.setTime(AA12G05.get(AA12G05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G05.get(AA12G05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G06.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-12-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12G06.get(AA12G06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-12-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G06.get(AA12G06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-12-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G06.get(AA12G06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-12-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G06.get(AA12G06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-12-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-12-06)");
			Insertdto.setSensor_status(AA12G06.get(AA12G06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G06");
			Insertdto.setTime(AA12G06.get(AA12G06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G06.get(AA12G06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12G07.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-12-07)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12G07");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12G07.get(AA12G07.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-07) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-12-07) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12G07.get(AA12G07.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-07) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-12-07) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G07.get(AA12G07.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-07) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-12-07) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12G07.get(AA12G07.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-12-07) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-12-07) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-12-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-12-07)");
			Insertdto.setSensor_status(AA12G07.get(AA12G07.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12G07");
			Insertdto.setTime(AA12G07.get(AA12G07.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12G07.get(AA12G07.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14G09.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-14-09)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14G09");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14G09.get(AA14G09.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-14-09) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-14-09) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-14-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14G09.get(AA14G09.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-14-09) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-14-09) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-14-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G09.get(AA14G09.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-14-09) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-14-09) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("가스-W(#1-14-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14G09.get(AA14G09.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-14-09) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-14-09) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-14-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-14-09)");
			Insertdto.setSensor_status(AA14G09.get(AA14G09.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14G09");
			Insertdto.setTime(AA14G09.get(AA14G09.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14G09.get(AA14G09.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-15-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15G01.get(AA15G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-01) 센서 상태 알림 - 경고1단계"); 
					notificationdto.setContents("가스-W(#1-15-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15G01.get(AA15G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-15-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15G01.get(AA15G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#1-15-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15G01.get(AA15G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-15-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-15-01)");
			Insertdto.setSensor_status(AA15G01.get(AA15G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15G01");
			Insertdto.setTime(AA15G01.get(AA15G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15G01.get(AA15G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#1-15-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15G02.get(AA15G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#1-15-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15G02.get(AA15G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#1-15-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15G02.get(AA15G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-02) 센서 상태 알림 - 경고3단계"); 
					notificationdto.setContents("가스-W(#1-15-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15G02.get(AA15G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#1-15-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#1-15-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#1-15-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#1-15-02)");
			Insertdto.setSensor_status(AA15G02.get(AA15G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15G02");
			Insertdto.setTime(AA15G02.get(AA15G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15G02.get(AA15G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01G04.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#3-01-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01G04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01G04.get(AC01G04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#3-01-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#3-01-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01G04.get(AC01G04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#3-01-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#3-01-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G04.get(AC01G04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#3-01-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#3-01-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G04.get(AC01G04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#3-01-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#3-01-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#3-01-04)");
			Insertdto.setSensor_status(AC01G04.get(AC01G04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01G04");
			Insertdto.setTime(AC01G04.get(AC01G04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01G04.get(AC01G04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD35G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-35-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD35G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD35G01.get(AD35G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-35-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD35G01.get(AD35G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-35-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G01.get(AD35G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-35-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G01.get(AD35G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-35-01)) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-35-01)");
			Insertdto.setSensor_status(AD35G01.get(AD35G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD35G01");
			Insertdto.setTime(AD35G01.get(AD35G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD35G01.get(AD35G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD35G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-35-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD35G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD35G02.get(AD35G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-35-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD35G02.get(AD35G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-35-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G02.get(AD35G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-35-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G02.get(AD35G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-35-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-35-02)");
			Insertdto.setSensor_status(AD35G02.get(AD35G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD35G02");
			Insertdto.setTime(AD35G02.get(AD35G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD35G02.get(AD35G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD35G03.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-35-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD35G03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD35G03.get(AD35G03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-35-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD35G03.get(AD35G03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-03) 센서 상태 알림 - 경고2단계"); 
					notificationdto.setContents("가스-W(#4-35-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator") 
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G03.get(AD35G03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-35-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G03.get(AD35G03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-35-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-35-03)");
			Insertdto.setSensor_status(AD35G03.get(AD35G03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD35G03");
			Insertdto.setTime(AD35G03.get(AD35G03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD35G03.get(AD35G03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD35G04.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-35-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD35G04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD35G04.get(AD35G04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-35-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD35G04.get(AD35G04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-35-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G04.get(AD35G04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-35-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD35G04.get(AD35G04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-35-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-35-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-35-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-35-04)");
			Insertdto.setSensor_status(AD35G04.get(AD35G04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD35G04");
			Insertdto.setTime(AD35G04.get(AD35G04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD35G04.get(AD35G04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD36G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-36-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD36G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD36G01.get(AD36G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-36-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("가스-W(#4-36-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD36G01.get(AD36G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-01)) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-36-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G01.get(AD36G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-36-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G01.get(AD36G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-36-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-36-01)");
			Insertdto.setSensor_status(AD36G01.get(AD36G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD36G01");
			Insertdto.setTime(AD36G01.get(AD36G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD36G01.get(AD36G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD36G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-36-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD36G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD36G02.get(AD36G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-36-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD36G02.get(AD36G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-36-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("가스-W(#4-36-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G02.get(AD36G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-36-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G02.get(AD36G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-36-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-36-02)");
			Insertdto.setSensor_status(AD36G02.get(AD36G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD36G02");
			Insertdto.setTime(AD36G02.get(AD36G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD36G02.get(AD36G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD36G03.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-36-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD36G03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD36G03.get(AD36G03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-36-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD36G03.get(AD36G03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-36-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G03.get(AD36G03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-36-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G03.get(AD36G03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-36-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-36-03)");
			Insertdto.setSensor_status(AD36G03.get(AD36G03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD36G03");
			Insertdto.setTime(AD36G03.get(AD36G03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD36G03.get(AD36G03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD36G04.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-36-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD36G04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD36G04.get(AD36G04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-36-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD36G04.get(AD36G04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-36-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G04.get(AD36G04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-36-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD36G04.get(AD36G04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-36-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-36-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-36-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-36-04)");
			Insertdto.setSensor_status(AD36G04.get(AD36G04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD36G04");
			Insertdto.setTime(AD36G04.get(AD36G04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD36G04.get(AD36G04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37G01.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-37-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {

			if(AD37G01.get(AD37G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-37-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37G01.get(AD37G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-37-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G01.get(AD37G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-37-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G01.get(AD37G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-37-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-37-01)");
			Insertdto.setSensor_status(AD37G01.get(AD37G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37G01");
			Insertdto.setTime(AD37G01.get(AD37G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37G01.get(AD37G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37G02.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-37-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37G02.get(AD37G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-37-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37G02.get(AD37G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-37-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G02.get(AD37G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-37-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G02.get(AD37G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-37-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-37-02)");
			Insertdto.setSensor_status(AD37G02.get(AD37G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37G02");
			Insertdto.setTime(AD37G02.get(AD37G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37G02.get(AD37G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37G03.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-37-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37G03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37G03.get(AD37G03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-37-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37G03.get(AD37G03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-37-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G03.get(AD37G03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-37-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G03.get(AD37G03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-37-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-37-03)");
			Insertdto.setSensor_status(AD37G03.get(AD37G03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37G03");
			Insertdto.setTime(AD37G03.get(AD37G03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37G03.get(AD37G03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37G04.size() == 0) {
			
			Insertdto.setSensor_name("가스-W(#4-37-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37G04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37G04.get(AD37G04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("가스-W(#4-37-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37G04.get(AD37G04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("가스-W(#4-37-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G04.get(AD37G04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("가스-W(#4-37-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37G04.get(AD37G04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("가스-W(#4-37-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("가스-W(#4-37-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("가스-W(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("가스-W(#4-37-04)");
			Insertdto.setSensor_status(AD37G04.get(AD37G04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37G04");
			Insertdto.setTime(AD37G04.get(AD37G04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37G04.get(AD37G04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A06.size() == 0) {
			
			Insertdto.setSensor_name("강우-복합기상(#4-37-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A06.get(AD37A06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("강우-복합기상(#4-37-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("강우-복합기상(#4-37-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("강우-복합기상(#4-37-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A06.get(AD37A06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("강우-복합기상(#4-37-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("강우-복합기상(#4-37-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("강우-복합기상(#4-37-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A06.get(AD37A06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("강우-복합기상(#4-37-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("강우-복합기상(#4-37-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("강우-복합기상(#4-37-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A06.get(AD37A06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("강우-복합기상(#4-37-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("강우-복합기상(#4-37-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("강우-복합기상(#4-37-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("강우-복합기상(#4-37-06)");
			Insertdto.setSensor_status(AD37A06.get(AD37A06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A06");
			Insertdto.setTime(AD37A06.get(AD37A06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A06.get(AD37A06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A05.size() == 0) {
			
			Insertdto.setSensor_name("기압-복합기상(#4-37-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A05.get(AD37A05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("기압-복합기상(#4-37-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("기압-복합기상(#4-37-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("기압-복합기상(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A05.get(AD37A05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("기압-복합기상(#4-37-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("기압-복합기상(#4-37-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("기압-복합기상(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A05.get(AD37A05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("기압-복합기상(#4-37-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("기압-복합기상(#4-37-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("기압-복합기상(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A05.get(AD37A05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("기압-복합기상(#4-37-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("기압-복합기상(#4-37-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("기압-복합기상(#4-37-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("기압-복합기상(#4-37-05)");
			Insertdto.setSensor_status(AD37A05.get(AD37A05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A05");
			Insertdto.setTime(AD37A05.get(AD37A05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A05.get(AD37A05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12L01.size() == 0) {
			
			Insertdto.setSensor_name("누액-01(#1-12-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12L01.get(AA12L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#1-12-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-01(#1-12-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12L01.get(AA12L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#1-12-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-01(#1-12-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L01.get(AA12L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#1-12-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-01(#1-12-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L01.get(AA12L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#1-12-01) 센서 상태 알림 - CCTV 이상 감지"); 
					notificationdto.setContents("누액-01(#1-12-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-01(#1-12-01)");
			Insertdto.setSensor_status(AA12L01.get(AA12L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12L01");
			Insertdto.setTime(AA12L01.get(AA12L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12L01.get(AA12L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L01.size() == 0) {
			
			Insertdto.setSensor_name("누액-01(#2-23-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L01.get(AB23L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#2-23-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-01(#2-23-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#2-23-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L01.get(AB23L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#2-23-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-01(#2-23-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#2-23-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L01.get(AB23L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#2-23-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-01(#2-23-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#2-23-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L01.get(AB23L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#2-23-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-01(#2-23-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#2-23-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-01(#2-23-01)");
			Insertdto.setSensor_status(AB23L01.get(AB23L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L01");
			Insertdto.setTime(AB23L01.get(AB23L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L01.get(AB23L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01L01.size() == 0) {
			
			Insertdto.setSensor_name("누액-01(#3-01-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01L01.get(AC01L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#3-01-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-01(#3-01-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01L01.get(AC01L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#3-01-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-01(#3-01-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L01.get(AC01L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#3-01-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-01(#3-01-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L01.get(AC01L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-01(#3-01-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-01(#3-01-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-01(#3-01-01)");
			Insertdto.setSensor_status(AC01L01.get(AC01L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01L01");
			Insertdto.setTime(AC01L01.get(AC01L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01L01.get(AC01L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12L02.size() == 0) {
			
			Insertdto.setSensor_name("누액-02(#1-12-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12L02.get(AA12L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#1-12-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-02(#1-12-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12L02.get(AA12L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#1-12-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-02(#1-12-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L02.get(AA12L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#1-12-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-02(#1-12-02)) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L02.get(AA12L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#1-12-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-02(#1-12-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-02(#1-12-02)");
			Insertdto.setSensor_status(AA12L02.get(AA12L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12L02");
			Insertdto.setTime(AA12L02.get(AA12L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12L02.get(AA12L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L02.size() == 0) {
			
			Insertdto.setSensor_name("누액-02(#2-23-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L02.get(AB23L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#2-23-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-02(#2-23-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#2-23-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L02.get(AB23L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#2-23-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-02(#2-23-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#2-23-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L02.get(AB23L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#2-23-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-02(#2-23-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#2-23-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L02.get(AB23L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#2-23-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-02(#2-23-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#2-23-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-02(#2-23-02)");
			Insertdto.setSensor_status(AB23L02.get(AB23L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L02");
			Insertdto.setTime(AB23L02.get(AB23L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L02.get(AB23L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01L02.size() == 0) {
			
			Insertdto.setSensor_name("누액-02(#3-01-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01L02.get(AC01L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#3-01-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-02(#3-01-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01L02.get(AC01L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#3-01-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-02(#3-01-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L02.get(AC01L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#3-01-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-02(#3-01-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L02.get(AC01L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-02(#3-01-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-02(#3-01-02) 센서 상태가 CCTV 이상 감지 입니다."+ System.getProperty("line.separator") 
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-02(#3-01-02)");
			Insertdto.setSensor_status(AC01L02.get(AC01L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01L02");
			Insertdto.setTime(AC01L02.get(AC01L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01L02.get(AC01L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14L03.size() == 0) {
			
			Insertdto.setSensor_name("누액-03(#1-14-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14L03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14L03.get(AA14L03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#1-14-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-03(#1-14-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#1-14-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14L03.get(AA14L03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#1-14-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-03(#1-14-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#1-14-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14L03.get(AA14L03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#1-14-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-03(#1-14-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#1-14-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14L03.get(AA14L03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#1-14-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-03(#1-14-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#1-14-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-03(#1-14-03)");
			Insertdto.setSensor_status(AA14L03.get(AA14L03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14L03");
			Insertdto.setTime(AA14L03.get(AA14L03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14L03.get(AA14L03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L03.size() == 0) {
			
			Insertdto.setSensor_name("누액-03(#2-23-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L03.get(AB23L03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#2-23-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-03(#2-23-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#2-23-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L03.get(AB23L03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#2-23-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-03(#2-23-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#2-23-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L03.get(AB23L03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#2-23-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-03(#2-23-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#2-23-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L03.get(AB23L03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#2-23-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-03(#2-23-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#2-23-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-03(#2-23-03)");
			Insertdto.setSensor_status(AB23L03.get(AB23L03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L03");
			Insertdto.setTime(AB23L03.get(AB23L03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L03.get(AB23L03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01L03.size() == 0) {
			
			Insertdto.setSensor_name("누액-03(#3-01-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01L03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01L03.get(AC01L03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#3-01-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-03(#3-01-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#3-01-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01L03.get(AC01L03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#3-01-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-03(#3-01-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#3-01-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L03.get(AC01L03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#3-01-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-03(#3-01-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#3-01-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L03.get(AC01L03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-03(#3-01-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-03(#3-01-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-03(#3-01-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-03(#3-01-03)");
			Insertdto.setSensor_status(AC01L03.get(AC01L03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01L03");
			Insertdto.setTime(AC01L03.get(AC01L03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01L03.get(AC01L03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L04.size() == 0) {
			
			Insertdto.setSensor_name("누액-04(#1-16-14)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L04.get(AA16L04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#1-16-14) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-04(#1-16-14) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#1-16-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L04.get(AA16L04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#1-16-14) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-04(#1-16-14) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#1-16-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L04.get(AA16L04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#1-16-14) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-04(#1-16-14) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#1-16-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L04.get(AA16L04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#1-16-14) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-04(#1-16-14) 센서 상태가 CCTV 이상 감지 입니다."
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#1-16-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-04(#1-16-14)");
			Insertdto.setSensor_status(AA16L04.get(AA16L04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L04");
			Insertdto.setTime(AA16L04.get(AA16L04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L04.get(AA16L04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L04.size() == 0) {
			
			Insertdto.setSensor_name("누액-04(#2-23-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L04.get(AB23L04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#2-23-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-04(#2-23-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#2-23-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L04.get(AB23L04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#2-23-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-04(#2-23-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#2-23-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L04.get(AB23L04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#2-23-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-04(#2-23-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#2-23-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L04.get(AB23L04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#2-23-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-04(#2-23-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#2-23-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-04(#2-23-04)");
			Insertdto.setSensor_status(AB23L04.get(AB23L04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L04");
			Insertdto.setTime(AB23L04.get(AB23L04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L04.get(AB23L04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01L04.size() == 0) {
			
			Insertdto.setSensor_name("누액-04(#3-01-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01L04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01L04.get(AC01L04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#3-01-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-04(#3-01-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01L04.get(AC01L04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#3-01-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-04(#3-01-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L04.get(AC01L04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#3-01-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-04(#3-01-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01L04.get(AC01L04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-04(#3-01-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-04(#3-01-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-04(#3-01-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-04(#3-01-04)");
			Insertdto.setSensor_status(AC01L04.get(AC01L04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01L04");
			Insertdto.setTime(AC01L04.get(AC01L04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01L04.get(AC01L04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L05.size() == 0) {
			
			Insertdto.setSensor_name("누액-05(#1-16-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L05.get(AA16L05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#1-16-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-05(#1-16-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#1-16-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L05.get(AA16L05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#1-16-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-05(#1-16-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#1-16-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L05.get(AA16L05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#1-16-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-05(#1-16-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#1-16-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L05.get(AA16L05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#1-16-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-05(#1-16-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#1-16-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-05(#1-16-05)");
			Insertdto.setSensor_status(AA16L05.get(AA16L05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L05");
			Insertdto.setTime(AA16L05.get(AA16L05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L05.get(AA16L05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L12.size() == 0) {
			
			Insertdto.setSensor_name("누액-05(#2-23-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L12");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L12.get(AB23L12.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-23-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-05(#2-23-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-23-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L12.get(AB23L12.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-23-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-05(#2-23-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-23-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L12.get(AB23L12.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-23-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-05(#2-23-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-23-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L12.get(AB23L12.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-23-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-05(#2-23-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-23-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-05(#2-23-05)");
			Insertdto.setSensor_status(AB23L12.get(AB23L12.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L12");
			Insertdto.setTime(AB23L12.get(AB23L12.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L12.get(AB23L12.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB24L05.size() == 0) {
			
			Insertdto.setSensor_name("누액-05(#2-24-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB24L05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB24L05.get(AB24L05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-24-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-05(#2-24-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-24-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB24L05.get(AB24L05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-24-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-05(#2-24-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-05(#2-24-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L05.get(AB24L05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-24-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-05(#2-24-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-05(#2-24-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L05.get(AB24L05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-05(#2-24-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-05(#2-24-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-05(#2-24-05)");
			Insertdto.setSensor_status(AB24L05.get(AB24L05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB24L05");
			Insertdto.setTime(AB24L05.get(AB24L05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB24L05.get(AB24L05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L06.size() == 0) {
			
			Insertdto.setSensor_name("누액-06(#1-16-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L06.get(AA16L06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#1-16-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-06(#1-16-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#1-16-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L06.get(AA16L06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#1-16-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-06(#1-16-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#1-16-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L06.get(AA16L06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#1-16-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-06(#1-16-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#1-16-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L06.get(AA16L06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#1-16-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-06(#1-16-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#1-16-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-06(#1-16-06)");
			Insertdto.setSensor_status(AA16L06.get(AA16L06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L06");
			Insertdto.setTime(AA16L06.get(AA16L06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L06.get(AA16L06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB23L13.size() == 0) {
			
			Insertdto.setSensor_name("누액-06(#2-23-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB23L13");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB23L13.get(AB23L13.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-23-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-06(#2-23-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-23-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB23L13.get(AB23L13.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-23-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-06(#2-23-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-23-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L13.get(AB23L13.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-23-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-06(#2-23-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-06(#2-23-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB23L13.get(AB23L13.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-23-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-06(#2-23-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-23-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-06(#2-23-06)");
			Insertdto.setSensor_status(AB23L13.get(AB23L13.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB23L13");
			Insertdto.setTime(AB23L13.get(AB23L13.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB23L13.get(AB23L13.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB24L06.size() == 0) {
			
			Insertdto.setSensor_name("누액-06(#2-24-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB24L06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB24L06.get(AB24L06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-24-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-06(#2-24-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-24-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB24L06.get(AB24L06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-24-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-06(#2-24-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-24-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L06.get(AB24L06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-24-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-06(#2-24-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-24-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L06.get(AB24L06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-06(#2-24-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-06(#2-24-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-06(#2-24-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-06(#2-24-06)");
			Insertdto.setSensor_status(AB24L06.get(AB24L06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB24L06");
			Insertdto.setTime(AB24L06.get(AB24L06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB24L06.get(AB24L06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L07.size() == 0) {
			
			Insertdto.setSensor_name("누액-07(#1-16-07)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L07");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L07.get(AA16L07.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#1-16-07) 센서 상태 알림 - 경고1단계"); 
					notificationdto.setContents("누액-07(#1-16-07) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#1-16-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L07.get(AA16L07.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#1-16-07) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-07(#1-16-07) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#1-16-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L07.get(AA16L07.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#1-16-07) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-07(#1-16-07) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#1-16-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L07.get(AA16L07.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#1-16-07) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-07(#1-16-07) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#1-16-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-07(#1-16-07)");
			Insertdto.setSensor_status(AA16L07.get(AA16L07.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L07");
			Insertdto.setTime(AA16L07.get(AA16L07.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L07.get(AA16L07.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB24L07.size() == 0) {
			
			Insertdto.setSensor_name("누액-07(#2-24-07)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB24L07");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB24L07.get(AB24L07.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#2-24-07) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-07(#2-24-07) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#2-24-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB24L07.get(AB24L07.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#2-24-07) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-07(#2-24-07) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#2-24-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L07.get(AB24L07.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#2-24-07) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-07(#2-24-07) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#2-24-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L07.get(AB24L07.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-07(#2-24-07) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-07(#2-24-07) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-07(#2-24-07)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-07(#2-24-07)");
			Insertdto.setSensor_status(AB24L07.get(AB24L07.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB24L07");
			Insertdto.setTime(AB24L07.get(AB24L07.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB24L07.get(AB24L07.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L08.size() == 0) {
			
			Insertdto.setSensor_name("누액-08(#1-16-08)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L08");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L08.get(AA16L08.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#1-16-08) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-08(#1-16-08) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#1-16-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L08.get(AA16L08.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#1-16-08) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-08(#1-16-08) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#1-16-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L08.get(AA16L08.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#1-16-08) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-08(#1-16-08) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-08(#1-16-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L08.get(AA16L08.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#1-16-08) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-08(#1-16-08) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#1-16-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-08(#1-16-08)");
			Insertdto.setSensor_status(AA16L08.get(AA16L08.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L08");
			Insertdto.setTime(AA16L08.get(AA16L08.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L08.get(AA16L08.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB24L08.size() == 0) {
			
			Insertdto.setSensor_name("누액-08(#2-24-08)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB24L08");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB24L08.get(AB24L08.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#2-24-08) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-08(#2-24-08) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#2-24-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB24L08.get(AB24L08.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#2-24-08) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-08(#2-24-08) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#2-24-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L08.get(AB24L08.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#2-24-08) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-08(#2-24-08) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#2-24-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L08.get(AB24L08.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-08(#2-24-08) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-08(#2-24-08) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-08(#2-24-08)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-08(#2-24-08)");
			Insertdto.setSensor_status(AB24L08.get(AB24L08.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB24L08");
			Insertdto.setTime(AB24L08.get(AB24L08.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB24L08.get(AB24L08.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L09.size() == 0) {
			
			Insertdto.setSensor_name("누액-09(#1-16-09)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L09");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L09.get(AA16L09.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#1-16-09) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-09(#1-16-09) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L09.get(AA16L09.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#1-16-09) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-09(#1-16-09) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L09.get(AA16L09.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#1-16-09) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-09(#1-16-09) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L09.get(AA16L09.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#1-16-09) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-09(#1-16-09) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#1-16-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-09(#1-16-09)");
			Insertdto.setSensor_status(AA16L09.get(AA16L09.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L09");
			Insertdto.setTime(AA16L09.get(AA16L09.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L09.get(AA16L09.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB24L09.size() == 0) {
			
			Insertdto.setSensor_name("누액-09(#2-24-09)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB24L09");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB24L09.get(AB24L09.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#2-24-09) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-09(#2-24-09) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#2-24-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB24L09.get(AB24L09.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#2-24-09) 센서 상태 알림 - 경고2단계"); 
					notificationdto.setContents("누액-09(#2-24-09) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#2-24-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L09.get(AB24L09.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#2-24-09) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-09(#2-24-09) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#2-24-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB24L09.get(AB24L09.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-09(#2-24-09) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-09(#2-24-09) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-09(#2-24-09)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-09(#2-24-09)");
			Insertdto.setSensor_status(AB24L09.get(AB24L09.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB24L09");
			Insertdto.setTime(AB24L09.get(AB24L09.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB24L09.get(AB24L09.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		
	}

	@Override
	public void SensorTest2() throws Exception {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date Sensortime = new Date();

		logger.info(Sensortime.toGMTString());
		
		//센서밸류
		SensorDTO sensordto = new SensorDTO();
		
		Calendar cal1 = Calendar.getInstance();
		
		//Default 시간 + 9
		Calendar cal2 = Calendar.getInstance();
		
		cal2.setTime(Sensortime);
		
		cal1.setTime(Sensortime);
		
		cal2.add(Calendar.HOUR_OF_DAY, 9);
		
		//AWS 시간
		cal1.add(Calendar.HOUR_OF_DAY , 5);
		
		//로컬용 시간
		//cal1.add(Calendar.HOUR_OF_DAY , -5);
		
		String OriginTime = format1.format(new Date(cal2.getTimeInMillis()));
		
		String time2 = format1.format(new Date(cal1.getTimeInMillis()));
		
		logger.info("After Time : " + time2);
		
		logger.info("now Time" + OriginTime);
		
		sensordto.setHour1time(time2);
		
		//현재시간
		sensordto.setHour2time(OriginTime);
		
		List<SensorDTO> AllSensorList = sensorDAO.ValueSensorList(sensordto);
		
		DbSensor_InsertDTO Insertdto = new DbSensor_InsertDTO();
		
		List<SensorDTO> AA15L15 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB27L15 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA15L16 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB32L16 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA15L17 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB32L17 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA20L18 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB28L18 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA14L19 = new ArrayList<SensorDTO>();
	
		List<SensorDTO> AB28L19 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12L20 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB28L20 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB28L21 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB27L22 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27L23 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27L24 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27L25 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB22L01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB22L02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB22L03 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB22L04 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB22L05 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27L21 = new ArrayList<SensorDTO>();

		List<SensorDTO> AC08L01 = new ArrayList<SensorDTO>();
	
		List<SensorDTO> AC08L02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA10L01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA10L02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA10F06 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37A02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37A01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12P01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB31P01 = new ArrayList<SensorDTO>();

		List<SensorDTO> AC01G01 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA12P02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27P02 = new ArrayList<SensorDTO>();

		List<SensorDTO> AC01G02 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA10P03 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AD37A04 = new ArrayList<SensorDTO>();

		List<SensorDTO> AD37A03 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L10 = new ArrayList<SensorDTO>();

		List<SensorDTO> AB31L10 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA16L11 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB31L11 = new ArrayList<SensorDTO>();

		List<SensorDTO> AA16L12 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA15L13 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AA15L14 = new ArrayList<SensorDTO>();
		
		List<SensorDTO> AB27L14 = new ArrayList<SensorDTO>();

		//분류 작업
		for(int i = 0; i < AllSensorList.size(); i++) {
			
			if(AllSensorList.get(i).ss_id.equals("AA15L15")) {
				
				AA15L15.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27L15")) {
				
				AB27L15.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA15L16")) {
				
				AA15L16.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AB32L16")) {
				
				AB32L16.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA15L17")) {
				
				AA15L17.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB32L17")) {
				
				AB32L17.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA20L18")) {
				
				AA20L18.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB28L18")) {
				
				AB28L18.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA14L19")) {
				
				AA14L19.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AB28L19")) {
				
				AB28L19.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12L20")) {
				
				AA12L20.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB28L20")) {
				
				AB28L20.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB28L21")) {
				
				AB28L21.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27L22")) {
				
				AB27L22.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27L23")) {
				
				AB27L23.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AB27L24")) {
				
				AB27L24.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27L25")) {
				
				AB27L25.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB22L01")) {
				
				AB22L01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB22L02")) {
				
				AB22L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB22L03")) {
				
				AB22L03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB22L04")) {
				
				AB22L04.add(AllSensorList.get(i));
			}
			
			//
			
			if(AllSensorList.get(i).ss_id.equals("AB22L05")) {
				
				AB22L05.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27L21")) {
				
				AB27L21.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AC08L01")) {
				
				AC08L01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC08L02")) {
				
				AC08L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10L01")) {
				
				AA10L01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10L02")) {
				
				AA10L02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10F06")) {
				
				AA10F06.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37A02")) {
				
				AD37A02.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AD37A01")) {
				
				AD37A01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA12P01")) {
				
				AA12P01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB31P01")) {
				
				AB31P01.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AC01G01")) {
				
				AC01G01.add(AllSensorList.get(i));
			}
		
			if(AllSensorList.get(i).ss_id.equals("AA12P02")) {
				
				AA12P02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB27P02")) {
				
				AB27P02.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AC01G02")) {
				
				AC01G02.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA10P03")) {
				
				AA10P03.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AD37A04")) {
				
				AD37A04.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AD37A03")) {
				
				AD37A03.add(AllSensorList.get(i));
			}

			if(AllSensorList.get(i).ss_id.equals("AA16L10")) {
				
				AA16L10.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB31L10")) {
				
				AB31L10.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L11")) {
				
				AA16L11.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AB31L11")) {
				
				AB31L11.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA16L12")) {
				
				AA16L12.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA15L13")) {
				
				AA15L13.add(AllSensorList.get(i));
			}
			
			if(AllSensorList.get(i).ss_id.equals("AA15L14")) {
				
				AA15L14.add(AllSensorList.get(i));
			}
			
			
			if(AllSensorList.get(i).ss_id.equals("AB27L14")) {
				
				AB27L14.add(AllSensorList.get(i));
			}			
			
	
		}
		
		
		
		//null의 경우
		if(AA16L10.size() == 0) {
			
			Insertdto.setSensor_name("누액-10(#1-16-10)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L10");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L10.get(AA16L10.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#1-16-10) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-10(#1-16-10) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L10.get(AA16L10.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#1-16-10) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-10(#1-16-10) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L10.get(AA16L10.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#1-16-10) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-10(#1-16-10) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L10.get(AA16L10.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#1-16-10) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-10(#1-16-10) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#1-16-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-10(#1-16-10)");
			Insertdto.setSensor_status(AA16L10.get(AA16L10.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L10");
			Insertdto.setTime(AA16L10.get(AA16L10.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L10.get(AA16L10.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB31L10.size() == 0) {
			
			Insertdto.setSensor_name("누액-10(#2-31-10)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB31L10");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB31L10.get(AB31L10.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#2-31-10) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-10(#2-31-10) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#2-31-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB31L10.get(AB31L10.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#2-31-10) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-10(#2-31-10) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator") 
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#2-31-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31L10.get(AB31L10.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#2-31-10) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-10(#2-31-10) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#2-31-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31L10.get(AB31L10.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-10(#2-31-10) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-10(#2-31-10) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-10(#2-31-10)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-10(#2-31-10)");
			Insertdto.setSensor_status(AB31L10.get(AB31L10.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB31L10");
			Insertdto.setTime(AB31L10.get(AB31L10.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB31L10.get(AB31L10.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L11.size() == 0) {
			
			Insertdto.setSensor_name("누액-11(#1-16-11)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L11");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L11.get(AA16L11.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#1-16-11) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-11(#1-16-11) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L11.get(AA16L11.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#1-16-11) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-11(#1-16-11) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L11.get(AA16L11.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#1-16-11) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-11(#1-16-11) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L11.get(AA16L11.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#1-16-11) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-11(#1-16-11) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-11(#1-16-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-11(#1-16-11)");
			Insertdto.setSensor_status(AA16L11.get(AA16L11.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L11");
			Insertdto.setTime(AA16L11.get(AA16L11.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L11.get(AA16L11.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB31L11.size() == 0) {
			
			Insertdto.setSensor_name("누액-11(#2-31-11)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB31L11");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB31L11.get(AB31L11.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#2-31-11) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-11(#2-31-11) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#2-31-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB31L11.get(AB31L11.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#2-31-11) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-11(#2-31-11) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-11(#2-31-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31L11.get(AB31L11.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#2-31-11) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-11(#2-31-11) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#2-31-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31L11.get(AB31L11.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-11(#2-31-11) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-11(#2-31-11) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-11(#2-31-11)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-11(#2-31-11)");
			Insertdto.setSensor_status(AB31L11.get(AB31L11.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB31L11");
			Insertdto.setTime(AB31L11.get(AB31L11.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB31L11.get(AB31L11.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA16L12.size() == 0) {
			
			Insertdto.setSensor_name("누액-12(#1-16-12)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA16L12");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA16L12.get(AA16L12.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-12(#1-16-12) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-12(#1-16-12) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA16L12.get(AA16L12.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-12(#1-16-12) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-12(#1-16-12) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L12.get(AA16L12.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-12(#1-16-12) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-12(#1-16-12) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA16L12.get(AA16L12.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-12(#1-16-12) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-12(#1-16-12) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-12(#1-16-12)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-12(#1-16-12)");
			Insertdto.setSensor_status(AA16L12.get(AA16L12.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA16L12");
			Insertdto.setTime(AA16L12.get(AA16L12.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA16L12.get(AA16L12.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15L13.size() == 0) {
			
			Insertdto.setSensor_name("누액-13(#1-15-13)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15L13");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15L13.get(AA15L13.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-13(#1-15-13) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-13(#1-15-13) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-13(#1-15-13)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15L13.get(AA15L13.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-13(#1-15-13) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-13(#1-15-13) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-13(#1-15-13)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L13.get(AA15L13.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-13(#1-15-13) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-13(#1-15-13) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-13(#1-15-13)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L13.get(AA15L13.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-13(#1-15-13) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-13(#1-15-13) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-13(#1-15-13)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-13(#1-15-13)");
			Insertdto.setSensor_status(AA15L13.get(AA15L13.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15L13");
			Insertdto.setTime(AA15L13.get(AA15L13.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15L13.get(AA15L13.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15L14.size() == 0) {
			
			Insertdto.setSensor_name("누액-14(#1-15-14)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15L14");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15L14.get(AA15L14.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#1-15-14) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-14(#1-15-14) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#1-15-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15L14.get(AA15L14.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#1-15-14) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-14(#1-15-14) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#1-15-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L14.get(AA15L14.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#1-15-14) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-14(#1-15-14) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#1-15-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L14.get(AA15L14.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#1-15-14) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-14(#1-15-14) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#1-15-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-14(#1-15-14)");
			Insertdto.setSensor_status(AA15L14.get(AA15L14.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15L14");
			Insertdto.setTime(AA15L14.get(AA15L14.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15L14.get(AA15L14.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L14.size() == 0) {
			
			Insertdto.setSensor_name("누액-14(#2-27-14)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L14");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L14.get(AB27L14.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#2-27-14) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-14(#2-27-14) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#2-27-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L14.get(AB27L14.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#2-27-14) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-14(#2-27-14) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#2-27-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L14.get(AB27L14.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#2-27-14) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-14(#2-27-14) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#2-27-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L14.get(AB27L14.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-14(#2-27-14) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-14(#2-27-14) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-14(#2-27-14)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-14(#2-27-14)");
			Insertdto.setSensor_status(AB27L14.get(AB27L14.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L14");
			Insertdto.setTime(AB27L14.get(AB27L14.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L14.get(AB27L14.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		
		//null의 경우
		if(AA15L15.size() == 0) {
			
			Insertdto.setSensor_name("누액-15(#1-15-15)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15L15");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15L15.get(AA15L15.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#1-15-15) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-15(#1-15-15) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#1-15-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15L15.get(AA15L15.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#1-15-15) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-15(#1-15-15) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#1-15-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L15.get(AA15L15.size()-1).ss_Stat == "03") {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#1-15-15) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-15(#1-15-15) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#1-15-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L15.get(AA15L15.size()-1).ss_Stat == "22") {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#1-15-15) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-15(#1-15-15) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#1-15-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-15(#1-15-15)");
			Insertdto.setSensor_status(AA15L15.get(AA15L15.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15L15");
			Insertdto.setTime(AA15L15.get(AA15L15.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15L15.get(AA15L15.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L15.size() == 0) {
			
			Insertdto.setSensor_name("누액-15(#2-27-15)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L15");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L15.get(AB27L15.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#2-27-15) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-15(#2-27-15) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#2-27-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L15.get(AB27L15.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#2-27-15) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-15(#2-27-15) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#2-27-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L15.get(AB27L15.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#2-27-15) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-15(#2-27-15) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#2-27-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L15.get(AB27L15.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-15(#2-27-15) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-15(#2-27-15) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-15(#2-27-15)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-15(#2-27-15)");
			Insertdto.setSensor_status(AB27L15.get(AB27L15.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L15");
			Insertdto.setTime(AB27L15.get(AB27L15.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L15.get(AB27L15.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15L16.size() == 0) {
			
			Insertdto.setSensor_name("누액-16(#1-15-16)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15L16");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15L16.get(AA15L16.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#1-15-16) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-16(#1-15-16) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#1-15-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15L16.get(AA15L16.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#1-15-16) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-16(#1-15-16) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#1-15-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L16.get(AA15L16.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#1-15-16) 센서 상태 알림 - 경고3단계"); 
					notificationdto.setContents("누액-16(#1-15-16)" + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#1-15-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L16.get(AA15L16.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#1-15-16) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-16(#1-15-16) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#1-15-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-16(#1-15-16)");
			Insertdto.setSensor_status(AA15L16.get(AA15L16.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15L16");
			Insertdto.setTime(AA15L16.get(AA15L16.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15L16.get(AA15L16.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB32L16.size() == 0) {
			
			Insertdto.setSensor_name("누액-16(#2-32-16)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB32L16");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB32L16.get(AB32L16.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#2-32-16) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-16(#2-32-16) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#2-32-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB32L16.get(AB32L16.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#2-32-16) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-16(#2-32-16) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#2-32-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB32L16.get(AB32L16.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#2-32-16) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-16(#2-32-16) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#2-32-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB32L16.get(AB32L16.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-16(#2-32-16) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-16(#2-32-16) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-16(#2-32-16)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-16(#2-32-16)");
			Insertdto.setSensor_status(AB32L16.get(AB32L16.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB32L16");
			Insertdto.setTime(AB32L16.get(AB32L16.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB32L16.get(AB32L16.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA15L17.size() == 0) {
			
			Insertdto.setSensor_name("누액-17(#1-15-17)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA15L17");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA15L17.get(AA15L17.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#1-15-17) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-17(#1-15-17) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#1-15-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA15L17.get(AA15L17.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#1-15-17) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-17(#1-15-17) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#1-15-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L17.get(AA15L17.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#1-15-17) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-17(#1-15-17) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#1-15-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA15L17.get(AA15L17.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#1-15-17) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-17(#1-15-17) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#1-15-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-17(#1-15-17)");
			Insertdto.setSensor_status(AA15L17.get(AA15L17.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA15L17");
			Insertdto.setTime(AA15L17.get(AA15L17.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA15L17.get(AA15L17.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB32L17.size() == 0) {
			
			Insertdto.setSensor_name("누액-17(#2-32-17)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB32L17");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB32L17.get(AB32L17.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#2-32-17) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-17(#2-32-17) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#2-32-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB32L17.get(AB32L17.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#2-32-17) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-17(#2-32-17) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#2-32-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB32L17.get(AB32L17.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#2-32-17) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-17(#2-32-17) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#2-32-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB32L17.get(AB32L17.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-17(#2-32-17) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-17(#2-32-17) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-17(#2-32-17)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-17(#2-32-17)");
			Insertdto.setSensor_status(AB32L17.get(AB32L17.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB32L17");
			Insertdto.setTime(AB32L17.get(AB32L17.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB32L17.get(AB32L17.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA20L18.size() == 0) {
			
			Insertdto.setSensor_name("누액-18(#1-20-18)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA20L18");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA20L18.get(AA20L18.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#1-20-18) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-18(#1-20-18) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#1-20-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA20L18.get(AA20L18.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#1-20-18) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-18(#1-20-18) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#1-20-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA20L18.get(AA20L18.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#1-20-18) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-18(#1-20-18) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#1-20-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA20L18.get(AA20L18.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#1-20-18) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-18(#1-20-18) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#1-20-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-18(#1-20-18)");
			Insertdto.setSensor_status(AA20L18.get(AA20L18.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA20L18");
			Insertdto.setTime(AA20L18.get(AA20L18.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA20L18.get(AA20L18.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28L18.size() == 0) {
			
			Insertdto.setSensor_name("누액-18(#2-28-18)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28L18");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB28L18.get(AB28L18.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#2-28-18) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-18(#2-28-18) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#2-28-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB28L18.get(AB28L18.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#2-28-18) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-18(#2-28-18) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#2-28-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L18.get(AB28L18.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#2-28-18) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-18(#2-28-18) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#2-28-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L18.get(AB28L18.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-18(#2-28-18) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-18(#2-28-18) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-18(#2-28-18)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-18(#2-28-18)");
			Insertdto.setSensor_status(AB28L18.get(AB28L18.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28L18");
			Insertdto.setTime(AB28L18.get(AB28L18.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28L18.get(AB28L18.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA14L19.size() == 0) {
			
			Insertdto.setSensor_name("누액-19(#1-14-19)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA14L19");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA14L19.get(AA14L19.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#1-14-19) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-19(#1-14-19) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#1-14-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA14L19.get(AA14L19.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#1-14-19) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-19(#1-14-19) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#1-14-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14L19.get(AA14L19.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#1-14-19) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-19(#1-14-19) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#1-14-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA14L19.get(AA14L19.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#1-14-19) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-19(#1-14-19) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#1-14-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-19(#1-14-19)");
			Insertdto.setSensor_status(AA14L19.get(AA14L19.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA14L19");
			Insertdto.setTime(AA14L19.get(AA14L19.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA14L19.get(AA14L19.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28L19.size() == 0) {
			
			Insertdto.setSensor_name("누액-19(#2-28-19)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28L19");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB28L19.get(AB28L19.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#2-28-19) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-19(#2-28-19) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#2-28-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB28L19.get(AB28L19.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#2-28-19) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-19(#2-28-19) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#2-28-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L19.get(AB28L19.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#2-28-19) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-19(#2-28-19) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-19(#2-28-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L19.get(AB28L19.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-19(#2-28-19) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-19(#2-28-19) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-19(#2-28-19)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-19(#2-28-19)");
			Insertdto.setSensor_status(AB28L19.get(AB28L19.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28L19");
			Insertdto.setTime(AB28L19.get(AB28L19.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28L19.get(AB28L19.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12L20.size() == 0) {
			
			Insertdto.setSensor_name("누액-20(#1-12-20)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12L20");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-20(#1-12-20)");
			Insertdto.setSensor_status(AA12L20.get(AA12L20.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12L20");
			Insertdto.setTime(AA12L20.get(AA12L20.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12L20.get(AA12L20.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28L20.size() == 0) {
			
			Insertdto.setSensor_name("누액-20(#2-28-20)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28L20");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12L20.get(AA12L20.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-20(#1-12-20) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-20(#1-12-20) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-20(#1-12-20)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-20(#2-28-20)");
			Insertdto.setSensor_status(AB28L20.get(AB28L20.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28L20");
			Insertdto.setTime(AB28L20.get(AB28L20.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28L20.get(AB28L20.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB28L21.size() == 0) {
			
			Insertdto.setSensor_name("누액-21(#2-28-21)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB28L21");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB28L21.get(AB28L21.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-21(#2-28-21) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-21(#2-28-21) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-21(#2-28-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB28L21.get(AB28L21.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-21(#2-28-21) 센서 상태 알림 - 경고2단계"); 
					notificationdto.setContents("누액-21(#2-28-21)" + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-21(#2-28-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L21.get(AB28L21.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-21(#2-28-21) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-21(#2-28-21) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-21(#2-28-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB28L21.get(AB28L21.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-21(#2-28-21) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-21(#2-28-21) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-21(#2-28-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-21(#2-28-21)");
			Insertdto.setSensor_status(AB28L21.get(AB28L21.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB28L21");
			Insertdto.setTime(AB28L21.get(AB28L21.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB28L21.get(AB28L21.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L22.size() == 0) {
			
			Insertdto.setSensor_name("누액-22(#2-27-22)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L22");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L22.get(AB27L22.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-22(#2-27-22) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-22(#2-27-22) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-22(#2-27-22)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L22.get(AB27L22.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-22(#2-27-22) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-22(#2-27-22) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-22(#2-27-22)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L22.get(AB27L22.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-22(#2-27-22) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-22(#2-27-22) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-22(#2-27-22)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L22.get(AB27L22.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-22(#2-27-22) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-22(#2-27-22) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-22(#2-27-22)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-22(#2-27-22)");
			Insertdto.setSensor_status(AB27L22.get(AB27L22.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L22");
			Insertdto.setTime(AB27L22.get(AB27L22.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L22.get(AB27L22.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L23.size() == 0) {
			
			Insertdto.setSensor_name("누액-23(#2-27-23)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L23");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L23.get(AB27L23.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-23(#2-27-23) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-23(#2-27-23) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-23(#2-27-23)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L23.get(AB27L23.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-23(#2-27-23) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-23(#2-27-23) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-23(#2-27-23)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L23.get(AB27L23.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-23(#2-27-23) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-23(#2-27-23) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-23(#2-27-23)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L23.get(AB27L23.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-23(#2-27-23) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-23(#2-27-23) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-23(#2-27-23)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-23(#2-27-23)");
			Insertdto.setSensor_status(AB27L23.get(AB27L23.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L23");
			Insertdto.setTime(AB27L23.get(AB27L23.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L23.get(AB27L23.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L24.size() == 0) {
			
			Insertdto.setSensor_name("누액-24(#2-27-24)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L24");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L24.get(AB27L24.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-24(#2-27-24) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-24(#2-27-24)) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("누액-24(#2-27-24)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L24.get(AB27L24.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-24(#2-27-24) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-24(#2-27-24) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-24(#2-27-24)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L24.get(AB27L24.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-24(#2-27-24) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-24(#2-27-24) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-24(#2-27-24)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L24.get(AB27L24.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-24(#2-27-24) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-24(#2-27-24) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-24(#2-27-24)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-24(#2-27-24)");
			Insertdto.setSensor_status(AB27L24.get(AB27L24.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L24");
			Insertdto.setTime(AB27L24.get(AB27L24.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L24.get(AB27L24.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L25.size() == 0) {
			
			Insertdto.setSensor_name("누액-25(#2-27-25)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L25");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L25.get(AB27L25.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-25(#2-27-25) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-25(#2-27-25) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-25(#2-27-25)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L25.get(AB27L25.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-25(#2-27-25) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-25(#2-27-25) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-25(#2-27-25)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L25.get(AB27L25.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-25(#2-27-25) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-25(#2-27-25) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-25(#2-27-25)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L25.get(AB27L25.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-25(#2-27-25) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-25(#2-27-25) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-25(#2-27-25)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-25(#2-27-25)");
			Insertdto.setSensor_status(AB27L25.get(AB27L25.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L25");
			Insertdto.setTime(AB27L25.get(AB27L25.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L25.get(AB27L25.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB22L01.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-22-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB22L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB22L01.get(AB22L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-22-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB22L01.get(AB22L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#2-22-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L01.get(AB22L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-22-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L01.get(AB22L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-22-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-22-01)");
			Insertdto.setSensor_status(AB22L01.get(AB22L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB22L01");
			Insertdto.setTime(AB22L01.get(AB22L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB22L01.get(AB22L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB22L02.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-22-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB22L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB22L02.get(AB22L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-22-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB22L02.get(AB22L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-02) 센서 상태 알림 - 경고2단계"); 
					notificationdto.setContents("누액-W(#2-22-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L02.get(AB22L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-22-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L02.get(AB22L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-02)센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-22-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-22-02)");
			Insertdto.setSensor_status(AB22L02.get(AB22L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB22L02");
			Insertdto.setTime(AB22L02.get(AB22L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB22L02.get(AB22L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB22L03.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-22-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB22L03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB22L03.get(AB22L03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-22-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB22L03.get(AB22L03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#2-22-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L03.get(AB22L03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-22-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L03.get(AB22L03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-22-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-22-03)");
			Insertdto.setSensor_status(AB22L03.get(AB22L03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB22L03");
			Insertdto.setTime(AB22L03.get(AB22L03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB22L03.get(AB22L03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB22L04.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-22-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB22L04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB22L04.get(AB22L04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-22-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB22L04.get(AB22L04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#2-22-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L04.get(AB22L04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-22-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L04.get(AB22L04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-22-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-22-04)");
			Insertdto.setSensor_status(AB22L04.get(AB22L04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB22L04");
			Insertdto.setTime(AB22L04.get(AB22L04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB22L04.get(AB22L04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB22L05.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-22-05)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB22L05");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB22L05.get(AB22L05.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-05) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-22-05) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB22L05.get(AB22L05.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-05) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#2-22-05) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L05.get(AB22L05.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-05) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-22-05) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB22L05.get(AB22L05.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-22-05) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-22-05) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-22-05)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-22-05)");
			Insertdto.setSensor_status(AB22L05.get(AB22L05.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB22L05");
			Insertdto.setTime(AB22L05.get(AB22L05.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB22L05.get(AB22L05.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27L21.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#2-27-21)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27L21");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27L21.get(AB27L21.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-27-21) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#2-27-21) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-27-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27L21.get(AB27L21.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-27-21) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#2-27-21) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-27-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L21.get(AB27L21.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-27-21) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#2-27-21) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-27-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27L21.get(AB27L21.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#2-27-21) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#2-27-21) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#2-27-21)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#2-27-21)");
			Insertdto.setSensor_status(AB27L21.get(AB27L21.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27L21");
			Insertdto.setTime(AB27L21.get(AB27L21.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27L21.get(AB27L21.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC08L01.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#3-08-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC08L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC08L01.get(AC08L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#3-08-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC08L01.get(AC08L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#3-08-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC08L01.get(AC08L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#3-08-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC08L01.get(AC08L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#3-08-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#3-08-01)");
			Insertdto.setSensor_status(AC08L01.get(AC08L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC08L01");
			Insertdto.setTime(AC08L01.get(AC08L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC08L01.get(AC08L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC08L02.size() == 0) {
			
			Insertdto.setSensor_name("누액-W(#3-08-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC08L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC08L02.get(AC08L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("누액-W(#3-08-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC08L02.get(AC08L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("누액-W(#3-08-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC08L02.get(AC08L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("누액-W(#3-08-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC08L02.get(AC08L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("누액-W(#3-08-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("누액-W(#3-08-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("누액-W(#3-08-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("누액-W(#3-08-02)");
			Insertdto.setSensor_status(AC08L02.get(AC08L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC08L02");
			Insertdto.setTime(AC08L02.get(AC08L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC08L02.get(AC08L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10L01.size() == 0) {
			
			Insertdto.setSensor_name("방폭누액-01(#1-10-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10L01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10L01.get(AA10L01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-01(#1-10-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("방폭누액-01(#1-10-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-01(#1-10-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10L01.get(AA10L01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-01(#1-10-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("방폭누액-01(#1-10-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-01(#1-10-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10L01.get(AA10L01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-01(#1-10-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("방폭누액-01(#1-10-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-01(#1-10-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10L01.get(AA10L01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-01(#1-10-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("방폭누액-01(#1-10-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-01(#1-10-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("방폭누액-01(#1-10-01)");
			Insertdto.setSensor_status(AA10L01.get(AA10L01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10L01");
			Insertdto.setTime(AA10L01.get(AA10L01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10L01.get(AA10L01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10L02.size() == 0) {
			
			Insertdto.setSensor_name("방폭누액-02(#1-10-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10L02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10L02.get(AA10L02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-02(#1-10-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("방폭누액-02(#1-10-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-02(#1-10-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10L02.get(AA10L02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-02(#1-10-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("방폭누액-02(#1-10-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-02(#1-10-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10L02.get(AA10L02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-02(#1-10-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("방폭누액-02(#1-10-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-02(#1-10-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10L02.get(AA10L02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("방폭누액-02(#1-10-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("방폭누액-02(#1-10-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("방폭누액-02(#1-10-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("방폭누액-02(#1-10-02)");
			Insertdto.setSensor_status(AA10L02.get(AA10L02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10L02");
			Insertdto.setTime(AA10L02.get(AA10L02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10L02.get(AA10L02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10F06.size() == 0) {
			
			Insertdto.setSensor_name("불꽃-W(#1-10-06)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10F06");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10F06.get(AA10F06.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("불꽃-W(#1-10-06) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("불꽃-W(#1-10-06) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("불꽃-W(#1-10-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10F06.get(AA10F06.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("불꽃-W(#1-10-06) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("불꽃-W(#1-10-06) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("불꽃-W(#1-10-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10F06.get(AA10F06.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("불꽃-W(#1-10-06) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("불꽃-W(#1-10-06) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("불꽃-W(#1-10-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10F06.get(AA10F06.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("불꽃-W(#1-10-06) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("불꽃-W(#1-10-06) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("불꽃-W(#1-10-06)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("불꽃-W(#1-10-06)");
			Insertdto.setSensor_status(AA10F06.get(AA10F06.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10F06");
			Insertdto.setTime(AA10F06.get(AA10F06.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10F06.get(AA10F06.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A02.size() == 0) {
			
			Insertdto.setSensor_name("습도-복합기상(#4-37-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A02.get(AD37A02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("습도-복합기상(#4-37-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("습도-복합기상(#4-37-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("습도-복합기상(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A02.get(AD37A02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("습도-복합기상(#4-37-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("습도-복합기상(#4-37-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("습도-복합기상(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A02.get(AD37A02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("습도-복합기상(#4-37-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("습도-복합기상(#4-37-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("습도-복합기상(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A02.get(AD37A02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("습도-복합기상(#4-37-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("습도-복합기상(#4-37-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("습도-복합기상(#4-37-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("습도-복합기상(#4-37-02)");
			Insertdto.setSensor_status(AD37A02.get(AD37A02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A02");
			Insertdto.setTime(AD37A02.get(AD37A02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A02.get(AD37A02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A01.size() == 0) {
			
			Insertdto.setSensor_name("온도-복합기상(#4-37-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A01.get(AD37A01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("온도-복합기상(#4-37-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("온도-복합기상(#4-37-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("온도-복합기상(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A01.get(AD37A01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("온도-복합기상(#4-37-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("온도-복합기상(#4-37-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("온도-복합기상(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A01.get(AD37A01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("온도-복합기상(#4-37-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("온도-복합기상(#4-37-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("온도-복합기상(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A01.get(AD37A01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("온도-복합기상(#4-37-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("온도-복합기상(#4-37-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("온도-복합기상(#4-37-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("온도-복합기상(#4-37-01)");
			Insertdto.setSensor_status(AD37A01.get(AD37A01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A01");
			Insertdto.setTime(AD37A01.get(AD37A01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A01.get(AD37A01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12P01.size() == 0) {
			
			Insertdto.setSensor_name("외부-01(#1-12-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12P01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12P01.get(AA12P01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#1-12-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-01(#1-12-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12P01.get(AA12P01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#1-12-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-01(#1-12-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12P01.get(AA12P01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#1-12-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-01(#1-12-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12P01.get(AA12P01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#1-12-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-01(#1-12-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#1-12-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-01(#1-12-01)");
			Insertdto.setSensor_status(AA12P01.get(AA12P01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12P01");
			Insertdto.setTime(AA12P01.get(AA12P01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12P01.get(AA12P01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB31P01.size() == 0) {
			
			Insertdto.setSensor_name("외부-01(#2-31-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB31P01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB31P01.get(AB31P01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#2-31-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-01(#2-31-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#2-31-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB31P01.get(AB31P01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#2-31-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-01(#2-31-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#2-31-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31P01.get(AB31P01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#2-31-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-01(#2-31-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#2-31-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB31P01.get(AB31P01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#2-31-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-01(#2-31-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#2-31-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-01(#2-31-01)");
			Insertdto.setSensor_status(AB31P01.get(AB31P01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB31P01");
			Insertdto.setTime(AB31P01.get(AB31P01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB31P01.get(AB31P01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01G01.size() == 0) {
			
			Insertdto.setSensor_name("외부-01(#3-01-01)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01G01");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01G01.get(AC01G01.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#3-01-01) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-01(#3-01-01) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01G01.get(AC01G01.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#3-01-01) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-01(#3-01-01) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G01.get(AC01G01.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#3-01-01) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-01(#3-01-01) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G01.get(AC01G01.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-01(#3-01-01) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-01(#3-01-01) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-01(#3-01-01)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-01(#3-01-01)");
			Insertdto.setSensor_status(AC01G01.get(AC01G01.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01G01");
			Insertdto.setTime(AC01G01.get(AC01G01.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01G01.get(AC01G01.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA12P02.size() == 0) {
			
			Insertdto.setSensor_name("외부-02(#1-12-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA12P02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA12P02.get(AA12P02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#1-12-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-02(#1-12-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA12P02.get(AA12P02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#1-12-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-02(#1-12-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12P02.get(AA12P02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#1-12-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-02(#1-12-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA12P02.get(AA12P02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#1-12-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-02(#1-12-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#1-12-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-02(#1-12-02)");
			Insertdto.setSensor_status(AA12P02.get(AA12P02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA12P02");
			Insertdto.setTime(AA12P02.get(AA12P02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA12P02.get(AA12P02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AB27P02.size() == 0) {
			
			Insertdto.setSensor_name("외부-02(#2-27-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AB27P02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AB27P02.get(AB27P02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#2-27-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-02(#2-27-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#2-27-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AB27P02.get(AB27P02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#2-27-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-02(#2-27-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#2-27-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27P02.get(AB27P02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#2-27-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-02(#2-27-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#2-27-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AB27P02.get(AB27P02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#2-27-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-02(#2-27-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#2-27-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-02(#2-27-02)");
			Insertdto.setSensor_status(AB27P02.get(AB27P02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AB27P02");
			Insertdto.setTime(AB27P02.get(AB27P02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AB27P02.get(AB27P02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AC01G02.size() == 0) {
			
			Insertdto.setSensor_name("외부-02(#3-01-02)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AC01G02");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AC01G02.get(AC01G02.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#3-01-02) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-02(#3-01-02) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AC01G02.get(AC01G02.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#3-01-02) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-02(#3-01-02) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G02.get(AC01G02.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#3-01-02) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-02(#3-01-02) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AC01G02.get(AC01G02.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-02(#3-01-02) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-02(#3-01-02) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-02(#3-01-02)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-02(#3-01-02)");
			Insertdto.setSensor_status(AC01G02.get(AC01G02.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AC01G02");
			Insertdto.setTime(AC01G02.get(AC01G02.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AC01G02.get(AC01G02.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AA10P03.size() == 0) {
			
			Insertdto.setSensor_name("외부-03(#1-10-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AA10P03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AA10P03.get(AA10P03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-03(#1-10-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("외부-03(#1-10-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AA10P03.get(AA10P03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-03(#1-10-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("외부-03(#1-10-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10P03.get(AA10P03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-03(#1-10-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("외부-03(#1-10-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AA10P03.get(AA10P03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("외부-03(#1-10-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("외부-03(#1-10-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("외부-03(#1-10-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("외부-03(#1-10-03)");
			Insertdto.setSensor_status(AA10P03.get(AA10P03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AA10P03");
			Insertdto.setTime(AA10P03.get(AA10P03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AA10P03.get(AA10P03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A04.size() == 0) {
			
			Insertdto.setSensor_name("풍속-복합기상(#4-37-04)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A04");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A04.get(AD37A04.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍속-복합기상(#4-37-04) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("풍속-복합기상(#4-37-04) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다."); 
					notificationdto.setSensor_name("풍속-복합기상(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A04.get(AD37A04.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍속-복합기상(#4-37-04) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("풍속-복합기상(#4-37-04) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍속-복합기상(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A04.get(AD37A04.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍속-복합기상(#4-37-04) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("풍속-복합기상(#4-37-04) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍속-복합기상(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A04.get(AD37A04.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍속-복합기상(#4-37-04) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("풍속-복합기상(#4-37-04) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍속-복합기상(#4-37-04)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("풍속-복합기상(#4-37-04)");
			Insertdto.setSensor_status(AD37A04.get(AD37A04.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A04");
			Insertdto.setTime(AD37A04.get(AD37A04.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A04.get(AD37A04.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//null의 경우
		if(AD37A03.size() == 0) {
			
			Insertdto.setSensor_name("풍향-복합기상(#4-37-03)");
			Insertdto.setSensor_status("-9999");
			Insertdto.setSs_full_id("AD37A03");
			Insertdto.setTime(cal2.getTime());
			Insertdto.setValue("-9999");
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}
		//null이 아닌 경우
		else {
			
			if(AD37A03.get(AD37A03.size()-1).ss_Stat.equals("01")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍향-복합기상(#4-37-03) 센서 상태 알림 - 경고1단계");
					notificationdto.setContents("풍향-복합기상(#4-37-03) 센서 상태가 경고1단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍향-복합기상(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}

			else if(AD37A03.get(AD37A03.size()-1).ss_Stat.equals("02")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍향-복합기상(#4-37-03) 센서 상태 알림 - 경고2단계");
					notificationdto.setContents("풍향-복합기상(#4-37-03) 센서 상태가 경고2단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍향-복합기상(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A03.get(AD37A03.size()-1).ss_Stat.equals("03")) {
				
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍향-복합기상(#4-37-03) 센서 상태 알림 - 경고3단계");
					notificationdto.setContents("풍향-복합기상(#4-37-03) 센서 상태가 경고3단계 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍향-복합기상(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
				
			}
			
			else if(AD37A03.get(AD37A03.size()-1).ss_Stat.equals("22")) {
			
				AssignListDTO assgindto = new AssignListDTO();
				
				SensorNotificaitonDTO notificationdto = new SensorNotificaitonDTO();
				
				assgindto.setArea_code("mk_factory_ansan_1");
				
				List<AssignListDTO> result = assgindao.AssignUserList(assgindto);
				
				for(int i = 0; i < result.size(); i++) {
					
					notificationdto.setTarget_user_id(result.get(i).user_id);
					notificationdto.setTitle("풍향-복합기상(#4-37-03) 센서 상태 알림 - CCTV 이상 감지");
					notificationdto.setContents("풍향-복합기상(#4-37-03) 센서 상태가 CCTV 이상 감지 입니다." + System.getProperty("line.separator")
							+ "센서의 지속적인 경고가 발생 될 경우 시설물 확인이 필요합니다.");
					notificationdto.setSensor_name("풍향-복합기상(#4-37-03)");
				
					sensornotidao.RegisterSensorNotification(notificationdto);
				}
			}
			
			Insertdto.setSensor_name("풍향-복합기상(#4-37-03)");
			Insertdto.setSensor_status(AD37A03.get(AD37A03.size()-1).ss_Stat);
			Insertdto.setSs_full_id("AD37A03");
			Insertdto.setTime(AD37A03.get(AD37A03.size()-1).Mdfy_Dttm);
			Insertdto.setValue(AD37A03.get(AD37A03.size()-1).ss_Value);
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}

	}
	
	
	
	/*
	@Override
	public void AutoDataInsert() throws Exception {
		
		DbSensor_InsertDTO Insertdto = new DbSensor_InsertDTO();
		
		
		//
		List<RSensorDTO> AA10C01 = sensorDAO.AA10C01();
		
		if(AA10C01.get(0) == null) {
			
			Insertdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
			Insertdto.setSensor_status("09");
			Insertdto.setSs_full_id("AA10C01");
			Insertdto.setTime(time);
			Insertdto.setValue(-9999);
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}else {
			
			Insertdto.setSensor_name("CCTV(불꽃감지(ZONE #1))");
			Insertdto.setSensor_status(AA10C01.get(0).ss_Stat);
			Insertdto.setSs_full_id(AA10C01.get(0).ss_ID);
			Insertdto.setTime(AA10C01.get(0).Mdfy_Dttm);
			Insertdto.setValue(AA10C01.get(0).ss_Value);	
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//
		List<RSensorDTO> AA10F01 = sensorDAO.AA10F01();
		
		if(AA10F01.get(0) == null) {
			
			Insertdto.setSensor_name("불꽃-W(#1-10-01)");
			Insertdto.setSensor_status("00");
			Insertdto.setSs_full_id("AA10F01");
			Insertdto.setTime(time);
			Insertdto.setValue(-9999);
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}else {
			
			Insertdto.setSensor_name("불꽃-W(#1-10-01)");
			Insertdto.setSensor_status(AA10F01.get(0).ss_Stat);
			Insertdto.setSs_full_id(AA10F01.get(0).ss_ID);
			Insertdto.setTime(AA10F01.get(0).Mdfy_Dttm);
			Insertdto.setValue(AA10F01.get(0).ss_Value);	
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//
		List<RSensorDTO> AA10G03 = sensorDAO.AA10G03();
		
		if(AA10F01.get(0) == null) {
			
			Insertdto.setSensor_name("가스-03(#1-10-03)");
			Insertdto.setSensor_status("00");
			Insertdto.setSs_full_id("AA10G03");
			Insertdto.setTime(time);
			Insertdto.setValue(-9999);
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}else {
			
			Insertdto.setSensor_name("가스-03(#1-10-03)");
			Insertdto.setSensor_status(AA10G03.get(0).ss_Stat);
			Insertdto.setSs_full_id(AA10G03.get(0).ss_ID);
			Insertdto.setTime(AA10G03.get(0).Mdfy_Dttm);
			Insertdto.setValue(AA10G03.get(0).ss_Value);	
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
		
		//
		List<RSensorDTO> AA10G04 = sensorDAO.AA10G04();
		
		if(AA10F01.get(0) == null) {
			
			Insertdto.setSensor_name("가스-04(#1-10-04)");
			Insertdto.setSensor_status("00");
			Insertdto.setSs_full_id("AA10G04");
			Insertdto.setTime(time);
			Insertdto.setValue(-9999);
			
			csensorDAO.RSensorDataInsert(Insertdto);
			
		}else {
			
			Insertdto.setSensor_name("가스-04(#1-10-04)");
			Insertdto.setSensor_status(AA10G04.get(0).ss_Stat);
			Insertdto.setSs_full_id(AA10G04.get(0).ss_ID);
			Insertdto.setTime(AA10G04.get(0).Mdfy_Dttm);
			Insertdto.setValue(AA10G04.get(0).ss_Value);	
			
			csensorDAO.RSensorDataInsert(Insertdto);
		}
	}
	*/
	
	@Override
	public ResponseEntity<Message> DefaultSensorList() throws Exception {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date time1 = new Date();

		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		//session.setAttribute("login", userService.loginSuccess(user_id));
		
		ZonelistDTO zonelist = new ZonelistDTO();
		
		Zone1_SectionlistDTO sectionlist1 = new Zone1_SectionlistDTO();
		
		Zone2_SectionlistDTO sectionlist2 = new Zone2_SectionlistDTO();
		
		Zone3_SectionlistDTO sectionlist3 = new Zone3_SectionlistDTO();
		
		Zone4_SectionlistDTO sectionlist4 = new Zone4_SectionlistDTO();
		
		sectionlist3.setSECTION_01(csensorDAO.Zone3_List_1());
		
		sectionlist3.setSECTION_08(csensorDAO.Zone3_List_2());
		
		sectionlist1.setSECTION_10(csensorDAO.Zone1_List_1());
		
		sectionlist1.setSECTION_12(csensorDAO.Zone1_List_2());
		
		sectionlist1.setSECTION_14(csensorDAO.Zone1_List_3());
		
		sectionlist1.setSECTION_15(csensorDAO.Zone1_List_4());
		
		sectionlist1.setSECTION_16(csensorDAO.Zone1_List_5());
		
		sectionlist1.setSECTION_20(csensorDAO.Zone1_List_6());
		
		sectionlist2.setSECTION_22(csensorDAO.Zone2_List_1());
		
		sectionlist2.setSECTION_23(csensorDAO.Zone2_List_2());
		
		sectionlist2.setSECTION_24(csensorDAO.Zone2_List_3());
		
		sectionlist2.setSECTION_27(csensorDAO.Zone2_List_4());
		
		sectionlist2.setSECTION_28(csensorDAO.Zone2_List_5());
		
		sectionlist2.setSECTION_31(csensorDAO.Zone2_List_6());
		
		sectionlist2.setSECTION_32(csensorDAO.Zone2_List_7());
		
		sectionlist4.setSECTION_35(csensorDAO.Zone4_List_1());
		
		sectionlist4.setSECTION_36(csensorDAO.Zone4_List_2());
		
		sectionlist4.setSECTION_37(csensorDAO.Zone4_List_3());
		
		zonelist.setZONE_1(sectionlist1);
		zonelist.setZONE_2(sectionlist2);
		zonelist.setZONE_3(sectionlist3);
		zonelist.setZONE_4(sectionlist4);
		
		//센서밸류
		SensorDTO sensordto = new SensorDTO();
		
		Calendar cal1 = Calendar.getInstance();
		
		//Default 시간 + 9
		Calendar cal2 = Calendar.getInstance();
		
		cal2.setTime(time1);
		
		cal1.setTime(time1);
		
		cal2.add(Calendar.HOUR_OF_DAY, 9);
		
		cal1.add(Calendar.HOUR_OF_DAY , 5);
		
		String OriginTime = format1.format(new Date(cal2.getTimeInMillis()));
		
		String time2 = format1.format(new Date(cal1.getTimeInMillis()));
		
		sensordto.setHour1time(time2);
		
		//현재시간
		sensordto.setHour2time(OriginTime);
		
		logger.info("After Time : " + time2);
		
		logger.info("now Time" + OriginTime);
		
		SensorDataDTO sendDTO = new SensorDataDTO();
		
		sendDTO.setDefault_Data(zonelist);
		sendDTO.setSensor_value(sensorDAO.ValueSensorList(sensordto));

		message.setResult("success");
		message.setMessage("성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(sendDTO);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);		

	}
	
	/*
	@Override
	public ResponseEntity<Message> DefaultSensorList(UserDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		//유효 사용자 체크
		String bcryPwd = userDAO.loginCheck(dto.getUser_id());
		
		Integer numtest = userDAO.OverlapID(dto.getUser_id());
		
		if(numtest == 0) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("로그인 정보가 정확하지 않습니다.");
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
		if (passwordEncoder.matches(dto.getUser_pw(), bcryPwd)) {
			
			List<Sensor_pinDTO> pin_list = sensor_pinDAO.SelectListPin(dto.getUser_id());
			
			//session.setAttribute("login", userService.loginSuccess(user_id));
			
			ZonelistDTO zonelist = new ZonelistDTO();
			
			Zone1_SectionlistDTO sectionlist1 = new Zone1_SectionlistDTO();
			
			Zone2_SectionlistDTO sectionlist2 = new Zone2_SectionlistDTO();
			
			Zone3_SectionlistDTO sectionlist3 = new Zone3_SectionlistDTO();
			
			Zone4_SectionlistDTO sectionlist4 = new Zone4_SectionlistDTO();
			
			sectionlist3.setSECTION_01(csensorDAO.Zone3_List_1());
			
			sectionlist3.setSECTION_08(csensorDAO.Zone3_List_2());
			
			sectionlist1.setSECTION_10(csensorDAO.Zone1_List_1());
			
			sectionlist1.setSECTION_12(csensorDAO.Zone1_List_2());
			
			sectionlist1.setSECTION_14(csensorDAO.Zone1_List_3());
			
			sectionlist1.setSECTION_15(csensorDAO.Zone1_List_4());
			
			sectionlist1.setSECTION_16(csensorDAO.Zone1_List_5());
			
			sectionlist1.setSECTION_20(csensorDAO.Zone1_List_6());
			
			sectionlist2.setSECTION_22(csensorDAO.Zone2_List_1());
			
			sectionlist2.setSECTION_23(csensorDAO.Zone2_List_2());
			
			sectionlist2.setSECTION_24(csensorDAO.Zone2_List_3());
			
			sectionlist2.setSECTION_27(csensorDAO.Zone2_List_4());
			
			sectionlist2.setSECTION_28(csensorDAO.Zone2_List_5());
			
			sectionlist2.setSECTION_31(csensorDAO.Zone2_List_6());
			
			sectionlist2.setSECTION_32(csensorDAO.Zone2_List_7());
			
			sectionlist4.setSECTION_35(csensorDAO.Zone4_List_1());
			
			sectionlist4.setSECTION_36(csensorDAO.Zone4_List_2());
			
			sectionlist4.setSECTION_37(csensorDAO.Zone4_List_3());
			
			zonelist.setZONE_1(sectionlist1);
			zonelist.setZONE_2(sectionlist2);
			zonelist.setZONE_3(sectionlist3);
			zonelist.setZONE_4(sectionlist4);
			
			//센서밸류
			SensorDTO sensordto = new SensorDTO();
			
			Calendar cal1 = Calendar.getInstance();
			
			//Default 시간 + 9
			Calendar cal2 = Calendar.getInstance();
			
			cal2.setTime(time);
			
			cal1.setTime(time);
			
			cal2.add(Calendar.HOUR_OF_DAY, 9);
			
			cal1.add(Calendar.HOUR_OF_DAY , 5);
			
			String OriginTime = format1.format(new Date(cal2.getTimeInMillis()));
			
			String time2 = format1.format(new Date(cal1.getTimeInMillis()));
			
			sensordto.setHour1time(time2);
			
			//현재시간
			sensordto.setHour2time(OriginTime);
			
			logger.info("After Time : " + time2);
			
			logger.info("now Time" + OriginTime);
			
			SensorDataDTO sendDTO = new SensorDataDTO();
			
			sendDTO.setDefault_Data(zonelist);
			sendDTO.setSensor_value(sensorDAO.ValueSensorList(sensordto));
			
			if(pin_list.get(0).getSensor_name() == null) {
				sendDTO.setPin_value("pin 고정데이터 없음");
			}else {
				sendDTO.setPin_value(pin_list);
			}
			message.setResult("success");
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(sendDTO);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("로그인 정보가 정확하지 않습니다.");
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		

	}
	
	*/
	
	@Override
	public ResponseEntity<Message> ValueSensorList() throws Exception {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date time1 = new Date();

		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		//센서밸류
		SensorDTO sensordto = new SensorDTO();
		
		Calendar cal1 = Calendar.getInstance();
		
		//Default 시간 + 9
		Calendar cal2 = Calendar.getInstance();
		
		cal2.setTime(time1);
		
		cal1.setTime(time1);
		
		cal2.add(Calendar.HOUR_OF_DAY, 9);
		
		cal1.add(Calendar.HOUR_OF_DAY , 5);
		
		String OriginTime = format1.format(new Date(cal2.getTimeInMillis()));
		
		String time2 = format1.format(new Date(cal1.getTimeInMillis()));
		
		sensordto.setHour1time(time2);
		
		//현재시간
		sensordto.setHour2time(OriginTime);
		
		logger.info("After Time : " + time2);
		
		logger.info("now Time" + OriginTime);
		
		logger.info(time2 + "---" + OriginTime);
		
		message.setResult("success");
		message.setMessage("성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(sensorDAO.ValueSensorList(sensordto));
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Message> RegisterPinSensor(Sensor_pinDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			sensor_pinDAO.RegisterPinSensor(dto);
			
			message.setResult("success");
			message.setMessage("등록 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("등록 실패");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> BasicSensorDataList() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ZonelistDTO zonelist = new ZonelistDTO();
		
		Zone1_SectionlistDTO sectionlist1 = new Zone1_SectionlistDTO();
		
		Zone2_SectionlistDTO sectionlist2 = new Zone2_SectionlistDTO();
		
		Zone3_SectionlistDTO sectionlist3 = new Zone3_SectionlistDTO();
		
		Zone4_SectionlistDTO sectionlist4 = new Zone4_SectionlistDTO();
		
		sectionlist3.setSECTION_01(csensorDAO.Zone3_List_1());
		
		sectionlist3.setSECTION_08(csensorDAO.Zone3_List_2());
		
		sectionlist1.setSECTION_10(csensorDAO.Zone1_List_1());
		
		sectionlist1.setSECTION_12(csensorDAO.Zone1_List_2());
		
		sectionlist1.setSECTION_14(csensorDAO.Zone1_List_3());
		
		sectionlist1.setSECTION_15(csensorDAO.Zone1_List_4());
		
		sectionlist1.setSECTION_16(csensorDAO.Zone1_List_5());
		
		sectionlist1.setSECTION_20(csensorDAO.Zone1_List_6());
		
		sectionlist2.setSECTION_22(csensorDAO.Zone2_List_1());
		
		sectionlist2.setSECTION_23(csensorDAO.Zone2_List_2());
		
		sectionlist2.setSECTION_24(csensorDAO.Zone2_List_3());
		
		sectionlist2.setSECTION_27(csensorDAO.Zone2_List_4());
		
		sectionlist2.setSECTION_28(csensorDAO.Zone2_List_5());
		
		sectionlist2.setSECTION_31(csensorDAO.Zone2_List_6());
		
		sectionlist2.setSECTION_32(csensorDAO.Zone2_List_7());
		
		sectionlist4.setSECTION_35(csensorDAO.Zone4_List_1());
		
		sectionlist4.setSECTION_36(csensorDAO.Zone4_List_2());
		
		sectionlist4.setSECTION_37(csensorDAO.Zone4_List_3());
		
		zonelist.setZONE_1(sectionlist1);
		zonelist.setZONE_2(sectionlist2);
		zonelist.setZONE_3(sectionlist3);
		zonelist.setZONE_4(sectionlist4);

		message.setResult("success");
		message.setMessage("성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(zonelist);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);	
		
	}
	
	@Override
	public ResponseEntity<Message> SelectListPin(Sensor_pinDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<Sensor_pinDTO> result = sensor_pinDAO.SelectListPin(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("센서 핀 조회 완료");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			message.setResult("success");
			message.setMessage("센서 핀 조회 없음");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> DeletePinSensor(Sensor_pinDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			sensor_pinDAO.DeletePinSensor(dto);
			
			message.setResult("success");
			message.setMessage("해제 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("해제 실패");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}
		
}
