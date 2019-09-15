package com.student.usermanagementdemo.utils;

import com.google.firebase.auth.FirebaseUser;

public class AppHelpers {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static void getAndStoreFirebaseUser(FirebaseUser firebaseUser){

    }
}
