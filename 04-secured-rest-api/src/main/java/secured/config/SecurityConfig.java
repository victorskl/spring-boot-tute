package secured.config;

// If Spring Security is on the classpath, then web applications are secured by default.
// Therefore, at simplest, just adding starter-security in pom.xml, the app is already secured
// with spring boot auto-config security setting.

// To see this back, just do block /* */ comment this whole class

///*

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
public class SecurityConfig {

  private final DataSource dataSource;

  @Autowired
  public SecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // 84.1 Switch off the Spring Boot Security Configuration
  // If you define a @Configuration with a WebSecurityConfigurerAdapter in your application,
  // it switches off the default webapp security settings in Spring Boot.
  // https://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html

  // Here we use two WebSecurityConfigurerAdapter: one for direct access to /api, another one for the web
  // suppose you also serve some basic web pages on top of api service.
  // https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/jc.html#multiple-httpsecurity

  @Configuration
  @Order(1)
  public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable(); // FIXME should not do this in Production
      http
          .antMatcher("/api/**")
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic()
          //
          // It is better to get rid of Browser's default Basic Auth login pop-up dialog.
          // Just return 401 header, if the connection is unauthorized or session has expired.
          // This is especially important to avoid confusion, if there is a Single-page Application
          // (SPA) frontend UI serve along with the API backend web resources.
          //
          // https://stackoverflow.com/questions/33801468/how-let-spring-security-response-unauthorizedhttp-401-code-if-requesting-uri-w
          //
          .and().exceptionHandling()
          //.authenticationEntryPoint(new Http401AuthenticationEntryPoint("headerValue")) // before 2.0
          .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
       ;
    }
  }

  @Configuration
  @Order(2)
  public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      // required for h2-console
      http.headers().frameOptions().disable(); // FIXME should not do this in Production
      http.csrf().disable(); // FIXME should not do this in Production

      http
          .authorizeRequests()
          .antMatchers("/h2-console/**").permitAll()
          .antMatchers("/css/**").permitAll()
          .antMatchers("/**").authenticated()
          .and()
          .formLogin()
          .and()
          .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
          .and()
          .sessionManagement().maximumSessions(-1).sessionRegistry(sessionRegistry())
      ;
    }
  }

  // 84.2 Change the UserDetailsService and Add User Accounts
  // If you provide a @Bean of type AuthenticationManager, AuthenticationProvider, or UserDetailsService,
  // the default @Bean for InMemoryUserDetailsManager is not created, so you have the full feature set of
  // Spring Security available (such as various authentication options). The easiest way to add user accounts
  // is to provide your own UserDetailsService bean.
  // https://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html

  // https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/jc.html#jc-authentication-inmemory

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    String encodedPass = passwordEncoder().encode("tommy");
    // tommy $2a$10$spGU0VO7NbzA8Uj/BzapceaTc4vIUFM8iiNyj1NGgQLQqwgZqKPuG

//    auth
//        .inMemoryAuthentication()
//        .passwordEncoder(passwordEncoder())
//        .withUser("user").password(encodedPass).roles("USER");

    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder())

        // https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/htmlsingle/#user-schema

//        .withDefaultSchema()
//        .withUser("user").password(encodedPass).roles("USER").and()
//        .withUser("admin").password(encodedPass).roles("USER", "ADMIN")

        // using custom schema

        .usersByUsernameQuery(
            "select username, password, enabled from users where username=?")
        .authoritiesByUsernameQuery(
            "select users.username, roles.name from users left outer join roles_users on users.id = roles_users.user_id left outer join roles on roles.id = roles_users.role_id where users.username=?")
    ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }
}

//*/
