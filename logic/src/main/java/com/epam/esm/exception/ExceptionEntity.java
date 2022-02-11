package com.epam.esm.exception;


public class ExceptionEntity {
    private String HTTP;
    private String errorMsg;
    private int errorCode;

    public ExceptionEntity(String httpStatus, String errorMsg, int errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.HTTP = httpStatus;
    }

    public String getHttpStatus() {
        return HTTP;
    }

    public void setHttpStatus(String httpStatus) {
        this.HTTP = httpStatus;
    }

    @Override
    public String toString() {
        return "ExceptionEntity{" +
                "HTTP =" + HTTP +
                ",\n errorMsg='" + errorMsg + '\'' +
                ",\n errorCode=" + errorCode +
                '}';
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
