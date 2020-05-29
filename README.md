# easyShiro
使用shiro+springboot+mybatis实现简单的登录和权限管理

1.用户调用login接口登录
2.未登录用户不可访问接口
3.已登录用户校验是否拥有访问接口的权限

#### error list
1.如果某些Bean提前被实例化，它就很有可能不能被所有的`BeanPostProcessor处理到了
因此会报 * is not eligible for getting processed by all BeanPostProcessors
可以加上@Lazy注解解决

