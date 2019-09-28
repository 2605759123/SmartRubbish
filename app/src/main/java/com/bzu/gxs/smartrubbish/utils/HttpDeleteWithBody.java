package com.bzu.gxs.smartrubbish.utils;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * Created by pc on 2018/5/13.
 */

public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteWithBody(final String uri) {

        super();

        setURI(URI.create(uri));

    }

    public HttpDeleteWithBody(final URI uri) {

        super();

        setURI(uri);

    }

    public HttpDeleteWithBody() { super(); }
}
