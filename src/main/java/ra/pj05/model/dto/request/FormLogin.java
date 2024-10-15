package ra.pj05.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormLogin {

	@NotBlank(message = "Tên đăng nhập không được để trống")
	private String username;
	@NotBlank(message = "Mẩu khẩu không được để trống")
	private String password;
	
}
