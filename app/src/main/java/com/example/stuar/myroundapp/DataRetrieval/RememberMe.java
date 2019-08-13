package com.example.stuar.myroundapp.DataRetrieval;

import com.example.stuar.myroundapp.Models.User;

public class RememberMe {

    public static User currentOnlineUser;

    public static final String UserEmailKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

    //current order global variables - so can retrieve in different activities
    public static int cartCount = 0;
    public static int total = 0;
    public static String rName;
}
