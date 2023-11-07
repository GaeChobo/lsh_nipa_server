package com.meta.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.dto.FacilityDTO;
import com.meta.service.FacilityService;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FacilityController {
	
	@Autowired
	FacilityService facilityService;

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
	
	@ApiOperation(value="시설물 데이터 리스트 출력", notes="시설물 데이터 리스트 출력" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/FacilityTypeList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")	
	public ResponseEntity<Message> FacilityTypeList() throws Exception {
		
		logger.info(time1 + " ( FacilityTypeList )");
		
		return facilityService.FacilityTypeList();
	
	}
	
	@ApiOperation(value="csv 데이터 업로드", notes="요청 후 전처리 데이터 파일 업로드" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "owner_user_id", value = "String", required = true),
		@ApiImplicitParam(name = "version_drawing", value = "String", required = true),
		@ApiImplicitParam(name = "ExcelFile", value = "File", required = true),
	})
	@RequestMapping(value = "/CsvDataUpload", method = RequestMethod.POST, produces = "multipart/form-data")	
	public ResponseEntity<Message> CsvDataUpload(MultipartHttpServletRequest request, @RequestParam String owner_user_id, @RequestParam String version_drawing) throws Exception {
		
		logger.info(time1 + " ( CsvDataUpload )");
		
		FacilityDTO dto = new FacilityDTO();
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<MultipartFile> files = request.getFiles("ExcelFile");
		
		System.out.println(version_drawing);
		
		
		
		if(version_drawing == "") {
			String null_version_drawing = null;
			
			dto.setVersion_drawing(null_version_drawing);
		}else {
			
			dto.setVersion_drawing(version_drawing);
		}
		
		
		dto.setOwner_user_id(owner_user_id);
		
		for(int j = 0; j < files.size(); j++) {
			
			InputStreamReader isr = new InputStreamReader(files.get(j).getInputStream());
				
			BufferedReader br = new BufferedReader(isr);
				
			String line;
				
			List<String> excelContext = new ArrayList<String>();
				
			while((line = br.readLine()) != null) {

				String[] temp = line.split("\t"); // 탭으로 구분
					
				for(int i=0; i<temp.length; i++) {

					excelContext.add(line);
				}
			}
				
				
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
					
					dto.setArea_code(AreaList.get(i));
					dto.setGroup_no(GroupList.get(i));
					dto.setOrder(OrderList.get(i));
					dto.setX1(XList.get(i));
					dto.setY1(YList.get(i));
					dto.setEl1(ELList.get(i));
					dto.setName(NameList.get(i));
					dto.setWidth(WidthList.get(i));
					dto.setHeight(HeightList.get(i));
					dto.setDiameter(DiameterList.get(i));
					dto.setLat1(LatList.get(i));
					dto.setLon1(LonList.get(i));
					dto.setType(TypeList.get(i));
					dto.setCategory(CategoryList.get(i));
					dto.setObject_key(Object_key);

					try {
						
						facilityService.CsvDataUpload(dto);
						
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
					
					dto.setArea_code(AreaList.get(i));
					dto.setName(HoleIdList.get(i));
					dto.setX1(XList.get(i));
					dto.setY1(YList.get(i));
					dto.setHole_depth(HoleDepthList.get(i));
					dto.setEl1(ELList.get(i));
					dto.setWidth(WidthList.get(i));
					dto.setHeight(HeightList.get(i));
					dto.setDiameter(DiameterList.get(i));
					dto.setLat1(LatList.get(i));
					dto.setLon1(LonList.get(i));
					dto.setType(TypeList.get(i));
					dto.setCategory(CategoryList.get(i));
					dto.setVersion_drawing(version_drawing);
					dto.setObject_key(Object_key);
					
					try {
						
						facilityService.CsvDataUpload(dto);
						
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
				//여기는 다이크 등등
			else if(excelContext.get(0).contains("real Z")) {
				
				
				List<String> AreaList = new ArrayList<String>();
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
					
					AreaList.add(TotalList[0]);
					
					int Group_no = (int) Double.parseDouble(TotalList[1]);
					GroupList.add(Group_no);
					
					int Order = (int) Double.parseDouble(TotalList[2]);
					OrderList.add(Order);
					
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
						ZList.add(0.0);
					}else {
						Double Z = (Double) Double.parseDouble(TotalList[5]);
						ZList.add(Z);
					}

					if(TotalList[6].equals("")) {
						RealZList.add(0.0);
					}else {
						Double RealZ = (Double) Double.parseDouble(TotalList[6]);
						RealZList.add(RealZ);
					}
					
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
				
				for (int i = 0; i < AreaList.size(); i++) {
				
					String Object_key = AreaList.get(i)+"_"+XList.get(i)+"_"+YList.get(i)+"_"+ZList.get(i);
					
					dto.setArea_code(AreaList.get(i));
					dto.setGroup_no(GroupList.get(i));
					dto.setOrder(OrderList.get(i));
					dto.setX1(XList.get(i));
					dto.setY1(YList.get(i));
					dto.setZ1(ZList.get(i));
					dto.setReal_z1(RealZList.get(i));
					dto.setWidth(WidthList.get(i));
					dto.setHeight(HeightList.get(i));
					dto.setLon1(LonList.get(i));
					dto.setLat1(LatList.get(i));
					dto.setType(TypeList.get(i));
					dto.setCategory(CategoryList.get(i));
					dto.setObject_key(Object_key);

					try {
						
						facilityService.CsvDataUpload(dto);
						
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
					
				List<String> AreaList = new ArrayList<String>();
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
					
					AreaList.add(TotalList[0]);
					
					int Order = (int) Double.parseDouble(TotalList[1]);
					OrderList.add(Order);
					
					
					if(TotalList[2].equals("")) {
						X1List.add(0.0);
						
					}else {
						Double X1 = (Double) Double.parseDouble(TotalList[2]);
						X1List.add(X1);
					}
					
					if(TotalList[3].equals("")) {
						Y1List.add(0.0);
						
					}else {
						Double Y1 = (Double) Double.parseDouble(TotalList[3]);
						Y1List.add(Y1);
					}
					
					if(TotalList[4].equals("")) {
						ZList.add(0.0);
						
					}else {
						Double Z = (Double) Double.parseDouble(TotalList[4]);
						ZList.add(Z);
					}
					
					if(TotalList[5].equals("")) {
						X2List.add(0.0);
						
					}else {
						Double X2 = (Double) Double.parseDouble(TotalList[5]);
						X2List.add(X2);
					}
					
					if(TotalList[6].equals("")) {
						Y2List.add(0.0);
						
					}else {
						Double Y2 = (Double) Double.parseDouble(TotalList[6]);
						Y2List.add(Y2);
					}
					
					if(TotalList[7].equals("")) {
						Lon1List.add(0.0);
						
					}else {
						Double Lon1 = (Double) Double.parseDouble(TotalList[7]);
						Lon1List.add(Lon1);
					}
					
					if(TotalList[8].equals("")) {
						Lat1List.add(0.0);
						
					}else {
						Double Lat1 = (Double) Double.parseDouble(TotalList[8]);
						Lat1List.add(Lat1);
					}
					
					if(TotalList[9].equals("")) {
						Lon2List.add(0.0);
						
					}else {
						Double Lon2 = (Double) Double.parseDouble(TotalList[9]);
						Lon2List.add(Lon2);
					}
					
					if(TotalList[10].equals("")) {
						Lat2List.add(0.0);
						
					}else {
						Double Lat2 = (Double) Double.parseDouble(TotalList[10]);
						Lat2List.add(Lat2);
					}
					
					int Type = (int) Double.parseDouble(TotalList[11]);
					TypeList.add(Type);

					int Category = (int) Double.parseDouble(TotalList[12]);
					CategoryList.add(Category);
				}
				
				for (int i = 0; i < AreaList.size(); i++) {

					String Object_key = AreaList.get(i)+"_"+X1List.get(i)+"_"+Y1List.get(i)+"_"+ZList.get(i);
					
					dto.setArea_code(AreaList.get(i));
					dto.setOrder(OrderList.get(i));
					dto.setX1(X1List.get(i));
					dto.setY1(Y1List.get(i));
					dto.setZ1(ZList.get(i));
					dto.setX2(X2List.get(i));
					dto.setY2(Y2List.get(i));
					dto.setLon1(Lon1List.get(i));
					dto.setLat1(Lat1List.get(i));
					dto.setLon2(Lon2List.get(i));
					dto.setLat2(Lat2List.get(i));
					dto.setType(TypeList.get(i));
					dto.setCategory(CategoryList.get(i));
					dto.setObject_key(Object_key);
					
					try {
						
						facilityService.CsvDataUpload(dto);
						
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
		message.setMessage("데이터 업로드 성공");
		message.setData(null);
			
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
}
