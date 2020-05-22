package com.akhmadov.saleschr2018.libs;

 public class TabId {
    public static String  GetTabId(String tab)
    {
        if (tab.toLowerCase().contains("муж"))
        {
            return  "1";
        }
        else if (tab.toLowerCase().contains("жен"))
        {
            return "2";
        }
        else if (tab.toLowerCase().contains("дет"))
        {
            return "3";
        }
        else
        return "0";
    }
}
