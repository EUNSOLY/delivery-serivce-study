package org.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeInterface;

// 최상위
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {
    private Result result;

    @Valid // 유효한지 검사
    private T body;

    public static <T>Api<T> OK(T data){
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface,tx);
        return api;
    }
    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface,String des){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface,des);
        return api;
    }
}
