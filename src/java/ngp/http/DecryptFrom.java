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
import ngp.crypto.EncryptedData;
import ngp.util.Convert;
import ngp.util.Logger;

import static ngp.http.JSONResponses.DECRYPTION_FAILED;
import static ngp.http.JSONResponses.INCORRECT_ACCOUNT;

import javax.servlet.http.HttpServletRequest;

public final class DecryptFrom extends APIServlet.APIRequestHandler {

    static final DecryptFrom instance = new DecryptFrom();

    private DecryptFrom() {
        super(new APITag[] {APITag.MESSAGES}, "account", "data", "nonce", "decryptedMessageIsText", "uncompressDecryptedMessage", "secretPhrase");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        byte[] publicKey = Account.getPublicKey(ParameterParser.getAccountId(req, true));
        if (publicKey == null) {
            return INCORRECT_ACCOUNT;
        }
        String secretPhrase = ParameterParser.getSecretPhrase(req, true);
        byte[] data = Convert.parseHexString(Convert.nullToEmpty(req.getParameter("data")));
        byte[] nonce = Convert.parseHexString(Convert.nullToEmpty(req.getParameter("nonce")));
        EncryptedData encryptedData = new EncryptedData(data, nonce);
        boolean isText = !"false".equalsIgnoreCase(req.getParameter("decryptedMessageIsText"));
        boolean uncompress = !"false".equalsIgnoreCase(req.getParameter("uncompressDecryptedMessage"));
        try {
            byte[] decrypted = Account.decryptFrom(publicKey, encryptedData, secretPhrase, uncompress);
            JSONObject response = new JSONObject();
            response.put("decryptedMessage", isText ? Convert.toString(decrypted) : Convert.toHexString(decrypted));
            return response;
        } catch (RuntimeException e) {
            Logger.logDebugMessage(e.toString());
            return DECRYPTION_FAILED;
        }
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}