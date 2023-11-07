package com.meta.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.UserDTO;
import com.meta.dto.User_verificationDTO;
import com.meta.service.CompanyService;
import com.meta.service.UserService;
import com.meta.service.UserVerificationService;
import com.meta.util.JwtUtil;
import com.meta.vo.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"req_001, req_002 유저 컨트롤러"})
@RequestMapping("/api")
public class UserController {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserVerificationService userverificationService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtutill;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	JavaMailSender javaMailSender;

	@ApiOperation(value="임시 비밀번호 입력 및 비밀번호변경", notes="임시 비밀번호 입력 및 비밀번호변경" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID @이메일"),
		@ApiImplicitParam(name = "user_pw", value = "변경할 비밀번호값"),
		@ApiImplicitParam(name = "temp_pw", value = "임시 비밀번호")
	})
	@RequestMapping(value = "/UpdatePW", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UpdatePW(@RequestBody UserDTO dto) throws Exception {

		logger.info(time1 + " ( UpdatePW )");

		
		return userService.UpdatePW(dto);
	}
	
	@ApiOperation(value="임시 비밀번호 이메일 전송", notes="이메일 전송" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID @이메일")
	})
	@RequestMapping(value = "/TemporaryPWSend", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> TemporaryPWSend(@RequestBody UserDTO dto) throws Exception {

		logger.info(time1 + " ( TemporaryPWSend )");

		
		return userService.TemporaryPW(dto);
	}
	
	
	@ApiOperation(value="req_001 회사 이메일 도메인 리스트 제공", notes="1. 회사 도메인 리스트(https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/CompanyDomainList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> CompanyDomainList() throws Exception {

		logger.info(time1 + " ( CompanyDomainList )");

		return companyService.CompanyDomainList();
	}
	
	@ApiOperation(value="req_001 ID 중복검사 및 회사 도메인 검사", notes="2. 아이디 중복 및 회사 이름(https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID @이메일"),
		@ApiImplicitParam(name = "domain_name", value = "회사도메인"),
	})
	@RequestMapping(value = "/OverlapID", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> OverlapID(@RequestBody Map<String, String> user_map) throws Exception {
		
		
		logger.info(time1 + " ( OverlapID )");
		
		return companyService.OverlapID(user_map.get("user_id"), user_map.get("domain_name"));
	}
	
	@ApiOperation(value="req_001 회원가입", notes="4. 회원 가입 완료(https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID - @이메일"),
		@ApiImplicitParam(name = "user_pw", value = "유저Password"),
		@ApiImplicitParam(name = "company_name", value = "회사명"),
	})
	@RequestMapping(value = "/RegisterUser", method = RequestMethod.POST ,produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterUser(@RequestBody UserDTO dto) throws Exception {
		
		logger.info(time1 + " ( RegisterUser )");
		
		System.out.println(dto.getUser_id());
		System.out.println(dto.getUser_pw());
		
		return userService.RegisterUser(dto);
			
	}

	@ApiOperation(value="req_001 회원가입 시 이메일 인증 확인", notes="3.이메일 인증 c (https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID - @이메일"),
		@ApiImplicitParam(name = "verification_code", value = "이메일에서 받은 인증코드"),
	})
	@RequestMapping(value = "/MailChk", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> MailChk(@RequestBody User_verificationDTO dto) throws Exception {
		
		logger.info(time1 + " ( MailChk )");
		
		return userService.MailChk(dto);
	}

	@ApiOperation(value="req_001 회원가입 시 이메일 전송", notes="3.이메일 인증 a,b (https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "인증메일 받을 이메일 ID")
	})
	@RequestMapping(value = "/MailCodeSend", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> MailCodeSend(@RequestBody User_verificationDTO dto) throws Exception {
		
		logger.info(time1 + " ( MailCodeSend )");
			
		return userService.MailCodeSend(dto);
	}


	@ApiOperation(value="req_002 유저 로그인", notes="4.로그인 (https://www.notion.so/req_002-226325750715413a837b8e1957f1d0fc)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "로그인 할 이메일 ID"),
		@ApiImplicitParam(name = "user_pw", value = "로그인 할 이메일 passoword")
	})
	@RequestMapping(value = "/UserLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UserLogin(@RequestBody UserDTO dto) throws Exception {
		
		logger.info(time1 + " ( UserLogin )");
		
		return userService.UserLogin(dto);
	}

	@ApiOperation(value="req_001 회원가입 취소", notes="5.회원 가입 취소 (https://www.notion.so/req_001-f7335189fc124610981194f906225bd0)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "회원가입 취소 할 이메일 ID")
	})
	@RequestMapping(value = "/UserRegisterCancel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> UserRegisterCancel(@RequestBody Map<String, String> user_map) throws Exception {
		
		logger.info(time1 + " ( UserRegisterCancel )");
		
		System.out.println(user_map.get("user_id"));
		
		return userService.UserRegisterCancel(user_map.get("user_id"));
	}
}
