package com.akhmadov.saleschr2018.libs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akhmadov.saleschr2018.R;

public class DialogFilter extends Dialog {
   public TextView price_desc;
    public TextView price_asc;
    public  TextView skidka;
    public  TextView by_new;
    public ImageView check_new;
    public ImageView check_price_asc;
    public ImageView check_price_desc;
    public ImageView check_sale;


    public DialogFilter(@NonNull final Context context) {
        super(context);

        this.setContentView(R.layout.filter_popup_menu);
        price_desc=this.findViewById(R.id.filter_popup_menu_price_desc);
        price_asc=this.findViewById(R.id.filter_popup_menu_price_asc);
        skidka=this.findViewById(R.id.filter_popup_menu_sale_asc);
        by_new=this.findViewById(R.id.filter_popup_menu_new);
        check_new=this.findViewById(R.id.check_new);
        check_price_asc=this.findViewById(R.id.check_price_asc);
        check_price_desc=this.findViewById(R.id.check_price_desc);
        check_sale=this.findViewById(R.id.check_sale);


    }

    public DialogFilter(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogFilter(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public void showDialogFilter()
    {

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations= R.style.DialogAnimation;
        this.setTitle(null);
        this.setCancelable(true);
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.show();
    }
    public void checkChoice(Integer orderId){
        switch (orderId){
            case 0:
                check_new.setVisibility(View.VISIBLE);
                check_price_asc.setVisibility(View.GONE);
                check_price_desc.setVisibility(View.GONE);
                check_sale.setVisibility(View.GONE);
                break;
            case 1:
                check_new.setVisibility(View.GONE);
                check_price_asc.setVisibility(View.GONE);
                check_price_desc.setVisibility(View.VISIBLE);
                check_sale.setVisibility(View.GONE);
                break;
            case 2:
                check_new.setVisibility(View.GONE);
                check_price_asc.setVisibility(View.VISIBLE);
                check_price_desc.setVisibility(View.GONE);
                check_sale.setVisibility(View.GONE);
                break;
            case 3:
                check_new.setVisibility(View.GONE);
                check_price_asc.setVisibility(View.GONE);
                check_price_desc.setVisibility(View.GONE);
                check_sale.setVisibility(View.VISIBLE);
                break;
        }
    }
}
