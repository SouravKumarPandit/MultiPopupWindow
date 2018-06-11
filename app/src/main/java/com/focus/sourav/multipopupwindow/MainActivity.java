package com.focus.sourav.multipopupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity  extends AppCompatActivity implements View.OnClickListener, NestedPopUpLayout.SelectedOptionListener {
    private PopupWindow changeSortPopUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setBackgroundColor(Color.DKGRAY);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < 15; i++)
        {
            linearLayout.addView(getRowLayout());
        }

        setContentView(linearLayout);
//        setContentView(R.layout.activity_popup);
//        getSetTextclick(R.id.popup_click_center);
//        getSetTextclick(R.id.popup_left_top);
//        getSetTextclick(R.id.popup_right_top);
//        getSetTextclick(R.id.popup_mid_right);
//        getSetTextclick(R.id.popup_mid_left);
//        getSetTextclick(R.id.popup_end_right);
//        getSetTextclick(R.id.popup_end_left);
//        getSetTextclick(R.id.popup_bottom_center);
//        getSetTextclick(R.id.popup_top_center);
//

    }


    private void showSortPopup(final Activity context, Point p, int iWidth, int iHeight)
    {
        // Inflate the popup_layout.xml
//        RelativeLayout viewGroup = (LinearLayout) context.findViewById(R.id.ll_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.sort_popup_layout, null);

        // Creating the PopupWindow
        changeSortPopUp = new PopupWindow(context);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);
//        changeSortPopUp.update();


        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
//        int OFFSET_X = 100;
//        int OFFSET_Y = -95;
        int OFFSET_X = iWidth;
        int OFFSET_Y = -iHeight / 2;

        if (p.x > getScreenWidth() - iWidth)
        {
            OFFSET_X = -iWidth;
        }

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(null);

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);


        // Getting a reference to Close button, and close the popup when clicked.
   /*     Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });*/

    }

    public static int getScreenWidth()
    {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight()
    {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static float dpToPixel(float dp)
    {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }

    @Override
    public void onClick(View view)
    {
//        int iWidth=view.getWidth();
//        int iHeight=view.getHeight();

//        Point point;
//        int[] location;
     /*   switch (view.getId()) {

            case R.id.popup_click_center:
                location = new int[2];
                view.getLocationOnScreen(location);
                point = new Point(location[0], location[1]);
//                showSortPopup(PopupPractive.this, point);
                break;
            case R.id.popup_left_top:
                break;
            case R.id.popup_right_top:
                break;
            case R.id.popup_mid_right:
                break;
            case R.id.popup_mid_left:
                break;
            case R.id.popup_end_right:
                break;
            case R.id.popup_end_left:
                break;
        }*/


//        location = new int[2];
//        view.getLocationOnScreen(location);
//        point = new Point(location[0], location[1]);
//        showSortPopup(PopupPractive.this, point,iWidth,iHeight);
        Toast.makeText(MainActivity.this, ((TextView) view).getText().toString() + "  : clicked", Toast.LENGTH_SHORT).show();
        String[] samplString = new String[]{"Text One", "Text Null", "Text Fake", "Text Awesome", "Text Weired"};
        NestedPopUpLayout nestedPopUpLayout = new NestedPopUpLayout(this, view, samplString);
        nestedPopUpLayout.setSelectionOptionLstener(this);
//        NestedPopUpWindow nestedPopUpLayout = new NestedPopUpWindow(this,view);
//        nestedPopUpLayout.show();
//        nestedPopUpLayout.showPopUP();
    }

    public void getSetTextclick(int viewID)
    {

        final TextView textView = findViewById(viewID);
        textView.setOnClickListener(this);
    }

    public LinearLayout getRowLayout()
    {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < 10; i++)
        {
            rowLayout.addView(getAddText());
        }

        return rowLayout;
    }

    public TextView getAddText()
    {
        TextView textView = new TextView(this);
        textView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        textView.setOnClickListener(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("+1");
        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        textViewParam.setMargins((int)dpToPixel(5),(int)dpToPixel(2),(int)dpToPixel(5),(int)dpToPixel(2));
        textView.setLayoutParams(textViewParam);
        return textView;
    }

    @Override
    public void onSelectedOption(int[] optionPath, String sLable)
    {
        Toast.makeText(this, "Selection  :  "+sLable, Toast.LENGTH_SHORT).show();
    }
}
