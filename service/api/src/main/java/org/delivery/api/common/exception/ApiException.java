package org.delivery.common.exception;

import lombok.Getter;
import org.delivery.common.error.ErrorCodeIfs;


//@Getter
//public class ApiException extends RuntimeException implements ApiExceptionIfs {
//
//    private final ErrorCodeIfs errorCodeIfs;
//
//    private final String errorDescription;
//
//
//    public ApiException(ErrorCodeIfs errorCodeIfs) {
//        super(errorCodeIfs.getDescription()); // 부모클래스의 생성자 호출
//        this.errorCodeIfs = errorCodeIfs;
//        this.errorDescription = errorCodeIfs.getDescription();
//    }
//
//    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
//        super(errorDescription);
//        this.errorCodeIfs = errorCodeIfs;
//        this.errorDescription = errorDescription;
//    }
//
//    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx) {
//        super(tx);
//        this.errorCodeIfs = errorCodeIfs;
//        this.errorDescription = errorCodeIfs.getDescription();
//    }
//
//    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx, String errorDescription ) {
//        super(tx);
//        this.errorCodeIfs = errorCodeIfs;
//        this.errorDescription = errorDescription;
//    }
//}
