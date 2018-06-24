/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelipost.webfront;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * @author Rafael
 */
public class RestErrorHandler implements ResponseErrorHandler {

    private static final Log logger = LogFactory.getLog(RestErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {
    }

    @Override
    public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
        return false;
    }
}
