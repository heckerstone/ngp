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

import ngp.peer.Peer;
import ngp.peer.Peers;

import static ngp.http.JSONResponses.MISSING_PEER;
import static ngp.http.JSONResponses.UNKNOWN_PEER;

import javax.servlet.http.HttpServletRequest;

public final class GetPeer extends APIServlet.APIRequestHandler {

    static final GetPeer instance = new GetPeer();

    private GetPeer() {
        super(new APITag[] {APITag.NETWORK}, "peer");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        String peerAddress = req.getParameter("peer");
        if (peerAddress == null) {
            return MISSING_PEER;
        }

        Peer peer = Peers.findOrCreatePeer(peerAddress, false);
        if (peer == null) {
            return UNKNOWN_PEER;
        }

        return JSONData.peer(peer);

    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}
