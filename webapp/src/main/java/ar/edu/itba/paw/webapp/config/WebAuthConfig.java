package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.constants.Roles;
import ar.edu.itba.paw.webapp.auth.CustomAuthenticationFailureHandler;
import ar.edu.itba.paw.webapp.auth.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    private static final String defaultKey = "f59ee49b6c6685dacb42b2f95c92ec0c5f67b0077c2fd531fa551f922f43";


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    private String getSSLKey() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("/openSSLKey/key.txt");
        if (inputStream == null) {
            //devolvemos clave default hay un problema al leer el archivo.
            return defaultKey;
        }
        Scanner scanner = new Scanner(inputStream);
        StringBuilder s = new StringBuilder();
        while (scanner.hasNextLine()) {
            s.append(scanner.next());
        }
        return s.toString();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .and().authorizeRequests()
                .antMatchers("/deleteUserRecipe/**", "/recipeDetail/likeComment/*" , "/recipeDetail/dislikeComment/*", "editRecipe/**").hasAnyRole(Roles.USER.getRole(), Roles.ADMIN.getRole())
                .antMatchers("/", "/recipeImage/**", "/recipeDetail/**","/userImage/**").permitAll()
                .antMatchers("/login", "/register", "/forgotPassword", "/resetPassword/**",
                        "/verifyAccount/**", "/verifyAccountSend", "/invalidResetPasswordToken", "/invalidVerifyToken","/accountVerified",
                        "/loginNotVerified", "/resendVerification", "/resetPasswordRequest").anonymous()// de aca para abajo solo se pide autenticacion (y no un rol en particular)
                .antMatchers("/deleteRecipeAdmin/**", "/deleteRecipeComment/**").hasRole(Roles.ADMIN.getRole())
                .antMatchers("/**").authenticated()
                .and().formLogin().failureHandler(authenticationFailureHandler)
                .loginPage("/login")
                // Le especifico como se llaman los parametros de username y password en el form:
                .usernameParameter("email").passwordParameter("password")
                // En el caso de login exitoso, vamos a redirigir al url "/":
                .defaultSuccessUrl("/", false)
                // El false es del parámetro "alwaysUse", que dice si tiene que forzar ese redirect o si puede ir a
                // otro lado. Por ej, intentaste acceder a /post/review pero te mando a login y despues volvemos
                // Podemos configurar para que se acuerde las sesiones:
                .and().rememberMe()
                // Especificamos que lo haga en base a un parametro "rememberme" en el formulario de login:
                .rememberMeParameter("rememberme")
                // Especificamos el UserDetailsService a utilizar. Usamos un PawUserDetailsService inyectado arriba:
                .userDetailsService(userDetailsService)
                .key(getSSLKey()) //
                // Especificamos la cantidad de tiempo en segundos que dura el remember me:
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                // Ahora vamos a configurar el logout:
                .and().logout()
                // La URL para hacer logout es "/logout":
                .logoutUrl("/logout")
                // Y cuando el logout se hace correctamente redirigimos a "/login":
                .logoutSuccessUrl("/login")
                // Ahora vamos a configurar el manejo de exceptions:
                .and().exceptionHandling()
                // En el caso de una excepción, redirigí a la página de access denied, que es "/errors/403":
                .accessDeniedPage("/error404")
                // Deshabilitar las reglas de cross-site-request-forgery. No explicaron por qué lo necesitamos:
                .and().csrf().disable();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico");
    }
}
