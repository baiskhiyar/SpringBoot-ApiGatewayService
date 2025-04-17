//package ApiGateWayService.configs;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import ApiGateWayService.services.RedisService;
//
//import java.security.PublicKey;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component("JwtValidationFilter")
//public class JwtValidationFilter implements GatewayFilter, Ordered {
//
//    @Autowired
//    private RedisService redisService;
//
//    private final PublicKeyProvider publicKeyProvider;
//
//    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // For path matching
//
//    @Autowired
//    public JwtValidationFilter(PublicKeyProvider publicKeyProvider) {
//        this.publicKeyProvider = publicKeyProvider;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getPath().value();
//
//        // Check if the path is excluded
//        if (isExcluded(path)) {
//            return chain.filter(exchange); // Skip JWT validation
//        }
//
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        String token = authHeader.substring(7);
//        // Check if the token is in Redis cache
//        Jws<Claims> cachedClaims = (Jws<Claims>) redisService.get(token);
//        if (cachedClaims != null) {
//            // Token is valid and cached, forward the request
//            return forwardRequest(exchange, chain, cachedClaims);
//        } else {
//            // Validate the token
//            try {
//                Jws<Claims> claims = parseAndValidateToken(token);
//                // Store the claims in Redis cache
//                redisService.save(token, claims, 30, TimeUnit.MINUTES);
//                // Forward the request
//                return forwardRequest(exchange, chain, claims);
//
//            } catch (JwtException e) {
//                System.err.println("JWT Validation Error: " + e.getMessage());
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
//            } catch (Exception e) {
//                System.err.println("Unexpected Error: " + e.getMessage());
//                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//                return exchange.getResponse().setComplete();
//            }
//        }
//    }
//
//    private boolean isExcluded(String path) {
//        List<String> excludedPaths = new ArrayList<>();
//        excludedPaths.add("/spring/userService/login");
//        excludedPaths.add("/spring/userService/register");
//        for (String excludedPath : excludedPaths) {
//            if (pathMatcher.match(excludedPath, path)) { // Use AntPathMatcher for pattern matching
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private Jws<Claims> parseAndValidateToken(String token) throws JwtException {
//        try{
//            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();
//
//            String keyId = jwtParserBuilder
//                    .build()
//                    .parseClaimsJws(token)
//                    .getHeader().getKeyId();
//
//            PublicKey publicKey = publicKeyProvider.getPublicKey(keyId);
//
//            if (publicKey == null) {
//                throw new JwtException("Public key not found for key ID: " + keyId);
//            }
//
//            return jwtParserBuilder
//                    .setSigningKey(publicKey)
//                    .build()
//                    .parseClaimsJws(token);
//        }catch (JwtException e){
//            System.err.println("JWT Parsing or Validation Exception: " + e.getMessage());
//            throw e;
//        }
//    }
//
//    private Mono<Void> forwardRequest(ServerWebExchange exchange, GatewayFilterChain chain, Jws<Claims> claims) {
//        String userId = claims.getBody().get("userId").toString();
//        List<String> scopes = (List<String>) claims.getBody().get("availableScopes"); //Cast to correct type
//        //String path = exchange.getRequest().getPath().value();  // Not needed if routing is already done by Gateway
//
//        // Set headers for downstream services
//        exchange.getRequest().mutate()
//                .header("X-User-Id", userId)
//                .header("X-User-Scopes", String.join(",", scopes))
//                .build();
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1; // Ensure filter is executed early
//    }
//}