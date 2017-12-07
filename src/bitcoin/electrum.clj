(ns bitcoin.electrum
  (:require [bitcoin.backend :refer [perform]]
            [bitcoin.json-rpc :refer [json-rpc]]))

(defmacro defapi
  [api doc args]
  `(defn ~api ~doc
     [~'rpc & [{:keys ~args :as ~'params}]] ;; too fix empty args
     (perform ~'rpc ~(name api) (or ~'params {}))))

(defapi getbalance
  "Return the balance of your wallet."
  [])

(defapi listaddresses
  "List wallet addresses. Returns the list of all addresses
in your wallet. Use optional arguments to filter the results."
  [receiving change labels frozen unused funded])

(defapi createnewaddress
  "Create a new receiving address, beyond the gap limit of the wallet."
  [])

(defapi history
  "Wallet history. Returns the transaction history of your wallet."
  [])

(defapi addrequest
  "Create a payment request, using the first unused address of the wallet. The
address will be condidered as used after this operation. If no payment is
received, the address will be considered as unused if the payment request is
deleted from the wallet."
  [amount memo expiration force])
