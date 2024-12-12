package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String email;
    private String token;

    public AuthResponse( String token) {
      
        this.token = token;
    }

    // Getters and setters

    
}
