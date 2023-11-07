package com.meta.service;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.dao.UserDAO;
import com.meta.dao.UserVerificationDAO;
import com.meta.dto.UserAdminDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserLoginDTO;
import com.meta.dto.User_verificationDTO;
import com.meta.util.JwtUtil;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	UserVerificationDAO userverificationDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtutill;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	@Override
	public ResponseEntity<Message> UserLogin(UserDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		//HttpSession session = request.getSession();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		String bcryPwd = loginCheck(dto.getUser_id());
		
		List<UserDTO> result = loginSuccess(dto.getUser_id());
		

		Integer numtest = OverlapID(dto.getUser_id());
		
		UserLoginDTO loginDTO = new UserLoginDTO();
		
		if(numtest == 0) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("로그인 정보가 정확하지 않습니다.");
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
		Date test = result.get(0).expiration_datetime;
		
		//계정만료일 전
		if(test.compareTo(time) > 0){
			
			//if(BCrypt.checkpw(user_pw, bcryPwd)) 
			if (passwordEncoder.matches(dto.getUser_pw(), bcryPwd)) {
				
				//session.setAttribute("login", userService.loginSuccess(user_id));
				
				loginDTO.setToken(jwtutill.createToken(result));
				loginDTO.setPassword_change(result.get(0).password_change);
				loginDTO.setSuper_admin(result.get(0).super_admin);
				loginDTO.setData_admin(result.get(0).data_admin);
				loginDTO.setCompany_admin(result.get(0).company_admin);
				
				message.setResult("success");
				message.setStatus(StatusEnum.OK);
				message.setMessage("로그인 성공");
				message.setStatus(StatusEnum.OK);
				message.setError_code(ErrorEnum.NONE);
				message.setData(loginDTO);
				
				//session.setAttribute("company_name", result.get(0).company_name);
				//session.setAttribute("user_id", result.get(0).user_id);

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
		//계정만료 후
		else if(test.compareTo(time) < 0) {
			
			IdExpiration(dto.getUser_id());
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("계정 만료일이 지났습니다.");
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);

				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		else {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("로그인 정보가 정확하지 않습니다.");
			message.setResult("fail");
			message.setError_code(ErrorEnum.NONE);
				
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		
			
		}
		
	}
	
	@Override
	public ResponseEntity<Message> UpdatePW(UserDTO dto) throws Exception {
	
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
		
			String bcryPwd = userDAO.loginCheck(dto.getUser_id());
			
			if(passwordEncoder.matches(dto.getTemp_pw(), bcryPwd)) {
			
				dto.setUser_id(dto.getUser_id());
				
				String RebcryPwd = passwordEncoder.encode(dto.getUser_pw());
				
				dto.setUser_pw(RebcryPwd);
			
				userDAO.TemporaryPW(dto);
				userDAO.UserTemptoryUpdate0(dto);
				
				message.setResult("success");
				message.setMessage("비밀번호변경 성공");
				message.setStatus(StatusEnum.OK);
				message.setData(null);
				message.setError_code(ErrorEnum.NONE);
				
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}else {
			
				message.setResult("fail");
				message.setMessage("비밀번호 변경 실패");
				message.setStatus(StatusEnum.BAD_REQUEST);
				message.setData(null);
				message.setError_code(ErrorEnum.NONE);
				
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}
			
			
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("비밀번호 변경 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		
	}
	
	@Override
	public ResponseEntity<Message> TemporaryPW(UserDTO dto) throws Exception {
	
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	
		StringBuffer newWord = new StringBuffer();
		
		Integer User_Count = userDAO.OverlapID(dto.getUser_id());
		
		//이메일 존재할 시
		if(User_Count >= 1) {
			
		    Random random = new Random();
		    int length = random.nextInt(5)+5;

		    for (int i = 0; i < length; i++) {
		        int choice = random.nextInt(3);
		        switch(choice) {
		            case 0:
		                newWord.append((char)((int)random.nextInt(25)+97));
		                break;
		            case 1:
		                newWord.append((char)((int)random.nextInt(25)+65));
		                break;
		            case 2:
		                newWord.append((char)((int)random.nextInt(10)+48));
		                break;
		            default:
		                break;
		        }
		    }
			
		    String TempPw = newWord.toString();
			
		    String bcryPwd = passwordEncoder.encode(TempPw);
		    
			SimpleMailMessage Mailmessage = new SimpleMailMessage();
			Mailmessage.setTo(dto.getUser_id());
				        
	        Mailmessage.setFrom("lsh.mv@movements.kr");
	        Mailmessage.setSubject("임시 비밀번호 메일 전송");
	        Mailmessage.setText("임시 비밀번호 : " + TempPw);

	        
	        javaMailSender.send(Mailmessage);

	        dto.setUser_id(dto.getUser_id());
		    dto.setUser_pw(bcryPwd);
		    
		    userDAO.TemporaryPW(dto);
			userDAO.UserTemptoryUpdate1(dto);
		    
		    message.setResult("success");
			message.setMessage("메일 전송 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		//아닐 떄
		else{
			
			message.setResult("fail");
			message.setMessage("확인할 수 없는 이메일입니다.");
			message.setStatus(StatusEnum.NOT_FOUND);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
		}
		
	}
	
	@Override
	public ResponseEntity<Message> RegisterUser(UserDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			String chk = dto.getUser_pw();
			
			String bcryPwd = passwordEncoder.encode(chk);
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
			cal.add(Calendar.YEAR, 5);
			
			dto.setCompany_name(dto.getCompany_name());
			dto.setUser_id(dto.getUser_id());
			dto.setUser_pw(bcryPwd);
			dto.setExpiration_datetime(cal.getTime());
			
			userDAO.RegisterUser(dto);
			
			message.setResult("success");
			message.setMessage("회원 가입 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setMessage("회원 가입 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);

		}
	}

	@Override
	public ResponseEntity<Message> MailChk(User_verificationDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		//이게 DB에 있는 인증코드
		String ChkData = userverificationDAO.MailChk(dto.getUser_id());
		
		if(dto.getVerification_code().equals(ChkData)) {
			
			userverificationDAO.UpdateStatus(dto.getVerification_code());
			
			message.setResult("success");
			message.setStatus(StatusEnum.OK);
			message.setMessage("인증 되었습니다.");
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
        	message.setResult("fail");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("인증 코드가 불일치 합니다. 인증코드 재전송을 통하여 코드를 변경하세요");
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<Message> MailCodeSend(User_verificationDTO dto) throws Exception {
		
		Message message = new Message();

		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Random random = new Random();
		
		String key = "";
		
		SimpleMailMessage Mailmessage = new SimpleMailMessage();
		Mailmessage.setTo(dto.getUser_id());
		
        int numIndex = random.nextInt(999999)+10000;

        key +=numIndex;
        
        Mailmessage.setFrom("lsh.mv@movements.kr");
        Mailmessage.setSubject("인증번호 입력을 위한 메일 전송");
        Mailmessage.setText("인증 번호 : " + key);
        
        try {

            javaMailSender.send(Mailmessage);

            dto.setUser_id(dto.getUser_id());
            dto.setVerification_code(key);
            
            userverificationDAO.MailCodeSend(dto);
        	
            message.setResult("success");
			message.setStatus(StatusEnum.OK);
			message.setMessage("인증코드가 발송 되었습니다");
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

            
        } catch(Exception e) {
        	
        	message.setResult("fail");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("인증코드 발송이 실패 하였습니다. 입력한 아이디를 확인 하고 인증코드를 재전송 하세요");
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
		
	}
	
	@Override
	public Integer OverlapID(String user_id) throws Exception {
		return userDAO.OverlapID(user_id);
	}
	
	@Override
	public String loginCheck(String user_id) throws Exception {
		return userDAO.loginCheck(user_id);
	}
	
	@Override
	public ResponseEntity<Message> UserRegisterCancel(String user_id) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			userDAO.UserRegisterCancel(user_id);
			
			userverificationDAO.DeleteVerifiaction(user_id);
			
			message.setStatus(StatusEnum.OK);
			message.setMessage("가입 취소 성공");
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("가입 취소 실패");
			message.setResult("fail");			
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);

		}

	}
	
	@Override
	public List<UserDTO> loginSuccess(String user_id) throws Exception {
		return userDAO.loginSuccess(user_id);
	}
	
	@Override
	public void IdExpiration(String user_id) throws Exception {
		userDAO.IdExpiration(user_id);
	}
}
