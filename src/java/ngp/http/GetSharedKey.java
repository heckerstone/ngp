/*
 * Copyright © 2013-2016 The NGP Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the NGP software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package ngp.http;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import ngp.Account;
import ngp.NgpException;
import ngp.crypto.Crypto;
import ngp.util.Convert;

import javax.servlet.http.HttpServletRequest;

public final class GetSharedKey extends APIServlet.APIRequestHandler {

    static final GetSharedKey instance = new GetSharedKey();

    private GetSharedKey() {
        super(new APITag[] {APITag.MESSAGES}, "account", "secretPhrase", "nonce");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        String secretPhrase = ParameterParser.getSecretPhrase(req, true);
        byte[] nonce = ParameterParser.getBytes(req, "nonce", true);
        long accountId = ParameterParser.getAccountId(req, "account", true);
        byte[] publicKey = Account.getPublicKey(accountId);
        if (publicKey == null) {
            return JSONResponses.INCORRECT_ACCOUNT;
        }
        byte[] sharedKey = Crypto.getSharedKey(Crypto.getPrivateKey(secretPhrase), publicKey, nonce);
        JSONObject response = new JSONObject();
        response.put("sharedKey", Convert.toHexString(sharedKey));
        return response;

    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}
