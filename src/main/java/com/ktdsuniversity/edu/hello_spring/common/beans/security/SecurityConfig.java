package com.ktdsuniversity.edu.hello_spring.common.beans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ktdsuniversity.edu.hello_spring.member.dao.MemberDao;

@Configuration // Bean 설정을 위한 annotation
@EnableWebSecurity // Spring Security 활성화 (인증 절차를 위한)
public class SecurityConfig {

	@Autowired
	private MemberDao memberDao;
	
	// 1. SecurityUserDetailsService bean 등록
	@Bean
	UserDetailsService securityUserDetailsService() {
		return new SecurityUserDetailsService(this.memberDao);
	}
	
	// 2. SecurityPasswordEncoder bean 등록
	@Bean
	@Scope("prototype") // 필요할 때마다 새로운 instance를 생성시키는 annotation ("prototype")
	/*
	 * singleton -> 유일한 객체 -> Application (기준이 필요함. 어디에서 유일한가? 우리 application에서 유일한 객체)
	 * 유일한 객체를 만드는 방법 = singleton pattern
	 * Spring @Controller, @Service, @Repository, @Component -> 객체 생성 -> Bean Container -주입-> @Autowired : 이미 만들어진 객체를 사용하는 것이 빠름
	 * 
	 * prototype: 필요할 때마다 매번 생성 => 소멸(메모리 제거) <-> singleton: 한 번만 생성해서 계속 쓴다 => 유일한 객체
	 * PasswordEncoder에서 멤버변수로 salt를 줬다
	 * 만일 PasswordEncoder가 singleton instance였다면 유일한 instance라 Bean Container 자체에 salt가 계속해서 바뀐다.
	 * 굉장히 치명적인 결함을 갖는다
	 * 어떤 사용자가 자기 비밀번호를 올바르게 입력했는데도 틀렸다고 한다 (동시성 문제가 생김)
	 * web application 개발할 때 항상 주의해야 하는 것은 동시성 문제
	 * a와 입력하고 검증을 하는 중에 b가 들어오면 a는 사라짐 -> a는 올바르게 입력해도 실패함
	 * 그래서 동시성 문제를 야기할 수 있으면 반드시 prototype으로 바꿔줘야 한다. 동시에 여러 개의 instance가 생겨서 a만 가지고 있고 b만 가지고 있게 해야 한다.
	 */
	PasswordEncoder securityPasswordEncoder() {
		return new SecurityPasswordEncoder();
	}
	@Bean
	// 3. SecurityAuthenticationProvider bean 등록
	AuthenticationProvider securityAuthenticationProvider() {
		return new SecurityAuthenticationProvider(this.securityUserDetailsService(), this.securityPasswordEncoder());
	}
	
	@Bean
	AuthenticationFailureHandler loginFailureHandler() {
		return new LoginFailureHandler(this.memberDao);
	}
	
	@Bean
	AuthenticationSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler(this.memberDao);
	}
	
	/**
	 * Spring Security는 필터 기반의 프레임워크
	 * Spring Security Filter의 실행 순서를 정의 역할을 하는 메소드
	 * @param http 동작시킬 필터들의 전략 수립
	 * @return 필터들의 동작 순서를 반환
	 * @throws Exception 
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// form을 이용한 로그인 페이지의 인증 정책을 설정한다
		http.formLogin( formLogin ->
					formLogin.loginPage("/member/login") // Spring Security의 로그인 URL을 변경한다
							 .usernameParameter("email") // 로그인에 필요한 아이디 파라미터의 이름을 email로 적용
							 .passwordParameter("password") // 로그인에 필요한 비밀번호 파라미터의 이름을 password로 적용
							 // .defaultSuccessUrl("/board/list") // 로그인을 성공했을 때 이동할 페이지의 URL
							 .loginProcessingUrl("/member/security/login") // SecurityAuthenticationProvider를 실행시킬 URL
							 .failureHandler(this.loginFailureHandler())
							 .successHandler(this.loginSuccessHandler())
					);
		
		return http.build();
	}
}
