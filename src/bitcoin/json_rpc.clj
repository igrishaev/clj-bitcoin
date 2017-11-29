(ns bitcoin.json-rpc
  (:require [clj-http.client :as client]
            [cheshire.core :refer [parse-string]]
            [clojure.tools.logging :as log]))

(defn rpc-call
  [config method args]
  (let [{:keys [id
                host
                port
                user
                pass
                timeout]} config
        url (format "http://%s:%s/" host port)
        payload {:jsonrpc "1.0"
                 :id (or id "bitcoin.json-rpc") ;; todo
                 :method method
                 :params args}
        params {:method :post
                :url url
                :basic-auth [user pass]
                :form-params payload
                :throw-exceptions false
                :content-type :json
                :as :json}
        _ (log/debugf "JSON-RPC request: %s %s" url payload)
        response (client/request params)
        {:keys [status body]} response]

    (if (= status 200)
      (:result body)
      (let [body (parse-string body true)
            {:keys [code message]} (:error body)
            exc-message (format "JSON-RPC failure: %s %s %s %s %s %s"
                                status url method args code message)
            _ (log/error exc-message)]
        (throw (Exception. exc-message))))))


(defn getbalance
  "[account] [minconf=1]
  If [account] is not specified, returns the server's total available balance.
  If [account] is specified, returns the balance in the account."
  ([cfg]
   (getbalance cfg "*"))
  ([cfg account]
   (getbalance cfg account 1))
  ([cfg account minconf]
   (rpc-call cfg "getbalance" [account minconf])))

(defn getaccountaddress
  "<account>
  Returns the current bitcoin address for receiving payments
  to this account. If <account> does not exist, it will be created
  along with an associated new address that will be returned."
  [cfg account]
  (rpc-call cfg "getaccountaddress" [account]))

(defn getaddressesbyaccount
  "<account>
  Returns the list of addresses for the given account."
  [cfg account]
  (rpc-call cfg "getaddressesbyaccount" [account]))
