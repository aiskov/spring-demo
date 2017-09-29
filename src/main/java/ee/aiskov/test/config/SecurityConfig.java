package ee.aiskov.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request -> {
                    return new CorsConfiguration().applyPermitDefaultValues();
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
                });

        http
                .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**", "/api/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");
    }

//i21> 2nd way to introduce CORS - some more strict configuration than "invert default" and "open all"
//@ Bean
//CorsConfigurationSource corsConfigurationSource() {
//	CorsConfiguration configuration = new CorsConfiguration();
//	configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
//	configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	source.registerCorsConfiguration("/**", configuration);
//	return source;
//}
//


//i21> 3rd way to inttoduce CORS - by filter as specified here "27.5 Filter based CORS support" -
// - https://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }


}