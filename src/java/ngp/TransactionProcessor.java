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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ngp.db.DbIterator;
import ngp.util.Observable;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

public interface TransactionProcessor extends Observable<List<? extends Transaction>,TransactionProcessor.Event> {

    enum Event {
        REMOVED_UNCONFIRMED_TRANSACTIONS,
        ADDED_UNCONFIRMED_TRANSACTIONS,
        ADDED_CONFIRMED_TRANSACTIONS,
        RELEASE_PHASED_TRANSACTION,
        REJECT_PHASED_TRANSACTION
    }

    DbIterator<? extends Transaction> getAllUnconfirmedTransactions();

    DbIterator<? extends Transaction> getAllUnconfirmedTransactions(int from, int to);

    DbIterator<? extends Transaction> getAllUnconfirmedTransactions(String sort);

    DbIterator<? extends Transaction> getAllUnconfirmedTransactions(int from, int to, String sort);
    
    boolean hasUnconfirmedTransactions();

    Transaction getUnconfirmedTransaction(long transactionId);

    Transaction[] getAllWaitingTransactions();

    Transaction[] getAllBroadcastedTransactions();

    void clearUnconfirmedTransactions();

    void requeueAllUnconfirmedTransactions();

    void rebroadcastAllUnconfirmedTransactions();

    void broadcast(Transaction transaction) throws NgpException.ValidationException;

    void processPeerTransactions(JSONObject request) throws NgpException.ValidationException;

    void processLater(Collection<? extends Transaction> transactions);

    SortedSet<? extends Transaction> getCachedUnconfirmedTransactions(List<String> exclude);

    List<Transaction> restorePrunableData(JSONArray transactions) throws NgpException.NotValidException;
}
