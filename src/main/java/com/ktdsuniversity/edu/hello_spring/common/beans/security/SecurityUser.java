package com.ktdsuniversity.edu.hello_spring.common.beans.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

/**
 * SpringSecurity로 인증할 사용자의 정보를 담고있는 객체.
 * SpringSecurity로 인증한 사용자의 정보를 담고있는 객체.
 * SpringSecurity가 사용할 클래스 --> 인증 수행
 * 
 * AuthorizationFilter -> AuthorizationManager -> AuthorizationProvider -> UserDatailsService -> 호출
 */
public class SecurityUser implements UserDetails {

	/**
	 * 로그인을 요청한 사용자의 권한 정보를 세팅
	 * -> 로그인 이후 해당 사용자의 권한 정보를 데이터베이스에서 조회한 후 권한 부여
	 */
	private static final long serialVersionUID = 653523424966037221L;
	
	/**
	 * UserDetailsService를 통해서 아이디(이메일)로 데이터베이스에서 조회된 결과를 가지고 있을 멤버변수
	 */
	private MemberVO memberVO;
	
	public SecurityUser(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	public String getSalt() {
		return this.memberVO.getSalt();
	}
	
	public MemberVO getMemberVO() {
		return this.memberVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 정보를 가지고 있는 interface => GrantedAuthority
		// GrantedAuthority를 구현한 권한 class => SimpleGrantedAuthority
		// SimpleGrantedAuthority List를 반환
		
		// 부여된 권한에 따라서 실행을 제어할 수 있다
		// 이 사용자는 CRUD 모두 가능한 권한을 가진 사용자
		return List.of(new SimpleGrantedAuthority("CREATE"),
					new SimpleGrantedAuthority("READ"),
					new SimpleGrantedAuthority("UPDATE"),
					new SimpleGrantedAuthority("DELETE"));
	}

	/**
	 * 로그인을 요청한 사용자의 비밀번호를 반환
	 */
	@Override
	public String getPassword() {
		return this.memberVO.getPassword();
	}

	/**
	 * 로그인을 요청한 사용자의 아이디(이메일)를 반환
	 */
	@Override
	public String getUsername() {
		return this.memberVO.getEmail();
	}

}
