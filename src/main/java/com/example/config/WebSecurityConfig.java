package com.example.config;


import com.example.service.UsersRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;


//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig  {
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SessionRegistryImpl sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/main", "/register","/resources/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login").defaultSuccessUrl("/main")
//                        .permitAll()
//
//                )
//                .logout((logout) -> logout.permitAll().logoutSuccessUrl("/main"));
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager configuration(HttpSecurity https, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService uds) throws Exception {
//        return https.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(uds).passwordEncoder(bCryptPasswordEncoder)
//                .and().
//                build();
//    }
//
//
//}

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UsersRepoService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/register").not().fullyAuthenticated()
                //Доступ разрешен всем пользователей
                .antMatchers("/","/main","/main/**","/files", "/files/**","/uploadForm","/resources/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .logoutSuccessUrl("/login")
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}

