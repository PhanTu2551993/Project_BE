package ra.pj05.model.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormRegister {

	@NotBlank(message = "Tên đăng nhập không được để trống")
	private String username;
	@NotBlank(message = "Tên không được để trống")
	private String fullName;
	@NotBlank(message = "Số điện thoại không được để trống")
	private String phone;
	@NotBlank(message = "Email không được để trống")
	private String email;
	@NotBlank(message = "Mật khẩu không được để trống")
	private String password;
	private Set<String> roles;
}
