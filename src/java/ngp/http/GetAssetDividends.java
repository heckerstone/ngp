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

import ngp.AssetDividend;
import ngp.NgpException;
import ngp.db.DbIterator;

import javax.servlet.http.HttpServletRequest;

public final class GetAssetDividends extends APIServlet.APIRequestHandler {

    static final GetAssetDividends instance = new GetAssetDividends();

    private GetAssetDividends() {
        super(new APITag[] {APITag.AE}, "asset", "firstIndex", "lastIndex", "timestamp");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NgpException {

        long assetId = ParameterParser.getUnsignedLong(req, "asset", false);
        int timestamp = ParameterParser.getTimestamp(req);
        int firstIndex = ParameterParser.getFirstIndex(req);
        int lastIndex = ParameterParser.getLastIndex(req);

        JSONObject response = new JSONObject();
        JSONArray dividendsData = new JSONArray();
        try (DbIterator<AssetDividend> dividends = AssetDividend.getAssetDividends(assetId, firstIndex, lastIndex)) {
            while (dividends.hasNext()) {
                AssetDividend assetDividend = dividends.next();
                if (assetDividend.getTimestamp() < timestamp) {
                    break;
                }
                dividendsData.add(JSONData.assetDividend(assetDividend));
            }
        }
        response.put("dividends", dividendsData);
        return response;
    }

}
