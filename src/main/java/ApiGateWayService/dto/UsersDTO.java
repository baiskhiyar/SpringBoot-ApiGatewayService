package ApiGateWayService.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.Data;


@Component
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UsersDTO {
    private Integer id;
    private String username;
    private String[] userScopes;
}
