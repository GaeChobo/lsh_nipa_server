package com.meta.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.dto.FacilityDTO;
import com.meta.dto.InterferenceDTO;
import com.meta.dto.InterferenceFinalChoiceDTO;
import com.meta.dto.InterferenceLocationDTO;
import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_detail_result;
import com.meta.dto.Interference_final_drawingDTO;
import com.meta.dto.Request_preprocessingDTO;
import com.meta.dto.UpdateInterferenceDetailDTO;
import com.meta.dto.UpdateVirtualDetailDTO;
import com.meta.dto.VirtualDTO;
import com.meta.dto.VirtualFinalChoiceDTO;
import com.meta.dto.VirtualFinalDTO;
import com.meta.dto.VirtualLocationDTO;
import com.meta.dto.Virtual_detailDTO;
import com.meta.dto.Virtual_detail_result;
import com.meta.dto.danger_data;
import com.meta.dto.interference_data;
import com.meta.service.FacilityService;
import com.meta.service.InterferenceService;
import com.meta.service.RequestService;
import com.meta.service.VirtualService;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;
import com.opencsv.CSVWriter;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RequestController {

	
	@Autowired
	RequestService requestService;
	
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	InterferenceService interferenceService;
	
	@Autowired
	VirtualService virtualService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	
	@ApiOperation(value="edc_nipa_req_007", notes="4.‘등록’ 버튼 으로 도면 데이터 전처리 요청 (https://www.notion.so/edc_nipa_req_007-a0f4fb6f0dbf47c9a4e880ab7be0ab00)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file", value = "file"),
		@ApiImplicitParam(name = "user_id", value = "String"),
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "request_title", value = "String"),
		@ApiImplicitParam(name = "description", value = "String")
	})
	@RequestMapping(value = "/RequestDrawing", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RequestDrawing(MultipartHttpServletRequest request, Request_preprocessingDTO dto) throws Exception {
		
		logger.info(time1 + " ( RequestDrawing )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd_HHmmss");
		
		MultipartFile file = request.getFile("file");
		
		String os = System.getProperty("os.name").toLowerCase();
		
		String path = "";
		
		if(os.contains("win")) {
			path = ("C:\\Users\\dltmd\\eclipse-workspace\\META\\target\\FileTest\\");
		}
		else if(os.contains("linux")) {
		
			path = ("/home/ubuntu/files/storage/path_drawing/");
			
		}else {
			
			message.setError_code(ErrorEnum.OS_ERROR);
			message.setResult("fail");
			message.setMessage("os 구분 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
			

		
		File dir = new File("/home/ubuntu/files/storage/path_drawing/");
		
		File[] dirs = dir.listFiles();
		
		String time3 = format2.format(cal.getTime());
		
		try {
			
			for(int i = 0; i < dirs.length; i++) {
				

				if(dirs[i].toString() == path+dto.getUser_id()) {
				
					String realpath = path+dto.getUser_id();
					
					String savepath = realpath+"/"+time3;
					
					File TimeFolder = new File(savepath);
					
					TimeFolder.mkdirs();
					
					File saveFile;
					
					saveFile = new File(savepath+"/"+file.getOriginalFilename());
					
					file.transferTo(saveFile);
					
					dto.setPath_drawing(savepath+"/"+file.getOriginalFilename());
					
					String drawing = "drawing_"+dto.getUser_id()+"_"+dto.getArea_code()+"_"+time3;
					
					dto.setVersion_drawing(drawing);
					
					requestService.RequestDrawing(dto);
					
					message.setError_code(ErrorEnum.NONE);
					message.setResult("success");
					message.setMessage("등록 완료");
					message.setStatus(StatusEnum.OK);
					message.setData(null);
					
					return new ResponseEntity<>(message, headers, HttpStatus.OK);
					
				}
				
			}
			
			String realpath = path+dto.getUser_id();
			
			File Folder = new File(realpath);
			
			Folder.mkdirs();
			
			String savepath = realpath+"/"+time3;
			
			File saveFolder = new File(savepath);
			
			saveFolder.mkdirs();
			
			File saveFile;
			
			saveFile = new File(savepath+"/"+file.getOriginalFilename());
			
			file.transferTo(saveFile);
			
			dto.setPath_drawing(savepath+"/"+file.getOriginalFilename());
			
			String drawing = "drawing_"+dto.getUser_id()+"_"+dto.getArea_code()+"_"+time3;
			
			dto.setVersion_drawing(drawing);
			
			requestService.RequestDrawing(dto);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("등록 완료");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setError_code(ErrorEnum.FILE_ERROR);
			message.setResult("fail");
			message.setMessage("도면 데이터 처리 요청이 실패하였습니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@ApiOperation(value="무브먼츠 전처리 진행중 올린 도면 '확인버튼'", notes="도면 확인 시 업데이트" , produces = "multipart/form-data")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "version_drawing", value = "String",dataTypeClass = String.class),
	})
	@RequestMapping(value = "/UpdateAdminCheck", method = RequestMethod.POST, produces = "multipart/form-data")
	public ResponseEntity<Message> UpdateAdminCheck(@RequestBody Map<String, String> darwing_map) throws Exception {
		

		logger.info(time1 + " ( UpdateAdminCheck )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		
		try {
			
			Request_preprocessingDTO dto = new Request_preprocessingDTO();
			
			dto.setVersion_drawing(darwing_map.get("version_drawing"));
			dto.setUpdate_datetime(cal.getTime());
			
			requestService.UpdateAdminCheck(dto);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("확인완료");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setResult("fail");
			message.setMessage("확인실패");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="무브먼츠 전처리 완료 시", notes="무브먼츠 측 도면 전처리 완료 후 업데이트" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "owner_user_id", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "Zipfile", value = "file", required = true, dataTypeClass = File.class),
		@ApiImplicitParam(name = "ExcelFile", value = "file", required = true, dataTypeClass = File.class)
	})
	@RequestMapping(value = "/UpdateDrawingComplete", method = RequestMethod.POST, produces = "multipart/form-data")
	public ResponseEntity<Message> UpdateDrawingComplete(@RequestParam MultipartFile Zipfile, @RequestParam List<MultipartFile> ExcelFile , @RequestParam String owner_user_id, @RequestParam String version_drawing, @RequestParam String area_code) throws Exception {
		
		logger.info(time1 + " ( UpdateDrawingComplete )");

		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd_HHmmss");
		
		Request_preprocessingDTO requestdto = new Request_preprocessingDTO();

		FacilityDTO facilitydto = new FacilityDTO();

		//시설물 등록 시작 , 요청시설물일 경우에는 version_drawing이 무조건 있고 없으면 그냥 보낼 것
		if(version_drawing == "") {
			
			String null_version_drawing = null;
			facilitydto.setVersion_drawing(null_version_drawing);
			
		}else {
		
			facilitydto.setVersion_drawing(version_drawing);
			facilitydto.setOwner_user_id(owner_user_id);
			
			requestdto.setUpdate_datetime(time);
			requestdto.setVersion_drawing(version_drawing);
		}
		
		//Zip파일처리 폴더 생성 후
	
		String path = ("/home/ubuntu/files/storage/path_csv/");
		
		File dir = new File("/home/ubuntu/files/storage/path_csv/");
		
		File[] dirs = dir.listFiles();
		
		String time3 = format2.format(cal.getTime());
		
		for(int i = 0; i < dirs.length; i++) {
			
			if(dirs[i].toString() == path+owner_user_id) {
			
			String realpath = path+owner_user_id;
			
			String savepath = realpath+"/"+time3;
			
			File TimeFolder = new File(savepath);
			
			TimeFolder.mkdirs();
			
			File saveFile;
			
			saveFile = new File(savepath+"/"+Zipfile.getOriginalFilename());
			
			Zipfile.transferTo(saveFile);
			
			requestdto.setPath_csv(savepath+"/"+Zipfile.getOriginalFilename());
			
			requestdto.setUpdate_datetime(cal.getTime());
			
			requestService.UpdateDrawingComplete(requestdto);
			
			}
		}
		
		String realpath = path+owner_user_id;
		
		File Folder = new File(realpath);
		
		Folder.mkdirs();
		
		String savepath = realpath+"/"+time3;
		
		File saveFolder = new File(savepath);
		
		saveFolder.mkdirs();
		
		File saveFile;
		
		saveFile = new File(savepath+"/"+Zipfile.getOriginalFilename());
		
		Zipfile.transferTo(saveFile);
		
		requestdto.setUpdate_datetime(cal.getTime());
		
		requestdto.setPath_csv(savepath+"/"+Zipfile.getOriginalFilename());
	
		requestService.UpdateDrawingComplete(requestdto);
		
		facilitydto.setOwner_user_id(owner_user_id);

	
		for(int j = 0; j < ExcelFile.size(); j++) {
			
			InputStreamReader isr = new InputStreamReader(ExcelFile.get(j).getInputStream());
				
			BufferedReader br = new BufferedReader(isr);
				
			String line;
				
			List<String> excelContext = new ArrayList<String>();
				
			while((line = br.readLine()) != null) {

				String[] temp = line.split("\t"); // 탭으로 구분
					
				for(int i=0; i<temp.length; i++) {

					excelContext.add(line);
				}
			}
				
			
			logger.info("test : "+excelContext.get(0).length());
			
			//여기는 오수관 우수관
			if(excelContext.get(0).contains("name")) {
				
				List<String> AreaList = new ArrayList<String>();
				List<Integer> GroupList =  new ArrayList<Integer>();
				List<Integer> OrderList = new ArrayList<Integer>();
				List<Double> XList = new ArrayList<Double>();
				List<Double> YList = new ArrayList<Double>();
				List<Double> ELList = new ArrayList<Double>();
				List<String> NameList = new ArrayList<String>();
				List<Double> WidthList = new ArrayList<Double>();
				List<Double> HeightList = new ArrayList<Double>();
				List<Double> DiameterList = new ArrayList<Double>();
				List<Double> LatList = new ArrayList<Double>();
				List<Double> LonList = new ArrayList<Double>();
				List<Integer> TypeList = new ArrayList<Integer>();
				List<Integer> CategoryList = new ArrayList<Integer>();
				
				
				for(int i = 1; i < excelContext.size(); i++) {
					
					String[] TotalList = excelContext.get(i).split(",");
					
					AreaList.add(TotalList[0]);
				
					int Group_no = (int) Double.parseDouble(TotalList[1]);
					GroupList.add(Group_no);
						
					int ORDER = (int) Double.parseDouble(TotalList[2]);
					OrderList.add(ORDER);
					
					if(TotalList[3].equals("")) {
						XList.add(0.0);
					}else {
						Double X = (Double) Double.parseDouble(TotalList[3]);
						XList.add(X);
					}

					if(TotalList[4].equals("")) {
						YList.add(0.0);
					}else {
						Double Y = (Double) Double.parseDouble(TotalList[4]);
						YList.add(Y);
					}

					if(TotalList[5].equals("")) {
						ELList.add(0.0);
					}else {
						Double EL = (Double) Double.parseDouble(TotalList[5]);
						ELList.add(EL);
					}
					
;
					
					NameList.add(TotalList[6]);
					
					if(TotalList[7].equals("")) {
						WidthList.add(0.0);
						
						
					}else {
						Double Width = (Double) Double.parseDouble(TotalList[7]);
						WidthList.add(Width);
					}
					
					if(TotalList[8].equals("")) {
						HeightList.add(0.0);
					}else {
						Double Height = (Double) Double.parseDouble(TotalList[8]);
						HeightList.add(Height);
					}

					if(TotalList[9].equals("")) {
						DiameterList.add(0.0);
					}else {
						Double Diameter = (Double) Double.parseDouble(TotalList[9]);
						DiameterList.add(Diameter);
					}
					
					if(TotalList[10].equals("")) {
						LatList.add(0.0);
					}else {
						Double Lat = (Double) Double.parseDouble(TotalList[10]);
						LatList.add(Lat);
					}
					
					if(TotalList[11].equals("")) {
						LonList.add(0.0);
					}else {
						Double Lon = (Double) Double.parseDouble(TotalList[11]);
						LonList.add(Lon);
					}
					
					int Type = (int) Double.parseDouble(TotalList[12]);
					TypeList.add(Type);
					
					int Category = (int) Double.parseDouble(TotalList[13]);
					CategoryList.add(Category);
					
				}
				
				for(int i = 0; i < AreaList.size(); i++) {
					
					String Object_key = AreaList.get(i)+"_"+XList.get(i)+"_"+YList.get(i)+"("+ELList.get(i)+")";
					
					facilitydto.setArea_code(area_code);
					facilitydto.setGroup_no(GroupList.get(i));
					facilitydto.setOrder(OrderList.get(i));
					facilitydto.setX1(XList.get(i));
					facilitydto.setY1(YList.get(i));
					facilitydto.setEl1(ELList.get(i));
					facilitydto.setName(NameList.get(i));
					facilitydto.setWidth(WidthList.get(i));
					facilitydto.setHeight(HeightList.get(i));
					facilitydto.setDiameter(DiameterList.get(i));
					facilitydto.setLat1(LatList.get(i));
					facilitydto.setLon1(LonList.get(i));
					facilitydto.setType(TypeList.get(i));
					facilitydto.setCategory(CategoryList.get(i));
					facilitydto.setObject_key(Object_key);

					try {
						
						facilityService.CsvDataUpload(facilitydto);
						
					}catch(Exception e) {
						
						message.setResult("fail");
						message.setError_code(ErrorEnum.EXCEL_ERROR);
						message.setStatus(StatusEnum.BAD_REQUEST);
						message.setMessage("잘못된 엑셀 데이터 삽입되있음");
						message.setData(null);
							
						return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
					}
				}
	
			}
			//여기는 오수맨홀, 우수맨홀
			else if(excelContext.get(0).contains("hole_depth")) {
				
				List<String> AreaList = new ArrayList<String>();
				List<String> HoleIdList = new ArrayList<String>();
				List<Double> XList = new ArrayList<Double>();
				List<Double> YList = new ArrayList<Double>();
				List<Double> ELList = new ArrayList<Double>();
				List<Double> HoleDepthList = new ArrayList<Double>();
				List<Double> WidthList = new ArrayList<Double>();
				List<Double> HeightList = new ArrayList<Double>();
				List<Double> DiameterList = new ArrayList<Double>();
				List<Double> LatList = new ArrayList<Double>();
				List<Double> LonList = new ArrayList<Double>();
				List<Integer> TypeList = new ArrayList<Integer>();
				List<Integer> CategoryList = new ArrayList<Integer>();
				
				for(int i = 1; i < excelContext.size(); i++) {
					
					String[] TotalList = excelContext.get(i).split(",");

					AreaList.add(TotalList[0]);
					HoleIdList.add(TotalList[1]);
					
					if(TotalList[2].equals("")) {
						XList.add(0.0);
					}else {
						Double X = (Double) Double.parseDouble(TotalList[2]);
						XList.add(X);
					}
					
					if(TotalList[3].equals("")) {
						YList.add(0.0);
					}else {
						Double Y = (Double) Double.parseDouble(TotalList[3]);
						YList.add(Y);
					}
					
					if(TotalList[4].equals("")) {
						HoleDepthList.add(0.0);
					}else {
						Double HoleDepth = (Double) Double.parseDouble(TotalList[4]);
						HoleDepthList.add(HoleDepth);
					}
					
					if(TotalList[5].equals("")) {
						ELList.add(0.0);
					}else {
						Double EL = (Double) Double.parseDouble(TotalList[5]);
						ELList.add(EL);
					}
					
					if(TotalList[6].equals("")) {
						WidthList.add(0.0);
					}else {
						Double Width = (Double) Double.parseDouble(TotalList[6]);
						WidthList.add(Width);
					}
					
					if(TotalList[7].equals("")) {
						HeightList.add(0.0);
					}else {
						Double Height = (Double) Double.parseDouble(TotalList[7]);
						HeightList.add(Height);
					}
					
					if(TotalList[8].equals("")) {
						DiameterList.add(0.0);
					}else {
						Double Diameter = (Double) Double.parseDouble(TotalList[8]);
						DiameterList.add(Diameter);
					}
					
					if(TotalList[9].equals("")) {
						LatList.add(0.0);
					}else {
						Double Lat = (Double) Double.parseDouble(TotalList[9]);
						LatList.add(Lat);
					}
					
					if(TotalList[10].equals("")) {
						LonList.add(0.0);
					}else {
						Double Lon = (Double) Double.parseDouble(TotalList[10]);
						LonList.add(Lon);
					}
					
					int Type = (int) Double.parseDouble(TotalList[11]);
					TypeList.add(Type);
					
					int Category = (int) Double.parseDouble(TotalList[12]);
					CategoryList.add(Category);
					
					
				}
				
				for(int i = 0; i < AreaList.size(); i++) {
					
					String Object_key = AreaList.get(i)+"_"+XList.get(i)+"_"+YList.get(i)+"("+ELList.get(i)+")";
					
					facilitydto.setArea_code(area_code);
					facilitydto.setName(HoleIdList.get(i));
					facilitydto.setX1(XList.get(i));
					facilitydto.setY1(YList.get(i));
					facilitydto.setHole_depth(HoleDepthList.get(i));
					facilitydto.setEl1(ELList.get(i));
					facilitydto.setWidth(WidthList.get(i));
					facilitydto.setHeight(HeightList.get(i));
					facilitydto.setDiameter(DiameterList.get(i));
					facilitydto.setLat1(LatList.get(i));
					facilitydto.setLon1(LonList.get(i));
					facilitydto.setType(TypeList.get(i));
					facilitydto.setCategory(CategoryList.get(i));
					facilitydto.setVersion_drawing(version_drawing);
					facilitydto.setObject_key(Object_key);
					
					try {
						
						facilityService.CsvDataUpload(facilitydto);
						
					}catch(Exception e) {
						
						message.setResult("fail");
						message.setError_code(ErrorEnum.EXCEL_ERROR);
						message.setStatus(StatusEnum.BAD_REQUEST);
						message.setMessage("잘못된 엑셀 데이터 삽입되있음");
						message.setData(null);
							
						return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
					}
					
					
				}
				
				
			}
			
			//도로, 보도, 블록, 식수대
			else if(!excelContext.get(0).contains("width")) {
				
				logger.info("road");
				
				List<Integer> GroupList =  new ArrayList<Integer>();
				List<Integer> OrderList = new ArrayList<Integer>();
				List<Double> XList = new ArrayList<Double>();
				List<Double> YList = new ArrayList<Double>();
				List<Double> ZList = new ArrayList<Double>();
				List<Double> RealZList = new ArrayList<Double>();
				List<Double> HeightList = new ArrayList<Double>();
				List<Double> LonList = new ArrayList<Double>();
				List<Double> LatList = new ArrayList<Double>();
				List<Integer> TypeList = new ArrayList<Integer>();
				List<Integer> CategoryList = new ArrayList<Integer>();

				for(int i = 1; i < excelContext.size(); i++) {
					
					String[] TotalList = excelContext.get(i).split(",");

					int Group_no = (int) Double.parseDouble(TotalList[0]);
					GroupList.add(Group_no);
					
					int Order = (int) Double.parseDouble(TotalList[1]);
					OrderList.add(Order);
					
					if(TotalList[2].equals("")) {
						XList.add(0.0);
					}else {
						Double X = (Double) Double.parseDouble(TotalList[2]);
						XList.add(X);
					}
					
					if(TotalList[3].equals("")) {
						YList.add(0.0);
					}else {
						Double Y = (Double) Double.parseDouble(TotalList[3]);
						YList.add(Y);
					}

					if(TotalList[4].equals("")) {
						ZList.add(0.0);
					}else {
						Double Z = (Double) Double.parseDouble(TotalList[4]);
						ZList.add(Z);
					}

					if(TotalList[5].equals("")) {
						RealZList.add(0.0);
					}else {
						Double RealZ = (Double) Double.parseDouble(TotalList[5]);
						RealZList.add(RealZ);
					}
					
					if(TotalList[6].equals("")) {
						HeightList.add(0.0);
					}else {
						Double Height = (Double) Double.parseDouble(TotalList[6]);
						HeightList.add(Height);
					}

					if(TotalList[7].equals("")) {
						LonList.add(0.0);
					}else {
						Double Lon = (Double) Double.parseDouble(TotalList[7]);
						LonList.add(Lon);
					}
					
					if(TotalList[8].equals("")) {
						LatList.add(0.0);
					}else {
						Double Lat = (Double) Double.parseDouble(TotalList[8]);
						LatList.add(Lat);
					}
					
					int Type = (int) Double.parseDouble(TotalList[9]);
					TypeList.add(Type);
					
					int Category = (int) Double.parseDouble(TotalList[10]);
					CategoryList.add(Category);
				}
				
				for (int i = 0; i < GroupList.size(); i++) {

					String Object_key = area_code+"_"+XList.get(i)+"_"+YList.get(i)+"_"+ZList.get(i);
					
					facilitydto.setArea_code(area_code);
					facilitydto.setGroup_no(GroupList.get(i));
					facilitydto.setOrder(OrderList.get(i));
					facilitydto.setX1(XList.get(i));
					facilitydto.setY1(YList.get(i));
					facilitydto.setZ1(ZList.get(i));
					facilitydto.setReal_z1(RealZList.get(i));
					facilitydto.setHeight(HeightList.get(i));
					facilitydto.setLon1(LonList.get(i));
					facilitydto.setLat1(LatList.get(i));
					facilitydto.setType(TypeList.get(i));
					facilitydto.setCategory(CategoryList.get(i));
					facilitydto.setObject_key(Object_key);
					
					try {
						
						facilityService.CsvDataUpload(facilitydto);
						
					}catch(Exception e) {
						
						message.setResult("fail");
						message.setError_code(ErrorEnum.EXCEL_ERROR);
						message.setStatus(StatusEnum.BAD_REQUEST);
						message.setMessage("잘못된 엑셀 데이터 삽입되있음");
						message.setData(null);
							
						return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
					}
					
				}
			}
			
			//여기는 다이크(area없는거), 차선, 차도경계선
			else if(excelContext.get(0).contains("real Z") && excelContext.get(0).contains("width") && excelContext.get(0).contains("height")) {
				
				logger.info("dike");
				
				List<Integer> GroupList =  new ArrayList<Integer>();
				List<Integer> OrderList = new ArrayList<Integer>();
				List<Double> XList = new ArrayList<Double>();
				List<Double> YList = new ArrayList<Double>();
				List<Double> ZList = new ArrayList<Double>();
				List<Double> RealZList = new ArrayList<Double>();
				List<Double> WidthList = new ArrayList<Double>();
				List<Double> HeightList = new ArrayList<Double>();
				List<Double> LatList = new ArrayList<Double>();
				List<Double> LonList = new ArrayList<Double>();
				List<Integer> TypeList = new ArrayList<Integer>();
				List<Integer> CategoryList = new ArrayList<Integer>();
				
				for(int i = 1; i < excelContext.size(); i++) {
					
					String[] TotalList = excelContext.get(i).split(",");

					int Group_no = (int) Double.parseDouble(TotalList[0]);
					GroupList.add(Group_no);
					
					int Order = (int) Double.parseDouble(TotalList[1]);
					OrderList.add(Order);
					
					if(TotalList[2].equals("")) {
						XList.add(0.0);
					}else {
						Double X = (Double) Double.parseDouble(TotalList[2]);
						XList.add(X);
					}
					
					if(TotalList[3].equals("")) {
						YList.add(0.0);
					}else {
						Double Y = (Double) Double.parseDouble(TotalList[3]);
						YList.add(Y);
					}

					if(TotalList[4].equals("")) {
						ZList.add(0.0);
					}else {
						Double Z = (Double) Double.parseDouble(TotalList[4]);
						ZList.add(Z);
					}

					if(TotalList[5].equals("")) {
						RealZList.add(0.0);
					}else {
						Double RealZ = (Double) Double.parseDouble(TotalList[5]);
						RealZList.add(RealZ);
					}
					
					if(TotalList[6].equals("")) {
						WidthList.add(0.0);
					}else {
						Double Width = (Double) Double.parseDouble(TotalList[6]);
						WidthList.add(Width);
					}
					
					if(TotalList[7].equals("")) {
						HeightList.add(0.0);
					}else {
						Double Height = (Double) Double.parseDouble(TotalList[7]);
						HeightList.add(Height);
					}

					if(TotalList[8].equals("")) {
						LonList.add(0.0);
					}else {
						Double Lon = (Double) Double.parseDouble(TotalList[8]);
						LonList.add(Lon);
					}
					
					if(TotalList[9].equals("")) {
						LatList.add(0.0);
					}else {
						Double Lat = (Double) Double.parseDouble(TotalList[9]);
						LatList.add(Lat);
					}
					
					int Type = (int) Double.parseDouble(TotalList[10]);
					TypeList.add(Type);
					
					int Category = (int) Double.parseDouble(TotalList[11]);
					CategoryList.add(Category);
				}
				
				for (int i = 0; i < GroupList.size(); i++) {
				
					String Object_key = area_code+"_"+XList.get(i)+"_"+YList.get(i)+"_"+ZList.get(i);
					
					facilitydto.setArea_code(area_code);
					facilitydto.setGroup_no(GroupList.get(i));
					facilitydto.setOrder(OrderList.get(i));
					facilitydto.setX1(XList.get(i));
					facilitydto.setY1(YList.get(i));
					facilitydto.setZ1(ZList.get(i));
					facilitydto.setReal_z1(RealZList.get(i));
					facilitydto.setWidth(WidthList.get(i));
					facilitydto.setHeight(HeightList.get(i));
					facilitydto.setLon1(LonList.get(i));
					facilitydto.setLat1(LatList.get(i));
					facilitydto.setType(TypeList.get(i));
					facilitydto.setCategory(CategoryList.get(i));
					facilitydto.setObject_key(Object_key);

					try {
						
						facilityService.CsvDataUpload(facilitydto);
						
					}catch(Exception e) {
						
						message.setResult("fail");
						message.setError_code(ErrorEnum.EXCEL_ERROR);
						message.setStatus(StatusEnum.BAD_REQUEST);
						message.setMessage("잘못된 엑셀 데이터 삽입되있음");
						message.setData(null);
							
						return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
					}
					

				}
					
			}
			//여기는 시설물
			else if(excelContext.get(0).contains("X1")) {
					

				List<Integer> OrderList = new ArrayList<Integer>();
				List<Double> X1List = new ArrayList<Double>();
				List<Double> Y1List = new ArrayList<Double>();
				List<Double> ZList = new ArrayList<Double>();
				List<Double> X2List = new ArrayList<Double>();
				List<Double> Y2List = new ArrayList<Double>();
				List<Double> Lon1List = new ArrayList<Double>();
				List<Double> Lat1List = new ArrayList<Double>();
				List<Double> Lon2List = new ArrayList<Double>();
				List<Double> Lat2List = new ArrayList<Double>();
				List<Integer> TypeList = new ArrayList<Integer>();
				List<Integer> CategoryList = new ArrayList<Integer>();
				
				for(int i = 1; i < excelContext.size(); i++) {
					
					String[] TotalList = excelContext.get(i).split(",");

					int Order = (int) Double.parseDouble(TotalList[0]);
					OrderList.add(Order);
					
					
					if(TotalList[1].equals("")) {
						X1List.add(0.0);
						
					}else {
						Double X1 = (Double) Double.parseDouble(TotalList[1]);
						X1List.add(X1);
					}
					
					if(TotalList[2].equals("")) {
						Y1List.add(0.0);
						
					}else {
						Double Y1 = (Double) Double.parseDouble(TotalList[2]);
						Y1List.add(Y1);
					}
					
					if(TotalList[3].equals("")) {
						ZList.add(0.0);
						
					}else {
						Double Z = (Double) Double.parseDouble(TotalList[3]);
						ZList.add(Z);
					}
					
					if(TotalList[4].equals("")) {
						X2List.add(0.0);
						
					}else {
						Double X2 = (Double) Double.parseDouble(TotalList[4]);
						X2List.add(X2);
					}
					
					if(TotalList[5].equals("")) {
						Y2List.add(0.0);
						
					}else {
						Double Y2 = (Double) Double.parseDouble(TotalList[5]);
						Y2List.add(Y2);
					}
					
					if(TotalList[6].equals("")) {
						Lon1List.add(0.0);
						
					}else {
						Double Lon1 = (Double) Double.parseDouble(TotalList[6]);
						Lon1List.add(Lon1);
					}
					
					if(TotalList[7].equals("")) {
						Lat1List.add(0.0);
						
					}else {
						Double Lat1 = (Double) Double.parseDouble(TotalList[7]);
						Lat1List.add(Lat1);
					}
					
					if(TotalList[8].equals("")) {
						Lon2List.add(0.0);
						
					}else {
						Double Lon2 = (Double) Double.parseDouble(TotalList[8]);
						Lon2List.add(Lon2);
					}
					
					if(TotalList[9].equals("")) {
						Lat2List.add(0.0);
						
					}else {
						Double Lat2 = (Double) Double.parseDouble(TotalList[9]);
						Lat2List.add(Lat2);
					}
					
					int Type = (int) Double.parseDouble(TotalList[10]);
					TypeList.add(Type);

					int Category = (int) Double.parseDouble(TotalList[11]);
					CategoryList.add(Category);
				}
				
				for (int i = 0; i < OrderList.size(); i++) {

					String Object_key = area_code+"_"+X1List.get(i)+"_"+Y1List.get(i)+"_"+ZList.get(i);
					
					facilitydto.setArea_code(area_code);
					facilitydto.setOrder(OrderList.get(i));
					facilitydto.setX1(X1List.get(i));
					facilitydto.setY1(Y1List.get(i));
					facilitydto.setZ1(ZList.get(i));
					facilitydto.setX2(X2List.get(i));
					facilitydto.setY2(Y2List.get(i));
					facilitydto.setLon1(Lon1List.get(i));
					facilitydto.setLat1(Lat1List.get(i));
					facilitydto.setLon2(Lon2List.get(i));
					facilitydto.setLat2(Lat2List.get(i));
					facilitydto.setType(TypeList.get(i));
					facilitydto.setCategory(CategoryList.get(i));
					facilitydto.setObject_key(Object_key);
					
					try {
						
						facilityService.CsvDataUpload(facilitydto);
						
					}catch(Exception e) {
						
						message.setResult("fail");
						message.setError_code(ErrorEnum.EXCEL_ERROR);
						message.setStatus(StatusEnum.BAD_REQUEST);
						message.setMessage("잘못된 엑셀 데이터 삽입되있음");
						message.setData(null);
							
						return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
					}
					
				}
				
			}
			//그외 틀리면 일로
			else {
			
				message.setResult("fail");
				message.setError_code(ErrorEnum.EXCEL_ERROR);
				message.setStatus(StatusEnum.BAD_REQUEST);
				message.setMessage("잘못된 엑셀 컬럼 형식");
				message.setData(null);
					
				return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			}

		}
		
		message.setResult("success");
		message.setError_code(ErrorEnum.NONE);
		message.setStatus(StatusEnum.OK);
		message.setMessage("전처리 업로드 완료");
		message.setData(null);
			
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="무브먼츠 전처리 완료 후 타사업자 확인", notes="무브먼츠 전처리 완료 후 타사업자 확인 상태 업데이트" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "version_drawing", value = "String",dataTypeClass = String.class)
	})
	@RequestMapping(value = "/UpdateCustomerCheck", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UpdateCustomerCheck(@RequestBody Map<String, String> darwing_map) throws Exception {
		
		logger.info(time1 + " ( UpdateCustomerCheck )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Request_preprocessingDTO requestdto = new Request_preprocessingDTO();
		
		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		try {
			
			requestdto.setUpdate_datetime(cal.getTime());
			requestdto.setVersion_drawing(darwing_map.get("version_drawing"));
			
			requestService.UpdateCustomerCheck(requestdto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("타사업자 확인 성공");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("타사업자 확인 실패");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}

	}
	
	@ApiOperation(value="edc_nipa_req_010 설계 도면 선택", notes="사용자가 등록한 도면 데이터 전처리 항목을 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String",dataTypeClass = String.class)
	})
	@RequestMapping(value = "/DrawingUserSelect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DrawingUserSelect(@RequestBody Map<String, String> darwing_map) throws Exception {
		
		logger.info(time1 + " ( DrawingUserSelect )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Request_preprocessingDTO requestdto = new Request_preprocessingDTO();
		
		
		try {

			requestdto.setUser_id(darwing_map.get("user_id"));
			requestdto.setArea_code(darwing_map.get("area_code"));

			if(requestService.UserRequestNum(requestdto) == 0) {
				
				message.setResult("fail");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("사용할 수 있는 전처리 도면 데이터가 없습니다.");
				message.setData(null);
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
			}else {
			
				message.setResult("success");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("성공");
				message.setData(requestService.DrawingUserSelect(requestdto));
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
			}
			
			
		}catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("사용할 수 있는 전처리 도면 데이터가 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		
	}
	
	
	@ApiOperation(value="edc_nipa_req_010 검토시작 버튼 -1 (사용자가 선택한 도면의 시설물 정보를 제공), edc_nipa_req_012 검토시작 버튼" , notes="시설물 테이블에서 version_drawing에 해당하는 시설물 정보 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/RequestInterUserList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RequestInterUserList(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( RequestInterUserList )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		FacilityDTO facilitydto = new FacilityDTO();
		
		try {
			
			facilitydto.setArea_code(request_map.get("area_code"));
			facilitydto.setOwner_user_id(request_map.get("user_id"));
			facilitydto.setVersion_drawing(request_map.get("version_drawing"));
			
			if (facilityService.RequestInterUserNum(facilitydto) == 0) {
				
				message.setResult("fail");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("해당 도면의 시설물 정보가 없습니다.");
				message.setData(null);
				
				
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}else {
				
				message.setResult("success");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("성공");
				message.setData(facilityService.RequestInterUserList(facilitydto));
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("해당 도면의 시설물 정보가 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}

	
	@ApiOperation(value="edc_nipa_req_010 검토시작 버튼 -2 (간섭 설계 검토 버전 정보 등록 - 단순 버전 정보 저정하는 API)", notes="간섭 설계 확인 결과 데이터(interference_detail) 테이블에 검토 이력 추가" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/RegisterInterferenceDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterInterferenceDetail(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( RegisterInterferenceDetail )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		InterferenceDTO dto = new InterferenceDTO();
		
		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd_HHmmss");
		
		String time3 = format2.format(cal.getTime());
		
		String version_interference = "interference_"+request_map.get("user_id")+"_"+request_map.get("area_code")+"_"+time3;
		
		Map<String, String> interference = new HashMap<String, String>();
		
		interference.put("version_interference", version_interference);
		
		try {

			if(requestService.CheckRequestVersion(request_map.get("version_drawing")) == 0) {
				
				message.setResult("fail");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("등록 실패하였습니다.");
				message.setData(null);
					
				return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			}
			
			dto.setArea_code(request_map.get("area_code"));
			dto.setUser_id(request_map.get("user_id"));
			dto.setVersion_drawing(request_map.get("version_drawing"));
			dto.setVersion_interference(version_interference);
			
			interferenceService.RegisterInterferenceDetail(dto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 완료");
			message.setData(interference);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 실패하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			
		}

	}
		
	
	@ApiOperation(value="edc_nipa_req_010 API : 간섭 설계 검토 결과 데이터 저장 )", notes="검토가 완료된 검토 결과를 저장" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_interference", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "interference_point", value = "int", dataTypeClass = int.class),
		@ApiImplicitParam(name = "x", value = "double[]", dataTypeClass = double.class),
		@ApiImplicitParam(name = "y", value = "double[]", dataTypeClass = double.class),
		@ApiImplicitParam(name = "z", value = "double[]", dataTypeClass = double.class)
	})
	@RequestMapping(value = "/UpdateInterferenceDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UpdateInterferenceDetail(@RequestBody UpdateInterferenceDetailDTO dto) throws Exception {
		
		logger.info(time1 + " ( UpdateInterferenceDetail )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		InterferenceLocationDTO locationdto = new InterferenceLocationDTO();
	
		Request_preprocessingDTO requestdto = new Request_preprocessingDTO();
			
		Interference_detailDTO detaildto = new Interference_detailDTO();
		
		try {

			String version_drawing = interferenceService.FindVersionRequest(dto.getVersion_interference());
			
			if(dto.getInterference_point() == 0) {
				
				Date time = new Date();
				
				Calendar cal = Calendar.getInstance();
				
				cal.setTime(time);
				
				cal.add(Calendar.HOUR, 9);
				
		        requestdto.setUpdate_datetime(cal.getTime());
		        requestdto.setVersion_drawing(version_drawing);
		        
		        requestService.RequestinterferenceComplete(requestdto);
				
		        detaildto.setVersion_interference(dto.getVersion_interference());
		        detaildto.setInterference_point(dto.getInterference_point());
		        detaildto.setPath_result_interference(null);
		        
		        interferenceService.UpdateInterferenceDetail(detaildto);
		        
			}else {

		    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd HHmmss");

		    	Date time = new Date();
		    	
				Calendar cal = Calendar.getInstance();
				
				cal.setTime(time);
				
				cal.add(Calendar.HOUR, 9);
		    	
		    	String time1 = format1.format(cal.getTime());
		    	
		    	String filepath ="/home/ubuntu/files/storage/path_result_interference/"+time1+".csv";
			
		        CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filepath), "MS949"),',', '"');

		        String[] entries = {"interference","간섭지점"};  // 1
		        cw.writeNext(entries);  // 2

		        String[] entries1 = {"간섭지점번호", "x", "y", "z"};  // 3
		        cw.writeNext(entries1);
				
		        for (int i = 0; i < dto.getInterference_point(); i++) {
		        	
		        	String[] Numentries = {String.valueOf(i+1),String.valueOf(dto.getX()[i]),String.valueOf(dto.getY()[i]),String.valueOf(dto.getZ()[i])};
		        	
		        	cw.writeNext(Numentries);

		        	locationdto.setVersion_interference(dto.getVersion_interference());
		        	locationdto.setX(dto.getX()[i]);
		        	locationdto.setY(dto.getY()[i]);
		        	locationdto.setZ(dto.getZ()[i]);
		        	
		        	interferenceService.RegisterInterferenceLocation(locationdto);
		        }
		        
		        cw.close();
		        
		        requestdto.setUpdate_datetime(cal.getTime());
		        requestdto.setVersion_drawing(version_drawing);
		        
		        requestService.RequestinterferenceComplete(requestdto);
		        
		        detaildto.setVersion_interference(dto.getVersion_interference());
		        detaildto.setInterference_point(dto.getInterference_point());
		        detaildto.setPath_result_interference(filepath);
		        
		        interferenceService.UpdateInterferenceDetail(detaildto);
		        
			}
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("저장 성공");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("간섭 설계 검토 결과 저장을 실패 하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
	}
		
	
	@ApiOperation(value="edc_nipa_req_010 ‘결과 저장’ 버튼 )", notes="API : 간섭 설계 검토 결과 데이터 다운로드 링크 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_interference", value = "String", required = true, dataTypeClass = String.class)
	})
	@RequestMapping(value = "/DownloadLinkInterferenceCSV", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DownloadLinkInterferenceCSV(HttpServletResponse response,@RequestParam String user_id, @RequestParam String area_code, @RequestParam String version_interference) throws Exception {
		
		logger.info(time1 + " ( DownloadLinkInterferenceCSV )");
		
		Interference_detailDTO dto = new Interference_detailDTO();
		
		dto.setArea_code(area_code);
		dto.setUser_id(user_id);
		dto.setVersion_interference(version_interference);
		
		String filepath = interferenceService.DownloadLinkInterferenceCSV(dto);

		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		if(filepath == null) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("저장을 할 수 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			
		}else {

			Path filename = Paths.get(filepath);
			
			byte[] fileByte = FileUtils.readFileToByteArray(new File(filepath));
			
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename.getFileName().toString(), "UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");

		    response.getOutputStream().write(fileByte);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		    
		    Map<String, String> path_result_interference = new HashMap<String, String>();
		    
		    path_result_interference.put("path_result_interference", filepath);
		    
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("성공");
			message.setData(path_result_interference);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		

		

		
	    
	}
	
	@ApiOperation(value="edc_nipa_req_010 ‘최적 설계 지정’  체크박스, edc_nipa_req_008 API : 최적 간접 설계 지정 값 변경", notes="API : 최적 간접 설계 지정 값 변경" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_interference", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "final_interference", value = "int", dataTypeClass = int.class)
	})
	@RequestMapping(value = "/InterferenceFinalChoice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> InterferenceFinalChoice(@RequestBody InterferenceFinalChoiceDTO choicedto) throws Exception {
		
		
		logger.info(time1 + " ( InterferenceFinalChoice )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Interference_final_drawingDTO dto = new Interference_final_drawingDTO();
		
		
		//삭제
		if(choicedto.getFinal_interference() == 0) {
			
			dto.setArea_code(choicedto.getArea_code());
			dto.setUser_id(choicedto.getUser_id());
			dto.setVersion_interference(choicedto.getVersion_interference());
			
			interferenceService.DeleteFinalInterference(dto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 해제 하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
			
		}
		//등록
		else if (choicedto.getFinal_interference() == 1) {
			
			
			dto.setArea_code(choicedto.getArea_code());
			dto.setUser_id(choicedto.getUser_id());
			dto.setVersion_interference(choicedto.getVersion_interference());
			
			interferenceService.RegisterFinalInterference(dto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 성공 하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		//잘못된 요청
		else {
			
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("등록 실패 하였습니다.");
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
		
	
	@ApiOperation(value="edc_nipa_req_012 ‘간섭 설계 선택’ 드랍다운 메뉴 ", notes= "API : 사용자가 수행한 간섭 설계 검토 항목을 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/interferenceChoiceList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> interferenceChoiceList(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( interferenceChoiceList )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		InterferenceDTO dto = new InterferenceDTO();

		dto.setArea_code(request_map.get("area_code"));
		dto.setUser_id(request_map.get("user_id"));
		
		if(interferenceService.interferenceChoiceList(dto).isEmpty()) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("사용할 수 있는 간섭 설계 검토 결과가 없습니다.");
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		
		try {

			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("성공");
			message.setData(interferenceService.interferenceChoiceList(dto));
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("사용할 수 있는 간섭 설계 검토 결과가 없습니다.");
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		

		
	}
	
	
	@ApiOperation(value="edc_nipa_req_012 검토시작 버튼 -2", notes="API : 가상 시공 검토 버전 정보 생성 및 가상 시공 데이터 레코드 추가" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_interference", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "virtual_title", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/RegisterVirtualDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterVirtualDetail(@RequestBody Map<String, String> request_map) throws Exception {
		
		
		logger.info(time1 + " ( RegisterVirtualDetail )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		VirtualDTO dto = new VirtualDTO();
		
		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd_HHmmss");
		
		String time3 = format2.format(cal.getTime());
		
		String version_virtual = "virtual_"+request_map.get("user_id")+"_"+request_map.get("area_code")+"_"+time3;
		
		logger.info(version_virtual);
		
		Map<String, String> virtual = new HashMap<String, String>();
		
		virtual.put("version_virtual", version_virtual);
		

			
			if(request_map.get("version_interference").equals("")) {
				
				dto.setVersion_drawing(request_map.get("version_drawing"));
				dto.setArea_code(request_map.get("area_code"));
				dto.setUser_id(request_map.get("user_id"));
				dto.setVirtual_title(request_map.get("virtual_title"));
				dto.setVersion_interference(null);
				dto.setVersion_virtual(version_virtual);
				
			}else {

				dto.setVersion_drawing(request_map.get("version_drawing"));
				dto.setArea_code(request_map.get("area_code"));
				dto.setUser_id(request_map.get("user_id"));
				dto.setVirtual_title(request_map.get("virtual_title"));
				dto.setVersion_interference(request_map.get("version_interference"));
				dto.setVersion_virtual(version_virtual);
				
			}
			
			virtualService.RegisterVirtualDetail(dto);

			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 완료");
			message.setData(virtual);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			

		
	}


	@ApiOperation(value="edc_nipa_req_012 API : 가상 시공 검토 결과 데이터 저장 )", notes="검토가 완료된 검토 결과를 저장" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_virtual", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "danger_point", value = "int", dataTypeClass = int.class),
		@ApiImplicitParam(name = "location", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "width", value = "double[]", dataTypeClass = double.class),
		@ApiImplicitParam(name = "x", value = "double[]", dataTypeClass = double.class),
		@ApiImplicitParam(name = "y", value = "double[]", dataTypeClass = double.class),
		@ApiImplicitParam(name = "z", value = "double[]", dataTypeClass = double.class)
	})
	@RequestMapping(value = "/UpdateVirtualDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UpdateVirtualDetail(@RequestBody UpdateVirtualDetailDTO dto) throws Exception {
		
		logger.info(time1 + " ( UpdateVirtualDetail )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		VirtualDTO virtualdto = new VirtualDTO();
		
		Request_preprocessingDTO requestdto = new Request_preprocessingDTO();
		
		VirtualLocationDTO locationdto = new VirtualLocationDTO();
		
		if(dto.getDanger_point() == 0) {
		
			Date time = new Date();
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
			
	        requestdto.setUpdate_datetime(cal.getTime());
	        requestdto.setVersion_drawing(dto.getVersion_drawing());
	        
	        requestService.RequestVirtualComplete(requestdto);
	        
	        virtualdto.setVersion_virtual(dto.getVersion_virtual());
	        virtualdto.setDanger_point(dto.getDanger_point());
	        virtualdto.setPath_result_virtual(null);
	        
	        virtualService.UpdateVirtualDetail(virtualdto);
	        
	        
		} else {
			
	    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd HHmmss");

	    	Date time = new Date();
	    	
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
	    	
	    	String time1 = format1.format(cal.getTime());
	    	
	    	String filepath ="/home/ubuntu/files/storage/path_result_virtual/"+time1+".csv";
	    	
	    	CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filepath), "MS949"),',', '"');
	    	
	        String[] entries = {"danger_point","위험지점"};  // 1
	        cw.writeNext(entries);  // 2

	        String[] entries1 = {"위험 지점 번호", "x", "y", "depth(깊이)", "location"};  // 3
	        cw.writeNext(entries1);
	        
	        for(int i = 0; i < dto.getDanger_point(); i++) {
	        	
	        	String[] Numentries = {String.valueOf(i+1),String.valueOf(dto.getX()[i]),String.valueOf(dto.getY()[i]),String.valueOf(dto.getZ()[i]), dto.getLocation()};
	        
	        	cw.writeNext(Numentries);
	        	
	        	locationdto.setVersion_virtual(dto.getVersion_virtual());
	        	locationdto.setLocation(dto.getLocation());
	        	locationdto.setX(dto.getX()[i]);
	        	locationdto.setY(dto.getY()[i]);
	        	locationdto.setZ(dto.getZ()[i]);
	        	locationdto.setWidth(dto.getWidth()[i]);
	        	
	        	virtualService.RegisterVirtualLocation(locationdto);
	        	
	        }
	        
	        cw.close();
	        
	        requestdto.setUpdate_datetime(cal.getTime());
	        requestdto.setVersion_drawing(dto.getVersion_drawing());
	        
			requestService.RequestVirtualComplete(requestdto);
			
			virtualdto.setVersion_virtual(dto.getVersion_virtual());
			virtualdto.setDanger_point(dto.getDanger_point());
			virtualdto.setPath_result_virtual(filepath);
			
			virtualService.UpdateVirtualDetail(virtualdto);
		}
		
		
		message.setResult("success");
		message.setError_code(ErrorEnum.NONE);
		message.setStatus(StatusEnum.OK);
		message.setMessage("저장 성공");
		message.setData(null);
			
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
	}
	

	@ApiOperation(value="edc_nipa_req_012 ‘결과 저장’ 버튼 )", notes="API : 간섭 설계 검토 결과 데이터 다운로드 링크 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_virtual", value = "String", required = true, dataTypeClass = String.class)
	})
	@RequestMapping(value = "/DownloadLinkVirtaulCSV", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DownloadLinkVirtaulCSV(HttpServletResponse response,@RequestParam String user_id, @RequestParam String area_code, @RequestParam String version_virtual) throws Exception {
		
		logger.info(time1 + " ( DownloadLinkVirtaulCSV )");
		
		VirtualDTO virtualdto = new VirtualDTO();
		
		virtualdto.setArea_code(area_code);
		virtualdto.setUser_id(user_id);
		virtualdto.setVersion_virtual(version_virtual);
		
		String filepath = virtualService.DownloadLinkVirtaulCSV(version_virtual);
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		if(filepath == null) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("저장을 할 수 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			
		}else {

			Path filename = Paths.get(filepath);
			
			byte[] fileByte = FileUtils.readFileToByteArray(new File(filepath));
			
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename.getFileName().toString(), "UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");

		    response.getOutputStream().write(fileByte);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		    
		    Map<String, String> path_result_interference = new HashMap<String, String>();
		    
		    path_result_interference.put("path_result_virtual", filepath);
		    
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("성공");
			message.setData(path_result_interference);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		
	}
	
	@ApiOperation(value="edc_nipa_req_012 ‘최적 설계 지정’  체크박스", notes="API : 최적 설계 지정 값 변경" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_virtual", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "final_virtual", value = "int", dataTypeClass = int.class)
	})
	@RequestMapping(value = "/VirtualFinalChoice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> VirtualFinalChoice(@RequestBody VirtualFinalChoiceDTO dto) throws Exception {
	
		
		logger.info(time1 + " ( VirtualFinalChoice )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		VirtualFinalDTO finaldto = new VirtualFinalDTO();
		
		if(dto.getFinal_virtual() == 0) {
			
			finaldto.setArea_code(dto.getArea_code());
			finaldto.setUser_id(dto.getUser_id());
			finaldto.setVersion_virtual(dto.getVersion_virtual());
			
			virtualService.DeleteFinalVirtual(finaldto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 해제 하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else if (dto.getFinal_virtual() == 1) {
			
			finaldto.setArea_code(dto.getArea_code());
			finaldto.setUser_id(dto.getUser_id());
			finaldto.setVersion_virtual(dto.getVersion_virtual());
			
			virtualService.RegisterFinalVirtual(finaldto);
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("등록 성공 하였습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		//잘못된 요청
		else {
			
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("등록 실패 하였습니다.");
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value="edc_nipa_req_008 도면 데이터 전처리 항목 카드 ", notes= "API : 도면 데이터 전처리 카드 항목 + 자세히 보기 데이터 까지 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/RequestCardList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")	
	public ResponseEntity<Message> RequestCardList(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( RequestCardList )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
		
			
			Request_preprocessingDTO dto = new Request_preprocessingDTO();
			
			dto.setUser_id(request_map.get("user_id"));
			dto.setArea_code(request_map.get("area_code"));
		
			if(requestService.RequestCardList(dto).isEmpty()) {
				
				message.setResult("fail");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("데이터가 없습니다.");
				message.setData(null);
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}else {
			
				message.setResult("success");
				message.setError_code(ErrorEnum.NONE);
				message.setStatus(StatusEnum.OK);
				message.setMessage("조회 성공");
				message.setData(requestService.RequestCardList(dto));
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}
			
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("데이터가 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
		
	}

	@ApiOperation(value="edc_nipa_req_008 파일 다운로드 링크 )", notes="path값 보내면 다운로드됨" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "DownloadLink", value = "String", required = true, dataTypeClass = String.class),
	})
	@RequestMapping(value = "/CardDownloadLink", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> CardDownloadLink(HttpServletResponse response,@RequestParam String DownloadLink) throws Exception {
		
		logger.info(time1 + " ( CardDownloadLink )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			Path filename = Paths.get(DownloadLink);
			
			byte[] fileByte = FileUtils.readFileToByteArray(new File(DownloadLink));
			
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename.getFileName().toString(), "UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    

		    response.getOutputStream().write(fileByte);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("다운로드 성공");
			message.setData(DownloadLink);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		    
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("다운로드 할 수 없습니다.");
			message.setData(null);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
			
		}
		
	}
				
	
	@ApiOperation(value="edc_nipa_req_008 ‘이력 삭제’ 버튼", notes="API : 진행 상태 변경 (Delete Status -> 4로 변경)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/DeleteRequestUpdate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DeleteRequestUpdate(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( DeleteRequestUpdate )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		try {
			
			Request_preprocessingDTO dto = new Request_preprocessingDTO();
			
			dto.setUser_id(request_map.get("user_id"));
			dto.setArea_code(request_map.get("area_code"));
			dto.setVersion_drawing(request_map.get("version_drawing"));
			dto.setUpdate_datetime(cal.getTime());
			
			
			requestService.DeleteRequestUpdate(dto);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("변경 성공 하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setResult("fail");
			message.setMessage("변경 실패 하였습니다.");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value="edc_nipa_req_011 ‘간섭 설계 검토 자세히 보기’ 버튼", notes="API : 검토결과 간섭 위치 정보제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_interference", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/InterferenceDetailResult", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> InterferenceDetailResult(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( InterferenceDetailResult )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		
		Interference_detailDTO dto = new Interference_detailDTO();
		
		interference_data locationDTO = new interference_data();
		
		Interference_detail_result resultDTO = new Interference_detail_result();
		
		dto.setArea_code(request_map.get("area_code"));
		dto.setUser_id(request_map.get("user_id"));
		dto.setVersion_interference(request_map.get("version_interference"));
	
		
		//둘다 불일치로 데이터가 없을 때
		if(requestService.interferenceDetailResult(dto).isEmpty() && requestService.interferenceDetailLocation(request_map.get("version_interference")).isEmpty()) {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("fail");
			message.setMessage("데이터 조회 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		//좌표값이 없을 때
		else if(requestService.interferenceDetailLocation(request_map.get("version_interference")).isEmpty()) {
		
			String request_titile = requestService.interferenceDetailResult(dto).get(0).request_title;
			String version_interference_ = requestService.interferenceDetailResult(dto).get(0).version_interference;
			int final_interference = requestService.interferenceDetailResult(dto).get(0).final_interference;
			
			resultDTO.setFinal_interference(final_interference);
			resultDTO.setInterference_data(null);
			resultDTO.setRequest_title(request_titile);
			resultDTO.setVersion_interference(version_interference_);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setData(resultDTO);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else if(requestService.interferenceDetailResult(dto).isEmpty()) {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("fail");
			message.setMessage("데이터 조회 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		// 둘다 데이터 있을 때
		else {
		
			String request_titile = requestService.interferenceDetailResult(dto).get(0).request_title;
			String version_interference_ = requestService.interferenceDetailResult(dto).get(0).version_interference;
			int final_interference = requestService.interferenceDetailResult(dto).get(0).final_interference;
			
			List<Double> xlist = new ArrayList<>();
			List<Double> ylist = new ArrayList<>();
			List<Double> zlist = new ArrayList<>();
			
			for(int i = 0; i < requestService.interferenceDetailLocation(request_map.get("version_interference")).size(); i++) {
								
				xlist.add(requestService.interferenceDetailLocation(request_map.get("version_interference")).get(i).x);
				ylist.add(requestService.interferenceDetailLocation(request_map.get("version_interference")).get(i).y);
				zlist.add(requestService.interferenceDetailLocation(request_map.get("version_interference")).get(i).z);
			}
			
			locationDTO.setX(xlist);
			locationDTO.setY(ylist);
			locationDTO.setZ(zlist);
			
			resultDTO.setFinal_interference(final_interference);
			resultDTO.setInterference_data(locationDTO);
			resultDTO.setRequest_title(request_titile);
			resultDTO.setVersion_interference(version_interference_);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setData(resultDTO);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}
		

	@ApiOperation(value="edc_nipa_req_013  ‘가상 시공 검토 자세히 보기’ 버튼", notes="API : 검토 결과 위험 지점 위치 정보 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "area_code", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_drawing", value = "String", dataTypeClass = String.class),
		@ApiImplicitParam(name = "version_virtual", value = "String", dataTypeClass = String.class)
	})
	@RequestMapping(value = "/VirtualDetailResult", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> VirtualDetailResult(@RequestBody Map<String, String> request_map) throws Exception {
		
		logger.info(time1 + " ( VirtualDetailResult )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Virtual_detailDTO dto = new Virtual_detailDTO();
		
		danger_data locationdto = new danger_data();
		
		Virtual_detail_result resultdto = new Virtual_detail_result();
		
		dto.setArea_code(request_map.get("area_code"));
		dto.setUser_id(request_map.get("user_id"));
		dto.setVersion_drawing(request_map.get("version_drawing"));
		dto.setVersion_virtual(request_map.get("version_virtual"));
		
		if(requestService.VirtualDetailResult(dto).isEmpty() && requestService.VirtualDetailLocation(request_map.get("version_virtual")).isEmpty()) {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("fail");
			message.setMessage("데이터 조회 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}else if(requestService.VirtualDetailLocation(request_map.get("version_virtual")).isEmpty()){
			
			String request_title = requestService.VirtualDetailResult(dto).get(0).request_title;
			String virtual_title = requestService.VirtualDetailResult(dto).get(0).virtual_title;
			int final_virtual = requestService.VirtualDetailResult(dto).get(0).final_virtual;
			String version_virtual_ = requestService.VirtualDetailResult(dto).get(0).version_virtual;
			
			
			resultdto.setRequest_title(request_title);
			resultdto.setVirtual_title(virtual_title);
			resultdto.setDanger_data(null);
			resultdto.setFinal_virtual(final_virtual);
			resultdto.setVersion_virtual(version_virtual_);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setData(resultdto);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		else if(requestService.VirtualDetailResult(dto).isEmpty()) {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("fail");
			message.setMessage("데이터 조회 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}else {
			
			String request_title = requestService.VirtualDetailResult(dto).get(0).request_title;
			String virtual_title = requestService.VirtualDetailResult(dto).get(0).virtual_title;
			int final_virtual = requestService.VirtualDetailResult(dto).get(0).final_virtual;
			String version_virtual_ = requestService.VirtualDetailResult(dto).get(0).version_virtual;
			
			List<Double> xlist = new ArrayList<>();
			List<Double> ylist = new ArrayList<>();
			List<Double> zlist = new ArrayList<>();
			List<String> locationlist = new ArrayList<>();
			List<Double> widthlist = new ArrayList<>();
			
			for(int i = 0; i < requestService.VirtualDetailLocation(request_map.get("version_virtual")).size(); i++) {
				
				xlist.add(requestService.VirtualDetailLocation(request_map.get("version_virtual")).get(i).x);
				ylist.add(requestService.VirtualDetailLocation(request_map.get("version_virtual")).get(i).y);
				zlist.add(requestService.VirtualDetailLocation(request_map.get("version_virtual")).get(i).z);
				locationlist.add(requestService.VirtualDetailLocation(request_map.get("version_virtual")).get(i).location);
				widthlist.add(requestService.VirtualDetailLocation(request_map.get("version_virtual")).get(i).width);
			}
			
			locationdto.setX(xlist);
			locationdto.setY(ylist);
			locationdto.setZ(zlist);
			locationdto.setLocation(locationlist);
			locationdto.setWidth(widthlist);
			
			resultdto.setFinal_virtual(final_virtual);
			resultdto.setDanger_data(locationdto);
			resultdto.setRequest_title(request_title);
			resultdto.setVirtual_title(virtual_title);
			resultdto.setVersion_virtual(version_virtual_);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setData(resultdto);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	/*
	@ApiOperation(value="무브먼츠 전처리 진행중 올린 도면 '확인버튼'", notes="도면 확인 시 업데이트" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "request_title", value = "String", required = true),
	})
	@RequestMapping(value = "/UpdateDrawingComplete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UpdateDrawingComplete(MultipartHttpServletRequest request, request_preprocessingDTO dto) throws Exception {
		
		logger.info(time1 + " ( UpdateDrawingComplete )");
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		

	}
	*/
}
