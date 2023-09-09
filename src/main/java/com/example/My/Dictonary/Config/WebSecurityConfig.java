package com.example.My.Dictonary.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.My.Dictonary.Auth.CustomOAuth2UserService;
import com.example.My.Dictonary.Security.CustomSuccessHandler;
import com.example.My.Dictonary.Services.MemberService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MemberService memberService;

	@Autowired
	CustomOAuth2UserService customOAuth2UserService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		web.ignoring().antMatchers("/photo/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().frameOptions().sameOrigin();
		
		http.csrf().disable()//
				.authorizeRequests() //
				.antMatchers("/main/**").hasRole("MEMBER") //
				.antMatchers("/fragment/**").hasRole("MEMBER") //
				.anyRequest().permitAll(); //

		http.formLogin() //
				.loginPage("/") //
				.defaultSuccessUrl("/main") //
				.successHandler(successHandler()) //
				.usernameParameter("id") //
				.passwordParameter("pw") //
				.failureUrl("/");

		http.logout() //
				.logoutSuccessUrl("/") //
				.invalidateHttpSession(true); //

		http.oauth2Login() //
				.defaultSuccessUrl("/main") //
				.userInfoEndpoint() //
				.userService(customOAuth2UserService);

//		http.exceptionHandling().//
//				accessDeniedPage("/");

	}

	// processing login
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEnconder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomSuccessHandler();
	}

}
