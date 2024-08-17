package com.marche.marche.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.modele.request.LoginReq;
import com.marche.marche.modele.response.ErrorRes;
import com.marche.marche.modele.response.LoginRes;
import com.marche.marche.repository.UtilisateurRepository;

import jakarta.servlet.http.*;

@RestController
@RequestMapping("/rest/auth")
@CrossOrigin(origins = "*")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;
    private final UtilisateurRepository ur;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,UtilisateurRepository ur) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.ur = ur;
    }

    @SuppressWarnings("rawtypes")
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq loginReq) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(),
                            loginReq.getPassword()));
            String email = authentication.getName();
            Utilisateur user = ur.findByEmail(email);
            user.setAdmin(((List<? extends GrantedAuthority>)authentication.getAuthorities()).get(0).getAuthority());
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email, token);

            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // @GetMapping("/logout")
    // public void logout(HttpServletRequest request) {
    //     HttpSession session = request.getSession();
    //     session.invalidate();
    //     SecurityContext securityContext = SecurityContextHolder.getContext();
    //     securityContext.setAuthentication(null);
    // }

    // @GetMapping("/logout")
    // public void logout(HttpServletRequest request, HttpServletResponse response) {
    //     SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    //     logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    // }

    // @GetMapping("/logout")
    // public ResponseEntity<Void> logout(HttpServletRequest request) {
    //     HttpSession session = request.getSession(false);
    //     if (session != null) {
    //         session.invalidate();
    //     }
    //     SecurityContextHolder.clearContext();
    //     return ResponseEntity.ok().build();
    // }

}
