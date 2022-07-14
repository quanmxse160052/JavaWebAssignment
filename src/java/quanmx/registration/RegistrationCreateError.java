/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanmx.registration;

/**
 *
 * @author Dell
 */
public class RegistrationCreateError {

    private String usernameLengthErr;
    private String passwordLengthErr;
    private String confirmNotMatchErr;
    private String fullNameLengthError;
    private String usernameIsExisted;

    public RegistrationCreateError() {
    }

    public RegistrationCreateError(String usernameLengthErr, String passwordLengthErr, String confirmNotMatchErr, String fullNameLengthError, String usernameIsExisted) {
        this.usernameLengthErr = usernameLengthErr;
        this.passwordLengthErr = passwordLengthErr;
        this.confirmNotMatchErr = confirmNotMatchErr;
        this.fullNameLengthError = fullNameLengthError;
        this.usernameIsExisted = usernameIsExisted;
    }

    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getConfirmNotMatchErr() {
        return confirmNotMatchErr;
    }

    public void setConfirmNotMatchErr(String confirmNotMatchErr) {
        this.confirmNotMatchErr = confirmNotMatchErr;
    }

    public String getFullNameLengthError() {
        return fullNameLengthError;
    }

    public void setFullNameLengthError(String fullNameLengthError) {
        this.fullNameLengthError = fullNameLengthError;
    }

    public String getUsernameIsExisted() {
        return usernameIsExisted;
    }

    public void setUsernameIsExisted(String usernameIsExisted) {
        this.usernameIsExisted = usernameIsExisted;
    }

}
