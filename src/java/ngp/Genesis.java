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

package ngp;

public final class Genesis {

    public static final long GENESIS_BLOCK_ID = -465575516309157244L;
    public static final long CREATOR_ID = -8579508346000165227L;
    public static final byte[] CREATOR_PUBLIC_KEY = {
    		-103, -111, -103, 121, 28, -75, -126, -69, 48, -114, 79, 116, -1, -86, -30, -119, -29, 41, -106, 61, -98, -34, -20, -64, 58, 127, -103, 36, -97, -39, 27, 4
    };


    public static final long[] GENESIS_RECIPIENTS = {
            Long.parseUnsignedLong("10908036269888271691"),
            Long.parseUnsignedLong("14238139472479815963")
    };


    public static final long[] GENESIS_AMOUNTS = {
            5000000000L,
            5000000000L,
     };

    public static final byte[][] GENESIS_SIGNATURES = {
      		{-7, -47, 32, 109, -34, 3, 81, -115, 56, 118, -77, -33, 110, -17, 109, -84, 123, 52, -34, 55, -65, 16, -11, 73, -69, 90, 66, 57, -125, -56, -103, 2, 99, 20, 73, -115, 27, -48, 115, -53, -51, 2, 97, -117, -62, -69, 57, 32, 104, 116, 41, -36, -36, 76, -115, -12, -52, -94, -92, -68, 14, -61, -60, -25},
      		{-100, 104, 45, -99, 18, -80, 24, 75, 80, 89, 36, -56, 66, -100, 9, -82, 46, -68, -70, -119, 56, -24, 107, 65, -32, 47, -32, 72, 72, 111, -111, 8, -53, -53, 94, -96, 69, -24, -90, -68, -111, 103, 110, -38, 10, -46, -31, -48, -6, -123, -16, -28, 25, 56, -124, 56, -112, 48, -31, 53, -128, 99, -32, 34},
     };
    
    public static final byte[] GENESIS_BLOCK_SIGNATURE = new byte[]{
    		-31, 57, -99, -33, -86, -116, 40, 29, -1, 7, 4, -44, 28, 43, -8, -96, 20, -29, -94, 91, -71, 17, -66, -120, -47, -27, 104, 113, -58, 72, 12, 13, 70, -116, -79, 75, -12, -4, -68, 11, 113, -59, 104, 107, -84, 127, 92, -84, 104, 47, -71, 27, -93, -104, -19, -88, -81, 17, -7, -88, -107, 104, 35, -61
    };

    private Genesis() {} // never

}
