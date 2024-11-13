package com.ktdsuniversity.edu.hello_spring.common.beans.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ktdsuniversity.edu.hello_spring.member.dao.MemberDao;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

/**
 * Spring Security에 인증을 요청한 사용자의 정보를 조회하는 역할
 * 
 * 아이디(이메일: UserDetails.getUserName())로만 데이트베이스에서 사용자의 정보를 조회한다
 * 비밀번호 확인은 다른 클래스의 역할
 * AuthorizationFilter -> AuthorizationManager -> AuthorizationProvider -> 호출
 */
public class SecurityUserDetailsService implements UserDetailsService{

	/**
	 * 사용자 정보를 조회할 DAO
	 */
	private MemberDao memberDao;
	
	public SecurityUserDetailsService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	/**
	 * 데이터베이스에서 사용자의 정보를 조회
	 * 
	 * @param username 인증을 요청한 사용자의 아이디(이메일)
	 * @return UserDetails interface를 구현한 사용자 정보 객체
	 * @throws UsernameNotFoundException username으로 조회한 결과가 null일 때 Spring Security에게 던질 예외
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberVO memberVO = this.memberDao.selectMemberByEmail(username);
		
		if(memberVO == null) {
			// UserDetailsService에서 예외가 던져지면
			// AuthenticationProvider에서 예외를 처리한다
			throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		// UserDetails interface 타입의 class로 계정 정보를 전달한다
		// SecurityUser is a UserDetails
		return new SecurityUser(memberVO);
	}

}
