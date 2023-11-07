package com.meta.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meta.dto.AreaDTO;
import com.meta.dto.AssginUserDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.dto.CctvDTO;
import com.meta.dto.CompanyDTO;
import com.meta.dto.DefaultSensorDTO;
import com.meta.dto.HomeDataRetrunDTO;
import com.meta.dto.MarkerDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserResponseDTO;
import com.meta.dto.UserSendDTO;
import com.meta.service.AdminService;
import com.meta.service.AssignmentService;
import com.meta.service.CctvService;
import com.meta.service.CompanyService;
import com.meta.service.MarkerService;
import com.meta.service.SensorService;
import com.meta.vo.ErrorEnum;
import com.meta.vo.HomeMessage;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;
import com.meta.vo.UserMessage;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	SensorService sensorService;
	
	@Autowired
	AssignmentService assignmentService;

	@Autowired
	CctvService cctvService;
	
	@Autowired
	MarkerService markerService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@ApiOperation(value="cctv 어드민 검색 조회", notes="전체 조회는 기존 있던거 , 이건 검색용으로만 사용 팝업검색" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cctv_no", value = "cctv넘버")
	})
	@RequestMapping(value = "/AdminCctvSearch", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminCctvSearch(@RequestBody @ApiIgnore CctvDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminCctvSearch )");
 
		return cctvService.AdminCctvSearch(dto);
	}
	
	@ApiOperation(value="어드민 센서 조회 및 검색", notes="공백값의 경우 전체 조회" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sensor_name", value = "센서명"),
		@ApiImplicitParam(name = "area_code", value = "지역코드")
	})
	@RequestMapping(value = "/AdminSensorSearch", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminSensorSearch(@RequestBody @ApiIgnore DefaultSensorDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminSensorSearch )");
 
		return sensorService.AdminSensorSearch(dto);
	}
	
	@ApiOperation(value="발주처 검색 및 조회", notes="공백값의 경우 전체 조회" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "owner_name", value = "발주처")
	})
	@RequestMapping(value = "/OwenerList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> OwenerList(@RequestBody @ApiIgnore AreaDTO dto) throws Exception {
		
		logger.info(time1 + " ( OwenerList )");
 
		return assignmentService.OwenerList(dto);
	}
	
	@ApiOperation(value="Marker 정보 수정", notes="마커 정보 수정" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역 코드"),
		@ApiImplicitParam(name = "zone_name", value = "구역명"),
		@ApiImplicitParam(name = "section_name", value = "섹션명"),
		@ApiImplicitParam(name = "marker_detail", value = "마커 설명"),
		@ApiImplicitParam(name = "marker_no", value = "마커 NO"),
		@ApiImplicitParam(name = "x", value = "마커 좌표X"),
		@ApiImplicitParam(name = "y", value = "마커 좌표Y"),
		@ApiImplicitParam(name = "z", value = "마커 좌표Z")
	})
	@RequestMapping(value = "/AdminMarkerUpdate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerUpdate(@RequestBody MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerUpdate )");
		 
		return markerService.AdminMarkerUpdate(dto);
	}

	
	@ApiOperation(value="Marker 이미지 등록", notes="마커 이미지 등록" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "zone_name", value = "String"),
		@ApiImplicitParam(name = "section_name", value = "String"),
		@ApiImplicitParam(name = "marker_detail", value = "String"),
		@ApiImplicitParam(name = "x", value = "double"),
		@ApiImplicitParam(name = "y", value = "double"),
		@ApiImplicitParam(name = "z", value = "double")
	})
	@RequestMapping(value = "/AdminMarkerRegister", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerRegister(@RequestBody @ApiIgnore MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerRegister )");
 
		return markerService.AdminMarkerRegister(dto);
	}
	
	@ApiOperation(value="Marker이미지 삭제", notes="마커 이미지 삭제" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "marker_no", value = "마커 NO값")
	})
	@RequestMapping(value = "/AdminMarkerImgDelete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerImgDelete(@RequestBody @ApiIgnore MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerImgDelete )");
 
		return markerService.AdminMarkerImgDelete(dto);
	}
	
	@ApiOperation(value="Marker이미지 다운로드", notes="마커 이미지 다운로드" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "marker_no", value = "마커 NO값")
	})
	@RequestMapping(value = "/AdminMarkerImgDownLoad", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerImgDownLoad(HttpServletResponse response, @RequestParam String marker_no) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerImgDownLoad )");
 
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		String path = markerService.AdminMarkerImgDownLoad(marker_no);
		
		Path filename = Paths.get(path);
		
		byte[] fileByte = FileUtils.readFileToByteArray(new File(path));
		
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename.getFileName().toString(), "UTF-8")+"\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    response.getOutputStream().write(fileByte);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    
		message.setResult("success");
		message.setError_code(ErrorEnum.NONE);
		message.setStatus(StatusEnum.OK);
		message.setMessage("성공");
		message.setData(null);
			
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	@ApiOperation(value="신규 고객의 이메일 도메인 정보 추가", notes="Web에서 사용할 Admin기능" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_name", value = "회사명 영문"),
		@ApiImplicitParam(name = "company_name_kr", value = "회사명 국문"),
		@ApiImplicitParam(name = "domain_name", value = "회사 도메인")
	})
	@RequestMapping(value = "/RegisterDomain", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterDomain(@RequestBody CompanyDTO dto) throws Exception {
		
		logger.info(time1 + " ( RegisterDomain )");
		
		return companyService.RegisterDomain(dto);
	}

	@ApiOperation(value="메인 홈 데이터 로딩 (loadingHomeData)", notes="admin 메인 홈 데이터 로딩" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저_ID")
	})
	@RequestMapping(value = "/loadingHomeData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<HomeMessage>loadingHomeData(@RequestBody Map<String, String> user) throws Exception {
		
		logger.info(time1 + " ( loadingHomeData )");

		return adminService.loadingHomeData(user.get("user_id"));
	}
	
	@ApiOperation(value="Admin 센서 정보 조회", notes="admin 센서 정보 조회 ALL" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역"),
		@ApiImplicitParam(name = "zone_name", value = "ZONE"),
		@ApiImplicitParam(name = "section_name", value = "섹션"),
		@ApiImplicitParam(name = "sensor_name", value = "센서명"),
		@ApiImplicitParam(name = "model_name", value = "모델명"),
		@ApiImplicitParam(name = "detection_target", value = "감지 Type"),
		@ApiImplicitParam(name = "sensor_type", value = "센서 Type"),
		@ApiImplicitParam(name = "x", value = "x값"),
		@ApiImplicitParam(name = "y", value = "y값"),
		@ApiImplicitParam(name = "z", value = "z값"),
		@ApiImplicitParam(name = "threshold_value", value = "한계점"),
		@ApiImplicitParam(name = "manufacturing_date", value = "제조일"),
		@ApiImplicitParam(name = "installation_date", value = "가동일")
	})
	@RequestMapping(value = "/AdminSensorList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminSensorList() throws Exception {
		
		logger.info(time1 + " ( AdminSensorList )");

		return sensorService.AdminSensorList();
	}
	
	
	@ApiOperation(value="발주처 조회", notes="발주처 리스트" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/OwenerSelectione", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> OwenerSelectione() throws Exception {
		
		logger.info(time1 + " ( OwenerSelectione )");

		return adminService.OwenerSelectione();
	}
	
	@ApiOperation(value="CCTV 정보 조회", notes="admin CCTV 정보 조회 ALL" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역"),
	})
	@RequestMapping(value = "/AdminCCTVList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminCCTVList(@RequestBody @ApiIgnore CctvDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminCCTVList )");

		return cctvService.AdminCCTVList(dto);
	}
	
	@ApiOperation(value="마커 정보 조회", notes="admin 마커 정보 조회 ALL" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역"),
	})
	@RequestMapping(value = "/AdminMarkerList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerList(@RequestBody @ApiIgnore MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerList )");

		return markerService.AdminMarkerList(dto);
	}
	
	@ApiOperation(value="마커 검색", notes="admin 마커 검색" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "marker_no", value = "String"),
	})
	@RequestMapping(value = "/AdminMarkerSearch", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerSearch(@RequestBody @ApiIgnore MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerSearch )");

		return markerService.AdminMarkerSearch(dto);
	}
	
	@ApiOperation(value="마커 삭제", notes="admin 마커 삭제" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "marker_no", value = "String")
	})
	@RequestMapping(value = "/AdminMarkerDelete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AdminMarkerDelete(@RequestBody @ApiIgnore MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( AdminMarkerDelete )");

		return markerService.AdminMarkerDelete(dto);
	}
	
	@ApiOperation(value="admin 카드형식으로 표현되는 고객사 정보 데이터", notes="고객사 메뉴의 화면을 셋팅할 데이터 항목 제공하고 화면의 카드 형식으로 회사 데이터를 표현" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/loadingCustomerData", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> loadingCustomerData() throws Exception {
		
		logger.info(time1 + " ( loadingCustomerData )");

		return adminService.loadingCustomerData();
	}
	
	@ApiOperation(value="admin 고객사 정보 수정", notes="카드로 표현되는 고객사 정보를 카드 우측 상단에 수정 버튼을 누르면 회사명(국문), 이메일 도메인 두 데이터를 업데이트 할 수 있음" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_name", value = "String"),
		@ApiImplicitParam(name = "company_name_kr", value = "String"),
		@ApiImplicitParam(name = "domain_name", value = "String")
	})
	@RequestMapping(value = "/updateCustomerData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> updateCustomerData(@RequestBody @ApiIgnore CompanyDTO dto) throws Exception {
		
		logger.info(time1 + " ( updateCustomerData )");

		return adminService.updateCustomerData(dto);
	}
	
	@ApiOperation(value="admin 고객사 등록", notes="(’신규 등록’→ 팝업 화면 ’사이트 생성’ 버튼)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_name", value = "String"),
		@ApiImplicitParam(name = "company_name_kr", value = "String"),
		@ApiImplicitParam(name = "domain_name", value = "String"),
		@ApiImplicitParam(name = "company_admin", value = "String")
	})
	@RequestMapping(value = "/createNewCustomer", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> createNewCustomer(@RequestBody @ApiIgnore CompanyDTO dto) throws Exception {
		
		logger.info(time1 + " ( createNewCustomer )");

		return adminService.createNewCustomer(dto);
	}
	
	@ApiOperation(value="admin 계정 중복 체크", notes="(’(’신규 등록’→ 팝업 화면 계정 ’중복 확인’ 버튼)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_admin", value = "String")
	})
	@RequestMapping(value = "/checkCompanyAdmin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> checkCompanyAdmin(@RequestBody @ApiIgnore CompanyDTO dto) throws Exception {
		
		logger.info(time1 + " ( checkCompanyAdmin )");

		return adminService.checkCompanyAdmin(dto);
	}
	
	
	@ApiOperation(value="고객사의 신규 사이트를 생성하고, 할당지역 테이블에 고객사 최고관리자 계정이 신규 사이트에 접속 할 수 있도록 메타버스 지역 정보를 등록함", notes="(고객사 정보 카드 → ‘> 사이트 관리’ 버튼 → 사이트 관리 → ‘신규 등록’ 버튼)" , produces = "multipart/form-data")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_admin", value = "고객사 최고 관리자 이메일 아이디"),
		@ApiImplicitParam(name = "construction_name", value = "사업명"),
		@ApiImplicitParam(name = "area_code", value = "현장 정보 코드"),
		@ApiImplicitParam(name = "area_type", value = "현장 타입 → (0 : 시공, 1: 플랜트, 2: 건물)"),
		@ApiImplicitParam(name = "area_address", value = "시공 위치"),
		@ApiImplicitParam(name = "construction_detail", value = "공사 내용"),
		@ApiImplicitParam(name = "path_img", value = "대표 이미지"),
		@ApiImplicitParam(name = "owner_name", value = "발주처(소유주)"),
		@ApiImplicitParam(name = "owner_contact", value = "발주처(소유주) 연락처")
	})
	@RequestMapping(value = "/createNewAreaInfo", method = RequestMethod.POST, produces = "multipart/form-data", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Message> createNewAreaInfo(@RequestParam String company_admin, 
			@RequestParam String construction_name, @RequestParam String area_code, @RequestParam int area_type, 
			@RequestParam String area_address, @RequestParam String construction_detail,
			@RequestPart MultipartFile path_img, @RequestParam String owner_name, @RequestParam String owner_contact) throws Exception {
		
		logger.info(time1 + " ( createNewAreaInfo )");
		
		return adminService.createNewAreaInfo(company_admin, construction_name, area_code, area_type, area_address, construction_detail, path_img, owner_name, owner_contact);
		
	}
	
	@ApiOperation(value="고객사 - 고객사→사이트 관리→사이트 리스트 ", notes="사이트 관리’ 버튼을 누르면 사이트 관리 페이지로 이동하고 이동한 페이지에 해당하는 데이터를 내려줌" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company_name", value = "고객사 영문명")
	})
	@RequestMapping(value = "/loadingAreaInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> loadingAreaInfo(@RequestBody @ApiIgnore AreaDTO dto) throws Exception {
		
		logger.info(time1 + " ( loadingAreaInfo )");
		
		return adminService.loadingAreaInfo(dto);
		
	}
	
	@ApiOperation(value="(카드안에 ‘수정’ ↔ ‘저장’ 버튼)", notes="카드로 표현되는 고객사 사이트 정보에서 카드 우측 상단에 수정 버튼" , produces = "multipart/form-data")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code ", value = "현장 시공 정보 코드"),
		@ApiImplicitParam(name = "construction_detail", value = "비고"),
		@ApiImplicitParam(name = "owner_name", value = "발주처(소유주)"),
		@ApiImplicitParam(name = "owner_contact", value = "발주처(소유주) 연락처"),
		@ApiImplicitParam(name = "supervisor_name", value = "감리단"),
		@ApiImplicitParam(name = "supervisor_contact", value = "감리단 연락처"),
		@ApiImplicitParam(name = "contractor_name", value = "시공사"),
		@ApiImplicitParam(name = "contractor_contact", value = "시공사 연락처"),
		@ApiImplicitParam(name = "period_start", value = "시공 시작일(착공일)"),
		@ApiImplicitParam(name = "period_end", value = "시공 종료(준공일)"),
		@ApiImplicitParam(name = "status", value = "현장 진행 상태 → (0 : 시공전, 1: 진행중, 2: 중단, 3 : 완료)"),
		@ApiImplicitParam(name = "facility_type", value = "시설 종류")
	})
	@RequestMapping(value = "/updateAreaInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> updateAreaInfo(@RequestBody HomeDataRetrunDTO dto) throws Exception {
		
		logger.info(time1 + " ( updateAreaInfo )");
		
		return adminService.updateAreaInfo(dto);
		
	}
	
	
	@ApiOperation(value="(카드안에 ‘대표이미지’ ↔ 수정)", notes="수정에서 대표이미지만 변경" , produces = "multipart/form-data")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code ", value = "현장 시공 정보 코드"),
		@ApiImplicitParam(name = "path_img ", value = "대표 이미지")
	})
	@RequestMapping(value = "/updateAreaImage", method = RequestMethod.POST, produces = "multipart/form-data", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Message> updateAreaImage(@RequestParam String area_code, @RequestPart MultipartFile path_img) throws Exception {
		
		logger.info(time1 + " ( updateAreaImage )");
		
		return adminService.updateAreaImage(path_img, area_code);
		
	}
	
	@ApiOperation(value="고객사 정보 카드 → ‘> 사이트 관리’ 버튼 → 사이트 관리 → ‘신규 등록’ 버튼 → 팝업 내 ‘중복 확인’ 버튼)", notes="입력한 사업명을 데이터 베이스 현장정보 테이블에서 기존 데이터와 중복되는지 확인 " , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "construction_name", value = "‘사업명’ 입력칸에 입력한 사업명 ")
	})
	@RequestMapping(value = "/checkConstructionName", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> checkConstructionName(@RequestBody @ApiIgnore AreaDTO dto) throws Exception {
		
		logger.info(time1 + " ( checkConstructionName )");
		
		return adminService.checkConstructionName(dto);
		
	}
	
	@ApiOperation(value="(해당 사이트(현장)를 사용 할 수 있는 유저들 데이터 표현)", notes="클라이언트는 해당 페이지로 진입시 이 API를 이용하여 데이터를 요청 하여 받고 받은 데이터로 화면을 생성" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역 코드"),
		@ApiImplicitParam(name = "user_id", value = "관리자 권한 아이디")
	})
	@RequestMapping(value = "/loadingUserData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<UserMessage> loadingUserData(@RequestBody @ApiIgnore AreaDTO dto) throws Exception {
		
		logger.info(time1 + " ( loadingUserData )");
		
		return adminService.loadingUserData(dto);
	}
	
	@ApiOperation(value="(해당 사이트(현장)를 사용 할 수 있는 유저들 데이터 표현) - 무브먼츠 전용", notes="- 무브먼츠 전용" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "지역 코드"),
		@ApiImplicitParam(name = "user_id", value = "관리자 권한 아이디")
	})
	@RequestMapping(value = "/MoveloadingUserData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<UserMessage> MoveloadingUserData(@RequestBody @ApiIgnore AreaDTO dto) throws Exception {
		
		logger.info(time1 + " ( MoveloadingUserData )");
		
		return adminService.MoveloadingUserData(dto);
	}
	
	
	@ApiOperation(value="(사용자 계정에서 권한 수정, 드랍다운 컴포넌트)", notes="해당 사이트를 사용할 수 있는 기존 유저들의 권한을 드랍다운 컴포넌트를 이용하여 권한 수정" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "현장(시공)정보 코드"),
		@ApiImplicitParam(name = "user_id", value = "권한을 수정한 계정의 이메일 형식의 아이디"),
		@ApiImplicitParam(name = "user_type", value = "수정된 권한")
		
	})
	@RequestMapping(value = "/chageUserType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> chageUserType(@RequestBody @ApiIgnore AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( chageUserType )");
		
		return adminService.chageUserType(dto);
	}
	
	@ApiOperation(value="(해당 사이트에서 유저를 할당을 해제함, x 버튼)", notes="이미 할당된 유저들을 해당 사이트를 사용하지 못하도록 할당 해제" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "현장(시공)정보 코드"),
		@ApiImplicitParam(name = "user_id", value = "권한을 수정한 계정의 이메일 형식의 아이디")
	})
	@RequestMapping(value = "/unassignUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> unassignUser(@RequestBody @ApiIgnore AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( unassignUser )");
		
		return adminService.unassignUser(dto);
	}
	
	@ApiOperation(value="(사용자 페이지 → ‘계정추가’ 버튼 → 팝업 내 ‘확인’ 버튼 )", notes="해당 사이트(현장)에 사용자를 추가하기 위하여 검색" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "입력한 아이디 (aaa@회사도메인), 찾으려는 아이디"),
		@ApiImplicitParam(name = "target_user_id", value = "관리자 아이디")
	})
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> searchUser(@RequestBody @ApiIgnore UserSendDTO dto) throws Exception {
		
		logger.info(time1 + " ( searchUser )");
		
		return adminService.searchUser(dto);
	}
	
	@ApiOperation(value="(사용자 페이지 →’계정추가’ 버튼 → 팝업 내 ‘계정 검색 필드 돋보기’)", notes="해당 사이트(현장)에 사용자를 추가하기 위하여 검색" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "현장(시공) 정보 코드"),
		@ApiImplicitParam(name = "company_admin", value = "고객사 최고 관리자 이메일 아이디"),
		@ApiImplicitParam(name = "user_id", value = "추가한 계정 아이디"),
		@ApiImplicitParam(name = "user_type", value = "접속 계정 권한 타입")
	})
	@RequestMapping(value = "/assignUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> assignUser (@RequestBody AssginUserDTO dto) throws Exception {
		
		logger.info(time1 + " ( assignUser )");
		
		return adminService.assignUser(dto);
	}
	
	@ApiOperation(value="고객사 정보 카드 → ‘> 사이트 관리’ 버튼 → 사이트 관리 → ‘신규 등록’ 버튼 → 팝업 내 현장 정보 코드 ‘중복 확인’ 버튼", notes="입력한 현장 정보 코드를 데이터 베이스 현장정보 테이블에서 기존 데이터와 중복되는지 확인 " , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "현장(시공) 정보 코드")
	})
	@RequestMapping(value = "/checkAreaCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> checkAreaCode (@RequestBody @ApiIgnore AssginUserDTO dto) throws Exception {
		
		logger.info(time1 + " ( checkAreaCode )");
		
		return adminService.checkAreaCode(dto);
	}
	
	
}
