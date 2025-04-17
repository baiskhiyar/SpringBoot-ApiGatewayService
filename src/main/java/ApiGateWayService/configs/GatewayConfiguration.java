//package ApiGateWayService.configs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfiguration {
//
//    @Autowired
//    private JwtValidationFilter jwtValidationFilter;
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-service-route", r -> r.path("/authService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/authService"))
//
//                .route("user-service-route", r -> r.path("/authService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/userService"))  // Or the direct URL
//
//                .route("product-service-route", r -> r.path("/productService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/productService"))
//
//                // Add more routes as needed
//                .build();
//    }
//}
//package ApiGateWayService.configs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfiguration {
//
//    @Autowired
//    private JwtValidationFilter jwtValidationFilter;
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-service-route", r -> r.path("/authService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/authService"))
//
//                .route("user-service-route", r -> r.path("/authService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/userService"))  // Or the direct URL
//
//                .route("product-service-route", r -> r.path("/productService/**")
//                        .filters(f -> f.filter(jwtValidationFilter))
//                        .uri("http://localhost:8080/spring/productService"))
//
//                // Add more routes as needed
//                .build();
//    }
//}
