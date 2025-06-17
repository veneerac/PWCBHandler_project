package org.veneerac;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * Custom Password Callback Handler for WSO2 Micro Integrator
 * Used for handling UsernameToken and Signature authentication.
 */
public class PWCBHandler implements CallbackHandler {

    /**
     * Handles WS-Security callbacks triggered by Rampart.
     *
     * @param callbacks array of callbacks to handle
     * @throws IOException
     * @throws UnsupportedCallbackException if the callback is unsupported
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                WSPasswordCallback pwcb = (WSPasswordCallback) callback;
                String id = pwcb.getIdentifier();
                int usage = pwcb.getUsage();

                // Validate based on the usage type
                switch (usage) {
                    case WSPasswordCallback.USERNAME_TOKEN:  // Handle plain or digest UsernameToken
                    case 5:  // Custom usage type if applicable
                        if ("admin".equals(id)) {
                            pwcb.setPassword("admin");
                        } else {
                            throw new UnsupportedCallbackException(callback, "Invalid username: " + id);
                        }
                        break;

                    case WSPasswordCallback.SIGNATURE:  // Handle signature-related authentication
                        // Example: validate signature key password if needed
                        if ("wso2carbon".equals(id)) {
                            pwcb.setPassword("wso2carbon");
                        } else {
                            throw new UnsupportedCallbackException(callback, "Invalid key alias: " + id);
                        }
                        break;

                    default:
                        throw new UnsupportedCallbackException(callback, "Unknown usage type: " + usage);
                }
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
            }
        }
    }
}
