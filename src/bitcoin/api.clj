(ns bitcoin.api
  (:require [bitcoin.backend :refer [perform]]
            [bitcoin.json-rpc :refer [map->JSON-RPC]]))

(defn getbalance
  "[account] [minconf=1]
  If [account] is not specified, returns the server's total available balance.
  If [account] is specified, returns the balance in the account."
  [backend & [account minconf]]
  (perform backend "getbalance" [(or account "*") (or minconf 1)]))

(defn getaccountaddress
  "<account>
  Returns the current bitcoin address for receiving payments
  to this account. If <account> does not exist, it will be created
  along with an associated new address that will be returned."
  [backend account]
  (perform backend "getaccountaddress" [account]))

(defn getaddressesbyaccount
  "<account>
  Returns the list of addresses for the given account."
  [backend account]
  (perform backend "getaddressesbyaccount" [account]))
