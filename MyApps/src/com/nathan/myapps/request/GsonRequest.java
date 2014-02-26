package com.nathan.myapps.request;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nathan.myapps.bean.miui.MiuiPic;
import com.nathan.myapps.utils.DateDeserializerUtils;
import com.nathan.myapps.utils.DateSerializerUtils;

public class GsonRequest<T> extends Request<T> {

    /**
     * Gson parser
     */

    public Gson mGson = new GsonBuilder()
            .registerTypeAdapter(java.util.Date.class, new DateDeserializerUtils())
            .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
            .enableComplexMapKeySerialization().setDateFormat(DateFormat.LONG).create();

    private final Class<T> parentClass;
    /**
     * Class type for the response
     */
    private final Class<?> mClass;

    /**
     * Callback for response delivery
     */
    private final Listener<T> mListener;

    private final ErrorListener mErrorListener;

    private Map<String, String> param = null;

    /**
     * @param method
     *            Request type.. Method.GET etc
     * @param url
     *            path for the requests
     * @param objectClass
     *            expected class type for the response. Used by gson for
     *            serialization.
     * @param listener
     *            handler for the response
     * @param errorListener
     *            handler for errors
     */
    public GsonRequest(int method, String url, Class<T> parentClass, Class<?> objectClass,
            Listener<T> listener, ErrorListener errorListener) {

        super(method, url, errorListener);
        this.parentClass = parentClass;
        this.mClass = objectClass;
        this.mListener = listener;
        this.mErrorListener = errorListener;
        setTimeOut();
    }

    /*
     * @Override public Map<String, String> getHeaders() throws AuthFailureError
     * { Map<String, String> headers = super.getHeaders(); if (headers == null
     * || headers.equals(Collections.emptyMap())) { headers = new
     * HashMap<String, String>(); }
     * MyApplication.getInstance().addSessionCookie(headers); return headers; }
     */

    public GsonRequest(int method, String url, Map<String, String> params, Class<T> parentClass,
            Class<?> class1, Listener<T> listener, ErrorListener errorListener) {

        super(method, url, errorListener);
        this.param = params;
        this.parentClass = parentClass;
        this.mClass = class1;
        this.mListener = listener;
        this.mErrorListener = errorListener;
        setTimeOut();
    }

    // 20秒超时，最大请求次数为1
    private void setTimeOut() {
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        return param;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

        }
        catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        Type objectType;
        if (mClass != null) {
            objectType = type(parentClass, mClass);
        }
        else {
            objectType = parentClass;
        }
        if (mClass == null && parentClass == null) {
            objectType = new com.google.gson.reflect.TypeToken<List<MiuiPic>>()
            {
            }.getType();
        }

        return (Response<T>) Response.success(mGson.fromJson(json, objectType),
                HttpHeaderParser.parseCacheHeaders(response));
    }

    static ParameterizedType type(final Class<?> raw, final Type... args) {
        return new ParameterizedType()
        {

            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }

    @Override
    public void deliverError(VolleyError error) {
        // TODO Auto-generated method stub
        super.deliverError(error);
    }

}
