package com.example.myapplication.viewmodel;

public class UpdatePassword {
    String userID,useRname,newPassword,confirmPassword;
    private boolean success;
    private String message;
    public UpdatePassword(String userID,String useRname, String newPassword, String confirmPassword) {
        this.userID = userID;
        this.newPassword = newPassword;
        this.useRname = useRname;
        this.confirmPassword = confirmPassword;

    }
    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public String getUserID() {
        return userID;
    }

    public String getUseRname() {
        return useRname;
    }

    public void setUseRname(String useRname) {
        this.useRname = useRname;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
