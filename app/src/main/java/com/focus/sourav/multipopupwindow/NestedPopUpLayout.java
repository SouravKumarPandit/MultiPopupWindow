package com.focus.sourav.multipopupwindow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class NestedPopUpLayout implements View.OnClickListener {
    private final Context mContext;
    private static int TOTAL_INSTANCE = -1;
    private int INSIDE_BLOCK = 0;
    private PopUpLinearLayout popUpLayout;
    private ArrayList<String> valueArrayList;
    private static ArrayList<PopupWindow> popupList = new ArrayList<>();
    private String[] sArrayListItem;

    private int OFFSET_X;
    private int OFFSET_Y;

    private SelectedOptionListener selectedOptionListener;

    public void setSelectionOptionLstener(SelectedOptionListener selectedOptionListener)
    {
        this.selectedOptionListener = selectedOptionListener;
    }

    public NestedPopUpLayout(Context mContext, View view, String[] optionArray)
    {
        this.mContext = mContext;
//        setSelectionOptionLstener(mContext)
        valueArrayList = new ArrayList<String>();
        this.sArrayListItem = optionArray;
        TOTAL_INSTANCE++;
        popUpLayout = getPopUpItems();
        PopupWindow popupWindow = new PopupWindow(popUpLayout);
//        dimBehind(popupWindow);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            popupWindow.setElevation(8f);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss()
            {
                TOTAL_INSTANCE--;
            }
        });


        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] iSectionIn = pointInside(location[0], (int) (location[1] + view.getWidth() * 0.7));
        renderWindowOffset(iSectionIn[0], iSectionIn[1], view);
        Point point = new Point(location[0], location[1]);
//        layout.getViewTreeObserver().addOnGlobalLayoutListener(mLocationLayoutListener);
//        layout.getViewTreeObserver().addOnGlobalLayoutListener(mAutoDismissLayoutListener);
        popupWindow.showAtLocation(popUpLayout, Gravity.NO_GRAVITY, (int) point.x + OFFSET_X, (int) point.y + OFFSET_Y);

        popupList.add(popupWindow);
    }


    private void renderWindowOffset(int tooLeft, int tooRight, View view)
    {

        int iWidth = view.getWidth();
        int iHeight = view.getHeight();
        OFFSET_X = iWidth;
        OFFSET_Y = -iHeight / 2;
        switch (INSIDE_BLOCK)
        {
            case 1:
            case 4:
            case 7:
                popUpLayout.setDrawableRight(false);
                OFFSET_X = iWidth;
                OFFSET_Y = -iHeight / 2;
                break;
            case 3:
            case 6:
            case 9:
                popUpLayout.setDrawableRight(true);
                OFFSET_X = -iWidth;
                OFFSET_Y = -iHeight / 2;
                break;

        }

    }

   /* public  void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager)context .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
//        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }*/

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

    public PopUpLinearLayout getPopUpItems()
    {

        PopUpLinearLayout popUpItemsList = new PopUpLinearLayout(mContext);
        popUpItemsList.setBgPaintColor(TOTAL_INSTANCE % 6);

        for (int i = 0; sArrayListItem.length > i; i++)
        {
            LinearLayout linearLayout = new LinearLayout(mContext);
            ViewGroup.LayoutParams linearLayoutPara = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(linearLayoutPara);
            TextView textLable = new TextView(mContext);
            textLable.setPadding((int) dpToPixel(2), (int) dpToPixel(5), (int) dpToPixel(2), (int) dpToPixel(5));
//            textLable.setTextColor(R.drawable.text_state_drawable);
            if (i != 0)
            {
                View divider = new View(mContext);
                LinearLayout.LayoutParams dividerParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                dividerParam.setMargins((int) dpToPixel(6), 0, (int) dpToPixel(6), (int) dpToPixel(2));
                divider.setLayoutParams(dividerParam);
                divider.setBackgroundColor(Color.LTGRAY);
                popUpItemsList.addView(divider);
            }
            if (i == 3 || i == 0/*todo if has option then do some thing dirty*/)
            {
//                textLable.setText("▶ "+sArrayListItem[i]);
                textLable.setText(sArrayListItem[i] + " ▶");
                PopupLable moreOptionDTO = new PopupLable();
                moreOptionDTO.setHasOption(true);
                moreOptionDTO.setsLable(sArrayListItem[i] + " ▶");
                moreOptionDTO.setsOptionArray(new String[]{"Text One : " + i, "Text Two : " + i, "TEXT Three : " + i, "Text Four : " + i, "TEXT Five : " + i});
                linearLayout.setTag(moreOptionDTO);

            }
            else
            {
                textLable.setText(sArrayListItem[i]);
                PopupLable popupLable = new PopupLable();
                popupLable.setHasOption(false);
                popupLable.setsLable(sArrayListItem[i]);
                popupLable.setsOptionArray(null);
                linearLayout.setTag(popupLable);
            }
            textLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            textLable.setTypeface(Typeface.DEFAULT_BOLD);
            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textLable.setLayoutParams(textParam);
            linearLayout.addView(textLable);
            popUpItemsList.addView(linearLayout);
            linearLayout.setOnClickListener(this);
        }
        return popUpItemsList;
    }

    @Override
    public void onClick(View view)
    {
        LinearLayout lableLayout = (LinearLayout) view;
        Object tagObj = lableLayout.getTag();
        PopupLable popupLable = null;
        if (tagObj != null)
            popupLable = (PopupLable) tagObj;
        if (popupLable != null)
        {
            if (popupLable.isHasOption())
            {
                NestedPopUpLayout nestedPop = new NestedPopUpLayout(mContext, view, popupLable.getsOptionArray());
                if (selectedOptionListener != null)
                {
                    nestedPop.setSelectionOptionLstener(selectedOptionListener);
                }
                valueArrayList.add(popupLable.getsLable());
            }
            else
            {
//                Toast.makeText(mContext, "" + moreOpt.getsLable(), Toast.LENGTH_SHORT).show();

                if (selectedOptionListener != null)
                {
                    selectedOptionListener.onSelectedOption(new int[]{}, popupLable.getsLable());
                }
                for (int i = popupList.size() - 1; i >= 0; i--)
                {
                    popupList.get(i).dismiss();
                    TOTAL_INSTANCE = -1;
                }

            }


        }
    }


    private int[] pointInside(int X, int Y)
    {
        int[] inSection = new int[2];
        Rect rectBound = new Rect();
        int totalSection = 3;
        int blockWidth = getScreenWidth() / totalSection;
        int blockHeight = getScreenHeight() / totalSection;
        int xStart = 0;
        int xEnd = blockWidth;
        int yStart = 0;
        int yEnd = blockHeight;
        for (int i = 1; i <= totalSection; )
        {
            inSection[0] = i;
            for (int j = 1; j <= totalSection; )
            {
                inSection[1] = j;
                INSIDE_BLOCK++;
                rectBound.set(xStart, yStart, xEnd, yEnd);
                if (rectBound.contains(X, Y))
                    return inSection;
/*
                {
                    Toast.makeText(mContext, "It's in Section " + inSection[0] + " : " + inSection[1], Toast.LENGTH_SHORT).show();
                }
*/
                j++;
                xStart = xEnd;
                xEnd = xStart + blockWidth;
            }
            i++;
            xStart = 0;
            xEnd = blockWidth;
            yStart = yEnd;
            yEnd = yEnd + blockHeight;
        }
        return inSection;
    }


    public interface SelectedOptionListener {
        public void onSelectedOption(int[] optionPath, String sLable);

    }
