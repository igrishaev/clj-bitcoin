(ns bitcoin.bitcoin
  (:require [bitcoin.api :refer [defapi]]
            [bitcoin.backend :refer [perform]]
            [bitcoin.json-rpc :refer [json-rpc]]))

(defapi getbalance
  "getbalance ( 'account' minconf include_watchonly )

If account is not specified, returns the server's total available balance.
If account is specified (DEPRECATED), returns the balance in the account.
Note that the account '' is not the same as leaving the parameter out.
The server total may be different to the balance in the default '' account."
  [account minconf include_watchonly])

(defapi getnewaddress
  "Returns a new address for account (if passed)."
  [account])

(defapi listtransactions
  "listtransactions ( 'account' count skip include_watchonly)

Returns up to 'count' most recent transactions skipping the first
'from' transactions for account 'account'."
  [account count skip include_watchonly])
