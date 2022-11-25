package com.goodmit.hypergit.restapi.controller;

import com.goodmit.hypergit.restapi.domain.response.BasicResponse;
import com.goodmit.hypergit.restapi.domain.response.ErrorResponse;
import com.goodmit.hypergit.restapi.domain.values.ResultValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Online Model ", description = "Online Model API")
@RestController
@RequestMapping("/api/online")
@RequiredArgsConstructor
public class OnlineRestApi {

    @Operation(summary = "Online\n" +
            "On-line Model 수행을 위해 사용 하는 API.", description = "Ml Model 의 산출값을 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResultValue.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST" , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND" , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

})
    @Parameters({
            @Parameter(name = "appId", description = "app 구분값 ", example = "APP1"),
            @Parameter(name = "transactionId", description = "Online 구분 값", example = "transaction1"),
            @Parameter(name = "data", description = "입력 값", example = "11,2,3,4,4,5,5,5,6,6,,6,7,7,7,,7,,,7,7,7,,7,7,7,7,,7,,7,7,7,7,,")
    })
    @ResponseBody
    @GetMapping( "")
    public ResponseEntity<String> getPosts(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "transactionId") String transactionId,
            @RequestParam(value = "data") String data
    ) {

        String result = "3,001,6,6,6,6,,002,0,0,0,0,,003,0,0,0,0";

        return  ResponseEntity.ok().body(result);
    }

    @ResponseBody
    @GetMapping( "{appId}")
    public ResponseEntity<String> getSelectPosts(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "transactionId") String transactionId,
            @RequestParam(value = "data") String data
    ) {

        String result = "3,001,6,6,6,6,,002,0,0,0,0,,003,0,0,0,0";

        return  ResponseEntity.ok().body(result);
    }
}
