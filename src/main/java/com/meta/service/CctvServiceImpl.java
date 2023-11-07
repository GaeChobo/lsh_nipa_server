package com.meta.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.dao.CctvDAO;
import com.meta.dto.CctvDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class CctvServiceImpl implements CctvService {

	@Autowired
	CctvDAO cctvDAO;
	
	@Override
	public void CCTVAllDelete() throws Exception {
		
		List<CctvDTO> result = cctvDAO.CCTVAllList();
		
		for(int i = 0; i < result.size(); i++ ) {
			
			String filepath = result.get(i).getPath_still_cut();
			
			File file = new File(filepath);
			
			if( file.exists() ){
	    		if(file.delete()){

	    		}else{

	    		}
	    	}else{

	    	}
		}
		
		cctvDAO.CCTVAllDelete();
	}
	
	@Override
	public ResponseEntity<Message> AdminCctvSearch(CctvDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		message.setResult("success");
		message.setMessage("cctv 입력 성공");
		message.setStatus(StatusEnum.OK);
		message.setData(cctvDAO.AdminCctvSearch(dto));
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Message> AdminCCTVList(CctvDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<CctvDTO> result = new ArrayList<CctvDTO>();
		
		List<CctvDTO> cctv1 = cctvDAO.CCTV_NO1_Find(dto);
		
		List<CctvDTO> cctv2 = cctvDAO.CCTV_NO2_Find(dto);
		
		List<CctvDTO> cctv3 = cctvDAO.CCTV_NO3_Find(dto);
		
		List<CctvDTO> cctv4 = cctvDAO.CCTV_NO4_Find(dto);
		
		result.addAll(0, cctv1);
		
		result.addAll(1, cctv2);
		
		result.addAll(2, cctv3);
		
		result.addAll(3, cctv4);
		
		message.setResult("success");
		message.setMessage("cctv 조회 성공");
		message.setStatus(StatusEnum.OK);
		message.setData(result);
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
		

	}
	
	@Override
	public ResponseEntity<byte[]> CCTVImageFindList(String cctv_no) throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {

			String fileName = cctvDAO.CCTVImageFindList(cctv_no);
			
	        InputStream imageStream = new FileInputStream(fileName);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
			
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
		}
		

        catch(Exception e) {
        	
        	
        	return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
	}
	
	@Override
	public ResponseEntity<Message> RegisterCCTV(MultipartHttpServletRequest request) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		CctvDTO dto = new CctvDTO();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Date time2 = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(time2);
		
		cal.add(Calendar.HOUR, 9);
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd_HHmmss");
		
		MultipartFile file = request.getFile("file");
		
		String CCTV_FILE = file.getOriginalFilename();
		
		String path = "";
		
		String cctv_number;
		
		double dx,dy,dz;
		

		
		if(CCTV_FILE.contains("CCTV_NO1")) {
			
			path = ("/home/ubuntu/files/cctv/CCTV_NO1/");
			
			cctv_number = "CCTV_NO1";
			
			dx = 0;
			dy = 0;
			dz = 0;
			
		}
		else if(CCTV_FILE.contains("CCTV_NO2")) {
			
			path = ("/home/ubuntu/files/cctv/CCTV_NO2/");
			
			cctv_number = "CCTV_NO2";
			
			dx = 1;
			dy = 1;
			dz = 1;
		}
		else if(CCTV_FILE.contains("CCTV_NO3")) {
			
			path = ("/home/ubuntu/files/cctv/CCTV_NO3/");
			
			cctv_number = "CCTV_NO3";
			
			dx = 2;
			dy = 2;
			dz = 2;
		}
		else if(CCTV_FILE.contains("CCTV_NO4")) {
			
			path = ("/home/ubuntu/files/cctv/CCTV_NO4/");
			
			cctv_number = "CCTV_NO4";
			
			dx = 3;
			dy = 3;
			dz = 3;
		}
		else {
			
			message.setResult("fail");
			message.setMessage("파일이름이 형식에 맞지않음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.FILE_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
		String time1 = format2.format(cal.getTime());
		
		String realpath = path+time1;
		
		File Folder= new File(realpath);

		Folder.mkdirs();

		File saveFile;
		
		saveFile = new File(realpath+"/"+file.getOriginalFilename());
			
		String fileName = "";
		
		fileName = realpath+"/"+file.getOriginalFilename();

		file.transferTo(saveFile);
		
		String zone_name = "ZONE1";
		String section_name = "SECTION8";
		String area_code = "mk_factory_ansan_1";
		
		String cctv_no = zone_name+"_"+section_name+"_"+cctv_number;

		String x = String.valueOf(dx);
		String y = String.valueOf(dy);
		String z = String.valueOf(dz);
		
		String object_key = area_code+"_"+x+"_"+y+"_"+z;
		
		dto.setArea_code(area_code);
		dto.setSection_name(section_name);
		dto.setZone_name(zone_name);
		dto.setX(dx);
		dto.setY(dy);
		dto.setZ(dz);
		dto.setPath_still_cut(fileName);
		dto.setCctv_no(cctv_no);
		dto.setObject_key(object_key);
		
		cctvDAO.RegisterCCTV(dto);
	
		
		message.setResult("success");
		message.setMessage("cctv 입력 성공");
		message.setStatus(StatusEnum.OK);
		message.setData(null);
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
}
