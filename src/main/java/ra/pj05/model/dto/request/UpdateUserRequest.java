package ra.pj05.model.dto.request;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private MultipartFile avatar;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
