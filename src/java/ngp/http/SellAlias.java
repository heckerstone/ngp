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
import ngp.Alias;
import ngp.Attachment;
import ngp.Constants;
import ngp.NgpException;
import ngp.util.Convert;

import static ngp.http.JSONResponses.INCORRECT_ALIAS_OWNER;
import static ngp.http.JSONResponses.INCORRECT_RECIPIENT;

import javax.servlet.http.HttpServletRequest;


public final class SellAlias extends CreateTransaction {

    static final SellAlias instance = new SellAlias();

    private SellAlias() {
        super(new APITag[] {APITag.ALIASES, APITag.CREATE_TRANSACTION}, "alias", "aliasName", "recipient", "priceNQT");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {
        Alias alias = ParameterParser.getAlias(req);
        Account owner = ParameterParser.getSenderAccount(req);

        long priceNQT = ParameterParser.getLong(req, "priceNQT", 0L, Constants.MAX_BALANCE_NQT, true);

        String recipientValue = Convert.emptyToNull(req.getParameter("recipient"));
        long recipientId = 0;
        if (recipientValue != null) {
            try {
                recipientId = Convert.parseAccountId(recipientValue);
            } catch (RuntimeException e) {
                return INCORRECT_RECIPIENT;
            }
            if (recipientId == 0) {
                return INCORRECT_RECIPIENT;
            }
        }

        if (alias.getAccountId() != owner.getId()) {
            return INCORRECT_ALIAS_OWNER;
        }

        Attachment attachment = new Attachment.MessagingAliasSell(alias.getAliasName(), priceNQT);
        return createTransaction(req, owner, recipientId, 0, attachment);
    }
}
