package io.micro.permissionsample.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Dialog util...
 */
public class DialogFactory {

    public void showDialog(Context context) {
        new AlertDialog.Builder(context)

                .show();
    }

}
