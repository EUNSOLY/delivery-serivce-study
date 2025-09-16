package org.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
