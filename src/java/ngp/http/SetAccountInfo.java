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
import ngp.Attachment;
import ngp.Constants;
import ngp.NgpException;
import ngp.util.Convert;

import static ngp.http.JSONResponses.INCORRECT_ACCOUNT_DESCRIPTION_LENGTH;
import static ngp.http.JSONResponses.INCORRECT_ACCOUNT_NAME_LENGTH;

import javax.servlet.http.HttpServletRequest;

public final class SetAccountInfo extends CreateTransaction {

    static final SetAccountInfo instance = new SetAccountInfo();

    private SetAccountInfo() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.CREATE_TRANSACTION}, "name", "description");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        String name = Convert.nullToEmpty(req.getParameter("name")).trim();
        String description = Convert.nullToEmpty(req.getParameter("description")).trim();

        if (name.length() > Constants.MAX_ACCOUNT_NAME_LENGTH) {
            return INCORRECT_ACCOUNT_NAME_LENGTH;
        }

        if (description.length() > Constants.MAX_ACCOUNT_DESCRIPTION_LENGTH) {
            return INCORRECT_ACCOUNT_DESCRIPTION_LENGTH;
        }

        Account account = ParameterParser.getSenderAccount(req);
        Attachment attachment = new Attachment.MessagingAccountInfo(name, description);
        return createTransaction(req, account, attachment);

    }

}
