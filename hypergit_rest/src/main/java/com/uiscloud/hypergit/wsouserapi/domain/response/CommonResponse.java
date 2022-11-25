package com.goodmit.hypergit.wsouserapi.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "결과  Response")
@Getter @Setter
public class CommonResponse<T> extends BasicResponse {
	@Schema(description = "결과 개수", defaultValue = "0")
	private int count;
	
	@Schema(description = "결과")
	private T data;
	
	public CommonResponse(T data) {
		this.data = data;
		if(data instanceof List) {
			this.count = ((List<?>)data).size();
		} else {
			this.count = 1;
		}
	}
	
}
