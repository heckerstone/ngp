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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import ngp.DigitalGoodsStore;
import ngp.NgpException;
import ngp.db.DbIterator;
import ngp.db.DbUtils;
import ngp.db.FilteringIterator;
import ngp.util.Filter;

import javax.servlet.http.HttpServletRequest;

public final class GetDGSGoods extends APIServlet.APIRequestHandler {

    static final GetDGSGoods instance = new GetDGSGoods();

    private GetDGSGoods() {
        super(new APITag[] {APITag.DGS}, "seller", "firstIndex", "lastIndex", "inStockOnly", "hideDelisted", "includeCounts");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {
        long sellerId = ParameterParser.getAccountId(req, "seller", false);
        int firstIndex = ParameterParser.getFirstIndex(req);
        int lastIndex = ParameterParser.getLastIndex(req);
        boolean inStockOnly = !"false".equalsIgnoreCase(req.getParameter("inStockOnly"));
        boolean hideDelisted = "true".equalsIgnoreCase(req.getParameter("hideDelisted"));
        boolean includeCounts = "true".equalsIgnoreCase(req.getParameter("includeCounts"));

        JSONObject response = new JSONObject();
        JSONArray goodsJSON = new JSONArray();
        response.put("goods", goodsJSON);

        Filter<DigitalGoodsStore.Goods> filter = hideDelisted ? goods -> ! goods.isDelisted() : goods -> true;

        FilteringIterator<DigitalGoodsStore.Goods> iterator = null;
        try {
            DbIterator<DigitalGoodsStore.Goods> goods;
            if (sellerId == 0) {
                if (inStockOnly) {
                    goods = DigitalGoodsStore.Goods.getGoodsInStock(0, -1);
                } else {
                    goods = DigitalGoodsStore.Goods.getAllGoods(0, -1);
                }
            } else {
                goods = DigitalGoodsStore.Goods.getSellerGoods(sellerId, inStockOnly, 0, -1);
            }
            iterator = new FilteringIterator<>(goods, filter, firstIndex, lastIndex);
            while (iterator.hasNext()) {
                DigitalGoodsStore.Goods good = iterator.next();
                goodsJSON.add(JSONData.goods(good, includeCounts));
            }
        } finally {
            DbUtils.close(iterator);
        }

        return response;
    }

}
