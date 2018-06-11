package com.focus.sourav.multipopupwindow;

public class PopupLable {

    private String sLable;
    private boolean hasOption;
    private String[] sOptionArray;

    public String getsLable()
    {
        return sLable;
    }

    public void setsLable(String sLable)
    {
        this.sLable = sLable;
    }

    public boolean isHasOption()
    {
        return hasOption;
    }

    public void setHasOption(boolean hasOption)
    {
        this.hasOption = hasOption;
    }

    public String[] getsOptionArray()
    {
        return sOptionArray;
    }

    public void setsOptionArray(String[] sOptionArray)
    {
        this.sOptionArray = sOptionArray;
        if (sOptionArray != null)
            setHasOption(true);
    }
}
