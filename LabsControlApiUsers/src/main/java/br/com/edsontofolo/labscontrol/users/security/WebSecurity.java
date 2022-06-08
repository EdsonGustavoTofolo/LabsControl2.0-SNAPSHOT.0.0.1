package br.com.edsontofolo.labscontrol.users.security;

import br.com.edsontofolo.labscontrol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurity {

    private final AuthenticationConfiguration authConfig;
    private final Environment env;
    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(AuthenticationConfiguration authConfig, Environment env, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authConfig = authConfig;
        this.env = env;
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http.csrf().disable()
                .headers().frameOptions().disable().and() // Para funcionar o console do H2Database
                .authorizeRequests(authz -> authz
                        .antMatchers("/api/users/**").hasIpAddress(env.getProperty("gateway.ip")).and()
                        .addFilter(getAuthenticationFilter())
                );
        return httpSecurity.build();
    }

    private AuthenticationFilter getAuthenticationFilter() {
        try {
            AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, env, authConfig.getAuthenticationManager());
            authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
            return authenticationFilter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }

}