/*
    public int[] pointInside(int X, int Y)
    {
        int[] inSection = new int[2];
        Rect rectBound = new Rect();

        int blockWidth = getScreenWidth() / totalSection;
        int blockHeight = getScreenHeight() / totalSection;

        int xStart = 0;
        int xEnd = blockWidth;

        int yStart = 0;
        int yEnd = blockHeight;


        for (int i = 1; i <= totalSection; )
        {

            inSection[0] = i;

            for (int j = 1; j <= totalSection; )
            {
                inSection[1] = j;

                rectBound.set(xStart, yStart, xEnd, yEnd);
                if (rectBound.contains(X, Y))
                {
                    Toast.makeText(mContext, "It's in Section " + inSection[0] + " : " + inSection[1], Toast.LENGTH_SHORT).show();
                    return inSection;
                }
//                Toast.makeText(mContext, "It's in Section " + xStart + " : " + yStart + " : " + xEnd + " : " + yEnd + " : ", Toast.     LENGTH_LONG).show();


//                Toast.makeText(mContext, "It's in Section " + inSection[0] + " : " + inSection[1], Toast.LENGTH_SHORT).show();


                j++;
                xStart = xEnd;
                xEnd = xStart + blockWidth;
            }


            i++;
            xStart = 0;
            xEnd = blockWidth;

            yStart = yEnd;
            yEnd = yEnd + blockHeight;


        }


        return inSection;
    }*/

    /*public int[] getViewOffsets(View view, View popView)
    {
        int[] offSetVal = new int[2];

        location = new int[2];
        view.getLocationOnScreen(location);

        Rect rectBound = new Rect(0, 0, getScreenWidth(), getScreenHeight());

        if (rectBound.contains(location[0], location[1])) {

        }


        int screenWidth = getScreenHeight();
        int screenHeight = getScreenHeight();
        if (location[0] > getScreenHeight() / 2) {


        }


        return offSetVal;
    }*/

/*

        int[] location = new int[2];
        view.getLocationOnScreen(location); //get physical location in Px
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((AppCompatActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeightPx = displaymetrics.heightPixels;
        int maxHeight = screenHeightPx - location[1] - view.getHeight() - 20;
*/

}
