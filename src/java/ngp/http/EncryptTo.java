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

import org.json.simple.JSONStreamAware;

import ngp.Account;
import ngp.NgpException;
import ngp.crypto.EncryptedData;
import ngp.util.Convert;

import static ngp.http.JSONResponses.INCORRECT_MESSAGE_TO_ENCRYPT;
import static ngp.http.JSONResponses.INCORRECT_RECIPIENT;
import static ngp.http.JSONResponses.MISSING_MESSAGE_TO_ENCRYPT;

import javax.servlet.http.HttpServletRequest;

public final class EncryptTo extends APIServlet.APIRequestHandler {

    static final EncryptTo instance = new EncryptTo();

    private EncryptTo() {
        super(new APITag[] {APITag.MESSAGES}, "recipient", "messageToEncrypt", "messageToEncryptIsText", "compressMessageToEncrypt", "secretPhrase");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        long recipientId = ParameterParser.getAccountId(req, "recipient", true);
        byte[] recipientPublicKey = Account.getPublicKey(recipientId);
        if (recipientPublicKey == null) {
            return INCORRECT_RECIPIENT;
        }
        boolean isText = !"false".equalsIgnoreCase(req.getParameter("messageToEncryptIsText"));
        boolean compress = !"false".equalsIgnoreCase(req.getParameter("compressMessageToEncrypt"));
        String plainMessage = Convert.emptyToNull(req.getParameter("messageToEncrypt"));
        if (plainMessage == null) {
            return MISSING_MESSAGE_TO_ENCRYPT;
        }
        byte[] plainMessageBytes;
        try {
            plainMessageBytes = isText ? Convert.toBytes(plainMessage) : Convert.parseHexString(plainMessage);
        } catch (RuntimeException e) {
            return INCORRECT_MESSAGE_TO_ENCRYPT;
        }
        String secretPhrase = ParameterParser.getSecretPhrase(req, true);
        EncryptedData encryptedData = Account.encryptTo(recipientPublicKey, plainMessageBytes, secretPhrase, compress);
        return JSONData.encryptedData(encryptedData);

    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}