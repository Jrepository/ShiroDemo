package nfha.com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class ShiroDemoTest {

	@Before
	public void shiroBefor(){
		
	}
	@Test
	public void ShiroDemoTest(){
		//
		DefaultSecurityManager sm = new DefaultSecurityManager();
		SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
		simpleAccountRealm.addAccount("zyy","111111");
		sm.setRealm(simpleAccountRealm);
		SecurityUtils.setSecurityManager(sm);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zyy", "111111");
		subject.login(token);
		subject.isAuthenticated();
		System.out.println(subject.isAuthenticated());
		
	}
}
