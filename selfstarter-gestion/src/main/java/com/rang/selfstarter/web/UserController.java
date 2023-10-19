package com.rang.selfstarter.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rang.selfstarter.entity.Role;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.helper.JWTHelper;
import com.rang.selfstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.rang.selfstarter.constant.JWTUtil.AUTH_HEADER;
import static com.rang.selfstarter.constant.JWTUtil.SECRET;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    private JWTHelper jwtHelper;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean checkIfEmailExists(@RequestParam(name = "email", defaultValue = "") String email){
         return userService.loadUserByEmail(email) != null;

    }

    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if(jwtRefreshToken != null){
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
            String email = decodedJWT.getSubject();
            User user = userService.loadUserByEmail(email);
            String accessToken = jwtHelper.generateAccessToken(user.getEmail(), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), jwtHelper.getTokensMap(accessToken,jwtRefreshToken));
        }else {
            throw new RuntimeException("Refresh token required");
        }

    }
}
