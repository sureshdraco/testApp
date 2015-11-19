package se.svt.sureshkumar.testapp.network.request;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.android.volley.Request;
import com.google.gson.Gson;

public abstract class SvtRequest {

    protected transient TreeMap<String, String> requestHeader = new TreeMap<String, String>();

    public abstract int getRequestMethodType();

    public abstract String getBaseUrl();

    public String getApiUrl() {
        return getBaseUrl() + "?format=json" + getqueryPart();
    }

    private String getqueryPart() {
        HashMap<String, String> queryParams = getQueryParams();
        if (queryParams == null || queryParams.isEmpty()) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        for (String key : queryParams.keySet()) {
            queryBuilder.append("&" + key + "=");
            queryBuilder.append(queryParams.get(key));
        }
        return queryBuilder.toString();
    }

    public abstract HashMap<String, String> getQueryParams();

    public Map<String, String> getHeaders() {
        return requestHeader;
    }

    public String getJsonBody() {
        switch (getRequestMethodType()) {
            case Request.Method.DELETE:
            case Request.Method.GET:
                return "";
            case Request.Method.POST:
            case Request.Method.PUT:
            case Request.Method.PATCH:
            default:
                return new Gson().toJson(this);
        }
    }
}