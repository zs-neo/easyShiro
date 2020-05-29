# easyShiro

使用shiro+springboot+mybatis实现简单的登录和权限管理，使用自定义权限注解实现权限管理。

数据库表user

| 字段名   | 示例 |
| -------- | ---- |
| id       | 1    |
| username | 1    |
| password | 1    |

数据库表auth

| 字段名     | 示例    |
| ---------- | ------- |
| id         | 1       |
| username   | 1       |
| role       | user    |
| permission | user:hi |

#### 快速上手

- 建立数据库

- 配置数据库连接

  ```
  server.port=8080
  #shiro
  shiro.enabled=true
  shiro.rememberMe.cookie.name=rememberMe
  shiro.rememberMe.cookie.maxAge=3600000
  #mysql
  spring.datasource.url=jdbc:mysql://127.0.0.1:3306/authdb?characterEncoding=utf8&useSSL=true&serverTimezone=UTC
  spring.datasource.username=root
  spring.datasource.password=123456
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.max-idle=10
  spring.datasource.max-wait=1000
  spring.datasource.min-idle=5
  spring.datasource.initial-size=5
  ```

- 设计对应的查询用户，查询权限接口

  ```java
  @Mapper
  public interface AuthMapper {
  	
  	@Select("select * from auth where username=#{name}")
  	List<Auth> getUserAuth(@Param("name") String name);
  	
  }
  ```

  ```java
  @Mapper
  public interface UserMapper {
  	
  	@Select("select * from user where username=#{name}")
  	User getUsersByName(@Param("name") String name);
  	
  }
  ```

- 拷贝support包到你的启动类同级目录下

- 启动类加注解

  ```java
  @SpringBootApplication
  @ServletComponentScan
  @ComponentScan(basePackages = {"com.example.demo.*"})
  public class DemoApplication {
  	
  	public static void main(String[] args) {
  		SpringApplication.run(DemoApplication.class, args);
  	}
  	
  }
  ```

- 新建一个controller

  ```java
  @RestController
  @RequestMapping("/api")
  public class DemoController {
  	
  	@Autowired
  	private UserService userService;
  	
  	@GetMapping("/hello")
  	@PermissionCheck("user:hello")
  	public String hello() {
  		return "hello";
  	}
  	
  	@GetMapping("/hi")
  	@PermissionCheck("user:hi,demo:good,demo:test")
  	public String hi() {
  		return "hi";
  	}
  	
  	@GetMapping("/ok")
  	@PermissionCheck("user:ok")
  	public String ok() {
  		return "ok";
  	}
  	
  	@GetMapping("/login")
  	public String login(@RequestParam String username, @RequestParam String password) {
  		try {
  			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
  			Subject currentUser = SecurityUtils.getSubject();
  			if (!currentUser.isAuthenticated()) {
  				token.setRememberMe(true);
  				currentUser.login(token);
  			}
  			return "suc";
  		} catch (UnknownAccountException uae) {
  			throw new GlobalException(CodeMsg.UNKONWN_ACCOUNT_ERROR);
  		} catch (LockedAccountException lae) {
  			throw new GlobalException(CodeMsg.LOCKED_ACCOUNT_ERROR);
  		} catch (ExcessiveAttemptsException eae) {
  			throw new GlobalException(CodeMsg.ATTEMPT_ACCOUNT_ERROR);
  		} catch (AuthenticationException ae) {
  			throw new GlobalException(CodeMsg.ACCOUNT_ERROR);
  		} catch (Exception e) {
  			throw new GlobalException(CodeMsg.ACCOUNT_ERROR);
  		}
  	}
  	
  	@RequestMapping(value = "/logout")
  	public String logout() {
  		SecurityUtils.getSubject().logout();
  		return "login";
  	}
  	
  }
  ```

- 运行项目吧！试着执行下面这几个url！

  ```
  http://localhost:8080/api/login?username=1&password=1      #登录成功以后执行下面的
  http://localhost:8080/api/hello							   #无权限
  http://localhost:8080/api/hi							   #有权限，访问成功
  http://localhost:8080/api/logout						   #退出登录
  http://localhost:8080/api/hello							   #跳转到login.html
  ```



#### 其他

1.用户调用login接口登录，get请求传过去明文username和password

2.未登录用户不可访问接口，会被跳转到login.html页面

3.已登录用户校验是否拥有访问接口的权限，如果有对应权限（或关系）就可以访问对应的接口

4.自定义的权限注解，多个资源用逗号连接即可，此处逻辑预留扩展性，可以自己拓展@PermissionCheck("user:hi,demo:good,demo:test")

5.过滤器GlobalFilter->（preHandle->postHandle->afterCompletion）均可自定义处理逻辑，扩展功能

#### error list

1.如果某些Bean提前被实例化，它就很有可能不能被所有的`BeanPostProcessor处理到了因此会报 * is not eligible for getting processed by all BeanPostProcessors可以加上@Lazy注解解决，目前不知道有没有更好的解决方法

2.bean的注入可以通过applicationcontext获取