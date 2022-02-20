package com.techpal.sn.payload.request;

import java.io.Serializable;
import java.util.Objects;

public class UserInfosModify implements Serializable {

    private String oldPassword;

    private String newPassword;

    private String uidUser;


    public UserInfosModify(String oldPassword, String newPassword, String uidUser) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.uidUser = uidUser;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfosModify that = (UserInfosModify) o;
        return getOldPassword().equals(that.getOldPassword()) && getNewPassword().equals(that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOldPassword(), getNewPassword());
    }
}
