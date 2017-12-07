(ns bitcoin.bitcoin
  (:require [bitcoin.backend :refer [perform]]
            [bitcoin.json-rpc :refer [json-rpc]]))

(defmacro defapi
  [api doc args]
  `(defn ~api ~doc
     [~'rpc & {:keys ~args :as ~'params}]
     (perform ~'rpc ~(name api) ~'params)))

(defapi getbalance
  "getbalance ( 'account' minconf include_watchonly )

If account is not specified, returns the server's total available balance.
If account is specified (DEPRECATED), returns the balance in the account.
Note that the account '' is not the same as leaving the parameter out.
The server total may be different to the balance in the default '' account."
  [account minconf include_watchonly])

#_(defapi getbalance
  "sdfsdf"
  [account minconf])

#_(defn getbalance
  "tests"
  [rpc & {:keys [account minconf] :as params}]
  (perform "getbalance" params))

#_(defn getbalance
  "[account] [minconf=1]
  If [account] is not specified, returns the server's total available balance.
  If [account] is specified, returns the balance in the account."
  [backend & [account minconf]]
  (perform backend "getbalance" [(or account "*") (or minconf 1)]))

#_(defn getaccountaddress
  "<account>
  Returns the current bitcoin address for receiving payments
  to this account. If <account> does not exist, it will be created
  along with an associated new address that will be returned."
  [backend account]
  (perform backend "getaccountaddress" [account]))

#_(defn getaddressesbyaccount
  "<account>
  Returns the list of addresses for the given account."
  [backend account]
  (perform backend "getaddressesbyaccount" [account]))
