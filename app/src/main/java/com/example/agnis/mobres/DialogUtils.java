package com.example.agnis.mobres;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rizzabas on 20/08/2018.
 */

public class DialogUtils  {

    private Activity activity;

    public DialogUtils(Activity activity) {
        this.activity = activity;
    }

    private Dialog buildDialogView(@LayoutRes int layout) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }


    public Dialog buildDialogInfo(@StringRes int title, @StringRes int content,@StringRes int agen, @StringRes int bt_text_pos, @DrawableRes int icon, final CallbackDialog callback) {
        return buildDialogInfo(activity.getString(title), activity.getString(content), activity.getString(agen), activity.getString(bt_text_pos), icon, callback);
    }

    // dialog info about
    public Dialog buildDialogInfo(String title, String content, String agen, String bt_text_pos, @DrawableRes int icon, final CallbackDialog callback) {
        final Dialog dialog = buildDialogView(R.layout.activity_dialogtentang);

        ((TextView) dialog.findViewById(R.id.title)).setText(title);
        ((TextView) dialog.findViewById(R.id.content)).setText(content);
        ((TextView) dialog.findViewById(R.id.agen)).setText(agen);
        ((Button) dialog.findViewById(R.id.bt_positive)).setText(bt_text_pos);
        ((ImageView) dialog.findViewById(R.id.icon)).setImageResource(icon);

        ((Button) dialog.findViewById(R.id.bt_positive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onPositiveClick(dialog);
            }
        });
        return dialog;
    }

}

