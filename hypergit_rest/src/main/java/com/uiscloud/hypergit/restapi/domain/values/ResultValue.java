package com.uiscloud.hypergit.restapi.domain.values;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모델 산출값")
@Getter @Setter
public class ResultValue {
	
	@Schema(description = "APP ID")
	private long appId;
	
	@Schema(description = "Transaction ID")
	private String transactionId;
	
	@Schema(description = "결곽 값")
	private String resultData;
}
