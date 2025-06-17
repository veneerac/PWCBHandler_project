package org.veneerac;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;


public class PWCBHandler implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                WSPasswordCallback pwcb = (WSPasswordCallback) callback;
                String id = pwcb.getIdentifier();
                int usage = pwcb.getUsage();

                System.out.println("Callback ID: " + id);
                System.out.println("Callback Usage: " + usage);

                switch (usage) {
                    case WSPasswordCallback.USERNAME_TOKEN:       // usage == 1
                    case 5:  // Handle both plain and digest UsernameToken
                        if ("admin".equals(id)) {
                            pwcb.setPassword("admin");
                        } else {
                            throw new UnsupportedCallbackException(callback, "Invalid username: " + id);
                        }
                        break;

                    case WSPasswordCallback.SIGNATURE:             // usage == 2


                    default:
                        throw new UnsupportedCallbackException(callback, "Unknown usage type: " + usage);
                }
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
            }
        }
    }
}
