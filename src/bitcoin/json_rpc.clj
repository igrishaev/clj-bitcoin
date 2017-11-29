(ns bitcoin.json-rpc
  (:require [bitcoin.backend :as backend]
            [clj-http.client :as client]
            [cheshire.core :refer [parse-string]]
            [clojure.tools.logging :as log])
  (:import bitcoin.backend.IBackend))

(defrecord JSON-RPC
    [host port user pass id timeout]

  IBackend

  (perform [_ method args]
    (let [url (format "http://%s:%s/" host port)
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
          (throw (Exception. exc-message)))))))
