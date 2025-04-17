//package ApiGateWayService.configs;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//
//import java.security.PublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@Component
//public class AuthServerPublicKeyProvider implements PublicKeyProvider {
//
//    @Value("${auth.service.public-key-url}")
//    private String publicKeyUrl;
//
//    private final WebClient webClient;
//
//    public AuthServerPublicKeyProvider(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    @Override
//    public PublicKey getPublicKey(String keyId) {
//        try {
//            String base64EncodedPublicKey = webClient.get()
//                    .uri(uriBuilder -> uriBuilder.path(publicKeyUrl).queryParam("kid", keyId).build())
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//
//            byte[] publicKeyBytes = Base64.getDecoder().decode(base64EncodedPublicKey);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(keySpec);
//
//        } catch (WebClientResponseException e) {
//            System.err.println("Error fetching public key: " + e.getMessage());
//            return null;
//        } catch (Exception e) {
//            System.err.println("Error generating public key: " + e.getMessage());
//            return null;
//        }
//    }
//}