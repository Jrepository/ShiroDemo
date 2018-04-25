package nfha.com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ShiroDemoTest {

	@Before
	public void shiroBefor(){
		
	}
	

	/**
	 * 认证
	 */
	@Test
	public void ShiroDemoTest1(){
		//
		DefaultSecurityManager sm = new DefaultSecurityManager();
		SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
		simpleAccountRealm.addAccount("test","111111");
		sm.setRealm(simpleAccountRealm);
		SecurityUtils.setSecurityManager(sm);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("test", "111111");
		subject.login(token);
		subject.isAuthenticated();
		System.out.println(subject.isAuthenticated());
		
	}
	
	/**
	 * 授权
	 */
	@Test
	public void ShiroDemoTest2(){
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		SimpleAccountRealm realm = new SimpleAccountRealm();
		realm.addAccount("test", "123456","admin","user");
		securityManager.setRealm(realm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");
		subject.login(token);
		
		boolean authenticated = subject.isAuthenticated();
		System.out.println(authenticated);
		
		subject.checkRoles("admin","user");
	}
	
	/**
	 * iniRealm
	 */
	@Test
	public void ShiroDemoTest3(){
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		IniRealm realm = new IniRealm("classpath:shiro.ini");
		securityManager.setRealm(realm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");
		subject.login(token);
		
		boolean authenticated = subject.isAuthenticated();
		System.out.println(authenticated);
		
		subject.checkRole("admin");
		
		subject.checkPermission("user:create");
	}
	
	/**
	 * IniSecurityManagerFactory
	 */
	@Test
	public void ShiroDemoTest4(){
			// 通过工厂，加载配置文件
			IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
			// 2、得到SecurityManager实例 并绑定给SecurityUtils 
			SecurityManager securityManager = factory.getInstance();
			SecurityUtils.setSecurityManager(securityManager);
			// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）  
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");
			subject.login(token);
	}
	
//	shiro认证的入门
//	复制代码
//	1. 因为现在没有提供Realm域，所以先自己手动提供配置文件
//	    * 创建shiro.ini的配置文件，写入如下代码
//	        [users]
//	        meimei=1234
	
//	2. 编写入门程序
	@Test
	public void ShiroDemoTest5(){
//	
		// 通过工厂，加载配置文件
	    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	    // 通过工厂获取到安全管理器
	    SecurityManager securityManager = factory.getInstance();
	    // 获取到subject对象，但是需要先注册工具
	    SecurityUtils.setSecurityManager(securityManager);
	    // 获取到subject对象
	    Subject subject = SecurityUtils.getSubject();
	    // 就可以认证了
	    AuthenticationToken token = new UsernamePasswordToken("test", "123456");
	    // 这就是认证
	    subject.login(token);
	    System.out.println("认证通过...");
//		
	}
	
	/**
	 * JdbcRealm
	 */
	@Test
	public void ShiroDemoTest6(){
		//数据库信息
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("para_admin");
		dataSource.setPassword("nfha_505");
		dataSource.setUrl("jdbc:mysql://192.168.0.161:3306/blcpara");
		dataSource.setServerName("192.168.0.161");
		//JdbcRealm
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);
		jdbcRealm.setAuthenticationQuery("select USEDT_NAME from used_to t where t.USEDT_CODE=?");
		//
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(jdbcRealm);
		//将securityManager设置到当前的环境中 
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		//创建token(认证令牌)
		SimpleHash hash = new SimpleHash("MD5", "1");//加密
		UsernamePasswordToken token = new UsernamePasswordToken("te","1");
		subject.login(token);
		 //是否认证通过  
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
	}
	
	
}
