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

import ngp.Token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import static ngp.http.JSONResponses.INCORRECT_FILE;
import static ngp.http.JSONResponses.INCORRECT_TOKEN;
import static ngp.http.JSONResponses.MISSING_TOKEN;

import java.io.IOException;

public final class DecodeFileToken extends APIServlet.APIRequestHandler {

    static final DecodeFileToken instance = new DecodeFileToken();

    private DecodeFileToken() {
        super("file", new APITag[] {APITag.TOKENS}, "token");
    }

    @Override
    public JSONStreamAware processRequest(HttpServletRequest req) throws ParameterException {
        String tokenString = req.getParameter("token");
        if (tokenString == null) {
            return MISSING_TOKEN;
        }
        byte[] data;
        try {
            Part part = req.getPart("file");
            if (part == null) {
                throw new ParameterException(INCORRECT_FILE);
            }
            ParameterParser.FileData fileData = new ParameterParser.FileData(part).invoke();
            data = fileData.getData();
        } catch (IOException | ServletException e) {
            throw new ParameterException(INCORRECT_FILE);
        }

        try {
            Token token = Token.parseToken(tokenString, data);
            return JSONData.token(token);
        } catch (RuntimeException e) {
            return INCORRECT_TOKEN;
        }
    }

    @Override
    protected boolean requirePost() {
        return true;
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}
