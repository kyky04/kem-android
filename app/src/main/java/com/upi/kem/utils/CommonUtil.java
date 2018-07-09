package com.upi.kem.utils;

import android.content.Context;
import android.widget.Toast;

public class CommonUtil {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
