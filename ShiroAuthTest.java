package com.shiroauth.Shiroauthtest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.ini.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.lang.util.Factory;


public class ShiroAuthTest {

    public static void main(String[] args) {


        Factory<SecurityManager> factory = new IniSecurityManagerFactory("claspath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();


        SecurityUtils.setSecurityManager(securityManager);


        Subject currentUser = SecurityUtils.getSubject();

 
        Session session = currentUser.getSession();
        session.setAttribute("key", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            System.out.println("Retrieved the correct value! [" + value + "]");
        }

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("arun", "password");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException e) {
            	System.out.println("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException e) {
            	System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            } 

            catch (AuthenticationException ae) {
            }
        }

        System.out.println("User [" + currentUser.getPrincipal() + "] logged in successfully.");


        currentUser.logout();

        System.exit(0);
    }
}

