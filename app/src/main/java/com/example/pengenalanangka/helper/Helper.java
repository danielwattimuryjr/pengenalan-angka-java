package com.example.pengenalanangka.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Helper {
    public static void navigateToActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public static void navigateToActivity(Context context, Class<?> className, android.os.Bundle extras) {
        Intent intent = new Intent(context, className);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
