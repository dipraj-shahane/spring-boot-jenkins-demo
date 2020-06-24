package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	// message key from properties file
	private static final String HELLO_WORLD_MESSAGE_KEY = "hello.world.message";
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/hello-world/path-variable/{name}") // http://localhost:8080/hello-world/path-variable/Dipraj
	public String helloWorldPathVariable(@PathVariable(name = "name", required = true) String name) {
		return String.format("Hello %s", name);
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean");
	}
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized(Locale locale) {
		return messageSource.getMessage(HelloWorldController.HELLO_WORLD_MESSAGE_KEY, null, locale);
	}
	
	@GetMapping("/message-locale")
	public HelloWorldBean helloWorldBeanWithLocaleAsParam(Locale locale) {
		return new HelloWorldBean(messageSource.getMessage(HelloWorldController.HELLO_WORLD_MESSAGE_KEY, null, locale));
	}
	
	@GetMapping("/message-locale-context-holder")
	public HelloWorldBean helloWorldBeanWithLocaleFromLocaleContextHolder() {
		return new HelloWorldBean(messageSource.getMessage(HelloWorldController.HELLO_WORLD_MESSAGE_KEY, null, LocaleContextHolder.getLocale()));
	}
	
	@GetMapping("/hello-world/{title}") // http://localhost:8080/hello-world/Mr.?firstname=Dipraj&surname=Shahane
	public String helloWorld(@PathVariable(name = "title") String title,
			@RequestParam(name = "firstname", required = false, defaultValue = "World") String firstname,
			@RequestParam(name = "lastname", required = false, defaultValue = "!") String lastname) {
		return String.format("Hello %s %s", title, firstname);
	}
}
