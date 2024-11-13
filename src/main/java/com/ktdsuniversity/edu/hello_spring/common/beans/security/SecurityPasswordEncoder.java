package com.ktdsuniversity.edu.hello_spring.common.beans.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktdsuniversity.edu.hello_spring.common.beans.Sha;

/**
 * SpringSecurity 인증 절차에서 사용자가 입력한 비밀번호와 데이터베이스 내에 존재하는 암호화된 비밀번호를 비교하는 역할 수행
 * 
 * AuthorizationFilter -> AuthorizationManager -> AuthorizationProvider -> 호출
 */
public class SecurityPasswordEncoder extends Sha implements PasswordEncoder {
	
	private String salt;
	
	/**
	 * 데이터베이스의 salt 값을 할당
	 * -> 암호화에 필요하기 때문
	 * -> 같은 salt로 암호화 해야 같은 결과가 나온다
	 * @param salt 인증을 요청한 사용자의 salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 사용자가 인증 요청한 비밀번호를 암호화 한다
	 * -> 데이터베이스의 암호화된 비밀번호와 일치하는지 확인하기 위해서
	 * @param rawPassword 사용자가 인증 요청한(입력한) 비밀번호
	 * @return 암호화된 사용자가 요청한 비밀번호
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return super.getEncrypt(String.valueOf(rawPassword), this.salt);
	}

	/**
	 * 암호화된 사용자의 비밀번호와 데이터베이스의 암호화된 비밀번호와 일치하는지 확인
	 * @param rawPassword 사용자가 인증 요청한(입력한) 비밀번호. (암호화 되기 전)
	 * @param encodedPassword 데이터베이스의 암호화된 비밀번호
	 * @return 비밀번호가 일치하는가?
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String password = this.encode(rawPassword); // 사용자가 입력한 비밀번호를 암호화
		return password.equals(encodedPassword);
	}
}
