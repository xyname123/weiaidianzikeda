package com.vivedu.ckd.config;



import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // 用来定义 DispatcherServlet 应用上下文中的 bean、
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration() {
		FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new AuthenticationFilter());// 创建上面的自定义的WebFilter对象
		registration.addInitParameter("casServerLoginUrl", "http://idas.uestc.edu.cn/authserver/login");// 相当于web.xml中的<param-name>、<param-value>。可以添加n个
		registration.addInitParameter("serverName", "http://study.uestc.cn");
		registration.setName("CAS Authentication Filter");// 该Filter的名字，自己随便定义
		registration.addUrlPatterns("/caslogin");	//过滤的地址
		//registration.setOrder(1);// 启动时候的优先级
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter> cas20ProxyFilterRegistration() {
		FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new Cas20ProxyReceivingTicketValidationFilter()); // 创建上面的自定义的WebFilter对象
		registration.addInitParameter("casServerUrlPrefix", "http://idas.uestc.edu.cn/authserver"); // 相当于web.xml中的<param-name>、<param-value>。可以添加n个
		registration.addInitParameter("serverName", "http://study.uestc.cn");
		registration.addInitParameter("redirectAfterValidation", "true");
		registration.addInitParameter("encoding", "UTF-8");
		registration.setName("CAS Validation Filter");  // 该Filter的名字，自己随便定义
		registration.addUrlPatterns("/*");	//过滤的地址
		//registration.setOrder(1);// 启动时候的优先级
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<HttpServletRequestWrapperFilter> httpServletFilterRegistration() {
		FilterRegistrationBean<HttpServletRequestWrapperFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new HttpServletRequestWrapperFilter());
		registration.setName("CAS HttpServletRequest Wrapper Filter");
		registration.addUrlPatterns("/*");
		//registration.setOrder(1);// 启动时候的优先级
		return registration;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	}

}