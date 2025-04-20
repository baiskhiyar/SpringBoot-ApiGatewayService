package ApiGateWayService.controllers;

import ApiGateWayService.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("spring")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(
            value = "/**",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> handleAllRequests(
            RequestEntity<byte[]> requestEntity, @RequestParam(required = false) Map<String, String> queryParams
    ) {
        HttpMethod method = requestEntity.getMethod();
        URI url = requestEntity.getUrl(); // Full URI including host, path, query string
        String path = url.getPath(); // Just the path part (e.g., /api/inspect)
        HttpHeaders headers = requestEntity.getHeaders(); // Gives you a MultiValueMap of headers
        byte[] bodyBytes = requestEntity.getBody(); // Raw body
        try {
            return gatewayService.handleRequests(method, path, headers, bodyBytes, queryParams);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("healthCheck")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", "Gateway Service is up and running");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}