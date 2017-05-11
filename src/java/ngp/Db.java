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

import ngp.db.BasicDb;
import ngp.db.TransactionalDb;

public final class Db {

    public static final String PREFIX = Constants.isTestnet ? "ngp.testDb" : "ngp.db";
    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(NGP.getIntProperty("ngp.dbCacheKB"))
            .dbUrl(NGP.getStringProperty(PREFIX + "Url"))
            .dbType(NGP.getStringProperty(PREFIX + "Type"))
            .dbDir(NGP.getStringProperty(PREFIX + "Dir"))
            .dbParams(NGP.getStringProperty(PREFIX + "Params"))
            .dbUsername(NGP.getStringProperty(PREFIX + "Username"))
            .dbPassword(NGP.getStringProperty(PREFIX + "Password", null, true))
            .maxConnections(NGP.getIntProperty("ngp.maxDbConnections"))
            .loginTimeout(NGP.getIntProperty("ngp.dbLoginTimeout"))
            .defaultLockTimeout(NGP.getIntProperty("ngp.dbDefaultLockTimeout") * 1000)
            .maxMemoryRows(NGP.getIntProperty("ngp.dbMaxMemoryRows"))
    );

    static void init() {
        db.init(new NgpDbVersion());
    }

    static void shutdown() {
        db.shutdown();
    }

    private Db() {} // never

}
