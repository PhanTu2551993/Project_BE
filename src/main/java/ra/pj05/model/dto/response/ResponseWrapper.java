package ra.pj05.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.pj05.constants.EHttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseWrapper<T> {
	EHttpStatus eHttpStatus;
	int statusCode;
	T data;
}
