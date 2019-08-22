package com.lpjj.application.exception;

public enum ResultStatusCode {
 
    Success("0", "Success"),
    UserNotExist("1", "User not exist"),
    InValidParameter("2","Invalid parameter"),
    DataFormatException("4", "DataFormat exception"),
    DataNotExistException("5", "DataNotExistException"),
    TimeFormatException("6","TimeFormat Exception"),
    PictureFormatException("7","PictureFormat Exception"),
    IllegalArgumentException("8","IllegalArgumentException"),
    TokenInvalidOrOverdueException("9", "Token invalid or overdue exception"),
    AuthorizationCodeError("10", "authorization code error"),
    WrongSignatureException("11","Wrong Signature Exception"),
    SystemException("50", "system Exception"),
    MissingServletRequestParameter("400","Missing servletRequest parameter"),
    TypeMismatchException("401","Request parameter Type not match"),
    RequestMethodNotAllowed("405","Request method  not Allowed"),
    ;
 
    private String code;
    private String msg;
 
    private ResultStatusCode(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
 
    public String getMsg(){
        return this.msg;
    }
    public String getCode(){
        return this.code;
    }
 
}