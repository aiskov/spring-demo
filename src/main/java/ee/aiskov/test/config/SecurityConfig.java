package ee.aiskov.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .configurationSource(request -> {
                    return new CorsConfiguration().applyPermitDefaultValues();
                });

        http
                .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**", "/api/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");
    }

//i21> 1st way to introduce CORS with some default options
//        http
//                .cors()
//                .configurationSource(request -> {
//                    return new CorsConfiguration().applyPermitDefaultValues();
//i21>
//public CorsConfiguration applyPermitDefaultValues()
//By default a newly created CorsConfiguration does not permit any cross-origin requests and must be configured explicitly to indicate what should be allowed.
//Use this method to flip the initialization model to start with open defaults that permit all cross-origin requests for GET, HEAD, and POST requests. Note however that this method will not override any existing values already set.
//
//The following defaults are applied if not already set:
//
//Allow all origins, i.e. "*".
//Allow "simple" methods GET, HEAD and POST.
//Allow all headers.
//Allow credentials.
//Set max age to 1800 seconds (30 minutes).
//source: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/cors/CorsConfiguration.html#applyPermitDefaultValues--
//                });

//i21> 2nd way to introduce CORS - some more strict configuration than "invert default" and "open all"
//public CorsFilter corsFilter() { //TODO,i21 filter based solution not working yet

//i21> 3rd way to inttoduce CORS - by filter as specified here "27.5 Filter based CORS support" -
// - https://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html

//i21> 4th way @CrossOrigin on the Controller or @CrossOrigin on a @RequestMapping-Annotated Handler Method
// source http://www.baeldung.com/spring-cors

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }


    @Bean
    public AuthenticationEntryPoint http401UnauthorizedEntryPoint() {
        return new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            }
        };
    }


//TODO,i21 filter based solution not working yet
//    http
//            .csrf()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .and()
//            .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class) /
//            .exceptionHandling()
//            .authenticationEntryPoint(http401UnauthorizedEntryPoint())
//        .and()
//
//    @Bean
//    public CorsFilter corsFilter() {
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Collections.singletonList("*"));  //TODO put trusted domain here
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        config.setAllowedHeaders(Collections.singletonList("*"));
//        config.setMaxAge(1800L);
//        config.setAllowCredentials(true);
//
//        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
//
//            source.registerCorsConfiguration("/api/**", config);
//        }
//        return new CorsFilter(source);
//    }


}