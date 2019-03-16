package com.example.agnis.mobres;

import android.app.Activity;
import android.app.Dialog;

/**
 * Created by Rizzabas on 20/08/2018.
 */

public class Tools {
    public static void showDialogAbout(Activity activity) {
        Dialog dialog = new DialogUtils(activity).buildDialogInfo(R.string.title, R.string.content_about, R.string.agen, R.string.OK, R.drawable.card, new CallbackDialog() {
            @Override
            public void onPositiveClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onNegativeClick(Dialog dialog) {
            }
        });
        dialog.show();
    }
}

