package org.delivery.api.common.exception;

import org.delivery.common.error.ErrorCodeIfs;

public interface ApiExceptionInterface {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();
}
