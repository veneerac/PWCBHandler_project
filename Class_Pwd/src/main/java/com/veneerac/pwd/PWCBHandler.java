package com.veneerac.pwd;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

    
public class PWCBHandler implements CallbackHandler {

    public PWCBHandler() {}

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                WSPasswordCallback pwcb = (WSPasswordCallback) callback;
                String id = pwcb.getIdentifier();
                int usage = pwcb.getUsage();

                // Allow only "admin"
                if (usage == WSPasswordCallback.USERNAME_TOKEN) { // usage == 1
                    if ("admin".equals(id)) {
                        pwcb.setPassword("admin");
                    } else {
                        // Reject unknown users
                        throw new UnsupportedCallbackException(callback, "Invalid username: " + id);
                    }
                }
                // Optional: Add conditions for other usage types if needed
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
            }
        }
    }
}