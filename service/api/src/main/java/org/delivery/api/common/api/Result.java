package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeInterface;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private Integer resultCode;

    private String resultMessage;

    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }
    // 기본 에러
    public static Result ERROR(ErrorCodeInterface errorCodeInterface){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription("성공")
                .build();
    }

    // 예외가 발생했을 때
    public static Result ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(tx.getLocalizedMessage()) //추천하지는 않는 메세지 사용
                .build();
    }

    // 에러문구 삽입
    public static Result ERROR(ErrorCodeInterface errorCodeInterface, String des){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(des)
                .build();
    }


}
