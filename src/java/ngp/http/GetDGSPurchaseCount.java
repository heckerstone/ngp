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

import ngp.DigitalGoodsStore;
import ngp.NgpException;

import javax.servlet.http.HttpServletRequest;

public final class GetDGSPurchaseCount extends APIServlet.APIRequestHandler {

    static final GetDGSPurchaseCount instance = new GetDGSPurchaseCount();

    private GetDGSPurchaseCount() {
        super(new APITag[] {APITag.DGS}, "seller", "buyer", "withPublicFeedbacksOnly", "completed");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        long sellerId = ParameterParser.getAccountId(req, "seller", false);
        long buyerId = ParameterParser.getAccountId(req, "buyer", false);
        final boolean completed = "true".equalsIgnoreCase(req.getParameter("completed"));
        final boolean withPublicFeedbacksOnly = "true".equalsIgnoreCase(req.getParameter("withPublicFeedbacksOnly"));

        JSONObject response = new JSONObject();
        int count;
        if (sellerId != 0 && buyerId == 0) {
            count = DigitalGoodsStore.Purchase.getSellerPurchaseCount(sellerId, withPublicFeedbacksOnly, completed);
        } else if (sellerId == 0 && buyerId != 0) {
            count = DigitalGoodsStore.Purchase.getBuyerPurchaseCount(buyerId, withPublicFeedbacksOnly, completed);
        } else if (sellerId == 0 && buyerId == 0) {
            count = DigitalGoodsStore.Purchase.getCount(withPublicFeedbacksOnly, completed);
        } else {
            count = DigitalGoodsStore.Purchase.getSellerBuyerPurchaseCount(sellerId, buyerId, withPublicFeedbacksOnly, completed);
        }
        response.put("numberOfPurchases", count);
        return response;
    }

}