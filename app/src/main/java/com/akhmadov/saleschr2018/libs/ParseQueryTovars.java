package com.akhmadov.saleschr2018.libs;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ParseQueryTovars extends ParseQuery<ParseObject>  {


    public ParseQueryTovars(String theClassName) {
        super(theClassName);
    }


public  ParseQuery setMultipleQuery (ParseQueryTovars parseQueryTovars, int orderBy)
{
    List<ParseQuery<ParseObject>> queries = new ArrayList<>();
    queries.add(this);
    queries.add(parseQueryTovars);
    ParseQuery mainQuery = ParseQuery.or(queries);
    switch (orderBy){
        case 1:
            mainQuery.orderByDescending("new_price");
            break;
        case 2:
            mainQuery.orderByAscending("new_price");
            break;
        case 3:
            mainQuery.orderByDescending("skidka");
            break;
        default:
            mainQuery.orderByDescending("_created_at");

    }
    return mainQuery;
}
    public void setOrderBy(int order_by){
        switch (order_by){
            case 1:
                this.orderByDescending("new_price");
                break;
            case 2:
                this.orderByAscending("new_price");
                break;
            case 3:
                this.orderByDescending("skidka");
                break;
            default:
                this.orderByDescending("_created_at");

        }

    }
}
