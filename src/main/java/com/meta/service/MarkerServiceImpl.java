package com.meta.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.meta.dao.MarkerDAO;
import com.meta.dto.MarkerDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class MarkerServiceImpl implements MarkerService {

	@Autowired
	MarkerDAO Markerdao;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();

	String time1 = format1.format(time);
	
	@Override
	public String AdminMarkerImgDownLoad(String marker_no) throws Exception {
		
		String Path = "/home/ubuntu/files/Qr_Code/"+marker_no+".png";
	
		return Path;
	}

	
	@Override
	public ResponseEntity<Message> AdminMarkerImgDelete(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {

			Markerdao.MarkerImgDelete(dto);
			
			message.setResult("success");
			message.setMessage("이미지 삭제 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("이미지 삭제 실패하였습니다. 다음에 다시 시도해주세요");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<Message> AdminMarkerSearch(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<MarkerDTO> result = Markerdao.AdminMarkerSearch(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("마커 정보 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("마커 정보 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
		

	@Override
	public ResponseEntity<Message> AdminMarkerList(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<MarkerDTO> result = Markerdao.AdminMarkerList(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("마커 정보 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("마커 정보 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	@Override
	public ResponseEntity<byte[]> MarkerImgFind(MarkerDTO dto) throws Exception {
		
		try {
			
			List<MarkerDTO> result = Markerdao.MarkerFindValue(dto.getMarker_no());
			
			String fileName = result.get(0).path_marker;
		
	        InputStream imageStream = new FileInputStream(fileName);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
			
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
		
		} catch(Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<Message> MarkerFindValue(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<MarkerDTO> result = Markerdao.MarkerFindValue(dto.getMarker_no());
		
		if(result.get(0).area_code == null) {
			
			message.setResult("fail");
			message.setMessage("해당하는 값 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(result);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}

	}
	
	@Override
	public ResponseEntity<Message> AdminMarkerRegister(MarkerDTO dto) throws Exception {
		
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			String path = ("/home/ubuntu/files/Qr_Code/");
			
			MarkerDTO markerdto = new MarkerDTO(); 
			
			String marker_no = dto.getZone_name()+"_"+dto.getSection_name()+"_"+"1";
			
			JsonObject obj = new JsonObject();
			
			obj.addProperty("area_code", dto.getArea_code());
			obj.addProperty("zone_name", dto.getZone_name());
			obj.addProperty("section_name", dto.getSection_name());
			obj.addProperty("maker_key", "1");
			obj.addProperty("marker_no", marker_no);
			obj.addProperty("marker_detail", dto.getMarker_detail());
			obj.addProperty("x", dto.getX());
			obj.addProperty("y", dto.getY());
			obj.addProperty("z", dto.getZ());
			
			//QR Code 컬러
			int qrcodeColor = 0xFF000000;
			
			//QR Code 배경컬러
			int backgroundColor = 0xFFFFFFFF;
			
			QRCodeWriter qrcodewriter = new QRCodeWriter();
			
			String codeurl = new String(obj.toString().getBytes("UTF-8"), "ISO-8859-1");
			
			//QR Code의 Width, Height 값
			BitMatrix bitMatrix = qrcodewriter.encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			String finalFile = path+marker_no+".png";
			
			String finalpath = "http://13.209.48.128:8080/Qr_Code/"+marker_no+".png";
			
			File savefile = new File(finalFile);
			
			ImageIO.write(bufferedImage, "png", savefile);
			
			markerdto.setArea_code(dto.getArea_code());
			markerdto.setZone_name(dto.getZone_name());
			markerdto.setSection_name(dto.getSection_name());
			markerdto.setMarker_key("1");
			markerdto.setPath_marker(finalpath);
			markerdto.setMarker_no(marker_no);
			markerdto.setMarker_detail(dto.getMarker_detail());
			markerdto.setX(dto.getX());
			markerdto.setY(dto.getY());
			markerdto.setZ(dto.getZ());
			
			Markerdao.MarkerRegister(markerdto);
			
			message.setResult("success");
			message.setMessage("AR마커 생성 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("AR마커 추가에 실패하였습니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}	
	}
	
	@Override
	public ResponseEntity<Message> AdminMarkerUpdate(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			Markerdao.MarkerUpdate(dto);
			
			message.setResult("success");
			message.setMessage("수정 성공하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("수정 실패하였습니다. 다음에 다시 시도해주세요");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> AdminMarkerDelete(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			MarkerDTO markerdto = new MarkerDTO();
			
			markerdto.setMarker_no(dto.getMarker_no());
			
			Markerdao.MarkerDelete(markerdto);
			
			message.setResult("success");
			message.setMessage("삭제 성공하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("삭제에 실패하였습니다. 다음에 다시 시도해주세요");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> MarkerRegister1(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		String path = ("/home/ubuntu/files/Qr_Code/");
				
		MarkerDTO markerdto = new MarkerDTO(); 
		
		String marker_no = dto.getZone_name()+"_"+dto.getSection_name()+"_"+dto.getMarker_key();
		
		JsonObject obj = new JsonObject();
		
		obj.addProperty("area_code", dto.getArea_code());
		obj.addProperty("zone_name", dto.getZone_name());
		obj.addProperty("section_name", dto.getSection_name());
		obj.addProperty("maker_key", dto.getMarker_key());
		obj.addProperty("marker_no", marker_no);
		obj.addProperty("marker_detail", dto.getMarker_detail());
		obj.addProperty("x", dto.getX());
		obj.addProperty("y", dto.getY());
		obj.addProperty("z", dto.getZ());
		
		//QR Code 컬러
		int qrcodeColor = 0xFF000000;
		
		//QR Code 배경컬러
		int backgroundColor = 0xFFFFFFFF;
		
		QRCodeWriter qrcodewriter = new QRCodeWriter();
		
		String codeurl = new String(obj.toString().getBytes("UTF-8"), "ISO-8859-1");
		
		//QR Code의 Width, Height 값
		BitMatrix bitMatrix = qrcodewriter.encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);
		
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
		
		BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
		
		String finalFile = path+marker_no+".png";
		
		File savefile = new File(finalFile);
		
		ImageIO.write(bufferedImage, "png", savefile);
		
		markerdto.setArea_code(dto.getArea_code());
		markerdto.setZone_name(dto.getZone_name());
		markerdto.setSection_name(dto.getSection_name());
		markerdto.setMarker_key(dto.getMarker_key());
		markerdto.setPath_marker(finalFile);
		markerdto.setMarker_no(marker_no);
		markerdto.setMarker_detail(dto.getMarker_detail());
		markerdto.setX(dto.getX());
		markerdto.setY(dto.getY());
		markerdto.setZ(dto.getZ());
		
		Markerdao.MarkerRegister(markerdto);
		
		message.setResult("success");
		message.setMessage("QR코드 생성 성공");
		message.setStatus(StatusEnum.OK);
		message.setData(null);
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	public ResponseEntity<Message> MarkerRegister2(MarkerDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		String path = ("/home/ubuntu/files/Qr_Code/");
				
		MarkerDTO markerdto = new MarkerDTO(); 
		
		String marker_no = dto.getZone_name()+"_"+dto.getSection_name()+"_"+dto.getMarker_key();
		
		//QR Code 컬러
		int qrcodeColor = 0xFF000000;
		
		//QR Code 배경컬러
		int backgroundColor = 0xFFFFFFFF;
		
		QRCodeWriter qrcodewriter = new QRCodeWriter();
		
		//QR Code의 Width, Height 값
		BitMatrix bitMatrix = qrcodewriter.encode(marker_no, BarcodeFormat.QR_CODE, 200, 200);
		
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
		
		BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
		
		String finalFile = path+marker_no+".png";
		
		File savefile = new File(finalFile);
		
		ImageIO.write(bufferedImage, "png", savefile);
		
		markerdto.setArea_code(dto.getArea_code());
		markerdto.setZone_name(dto.getZone_name());
		markerdto.setSection_name(dto.getSection_name());
		markerdto.setMarker_key(dto.getMarker_key());
		markerdto.setPath_marker(finalFile);
		markerdto.setMarker_no(marker_no);
		markerdto.setMarker_detail(dto.getMarker_detail());
		markerdto.setX(dto.getX());
		markerdto.setY(dto.getY());
		markerdto.setZ(dto.getZ());
		
		Markerdao.MarkerRegister(markerdto);
		
		message.setResult("success");
		message.setMessage("QR코드 생성 성공");
		message.setStatus(StatusEnum.OK);
		message.setData(null);
		message.setError_code(ErrorEnum.NONE);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	

}
