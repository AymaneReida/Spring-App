package net.reida.springmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /* @Autowired
    private DataSource dataSource; */
    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("******************************************");
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println("******************************************");
        /* auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
        auth.inMemoryAuthentication().withUser("user1").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("USER", "ADMIN"); */
        /* Pour le cas ou les utilisateurs sont stockés dans une base la même base de données de l’application
         */
        /* auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/
       auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        // http.formLogin();
        // http.httpBasic();
        http.authorizeRequests().antMatchers("/save**/**", "delete**/**", "form**/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/patients**/**", "index**/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/user/**", "/login", "/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        // http.csrf();
        // http.csrf().disable();
        // http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
        // http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
        // http.exceptionHandling().accessDeniedPage("/notAuthorized");
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
