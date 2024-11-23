package com.bits.pilani.api_gateway.main;

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfiguration {

//	@Bean
//	public RouterFunction<ServerResponse> userServiceRouter() {
//		return RouterFunctions.route(RequestPredicates.path("/user-service"),
//				HandlerFunctions.http("http://localhost:8081"));
//	}
}
