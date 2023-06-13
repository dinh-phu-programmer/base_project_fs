//package com.samsung.project.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.Arrays;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        //logic for authentication
//        String userName = authentication.getName();
//        String password = String.valueOf(authentication.getCredentials());
//        Connection connection = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        try {
//            connection = dataSource.getConnection();
//            stmt = connection.prepareStatement("select username, password, enabled from users where username = ?");
//            stmt.setString(1, userName);
//            rs = stmt.executeQuery();
//            rs.next();
//            if (rs.getString(1).equals(userName) && rs.getString(2).equals(password))
//                return new UsernamePasswordAuthenticationToken(userName, password, Arrays.asList());
//            else throw new AuthenticationCredentialsNotFoundException("Error in Authentication");
//
////            if ("john".equals(userName) && "123".equals(password))
////                return new UsernamePasswordAuthenticationToken(userName, password, Arrays.asList());
////            else
////                throw new AuthenticationCredentialsNotFoundException("Error in Authentication");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//}
