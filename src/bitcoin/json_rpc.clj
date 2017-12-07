(ns bitcoin.json-rpc
  (:require [bitcoin.backend :as backend]
            [clj-http.client :as client]
            [cheshire.core :refer [parse-string]]
            [clojure.tools.logging :as log])
  (:import bitcoin.backend.IBackend))

(defrecord JSON-RPC
    [port host user pass secure version timeout id]

  IBackend

  (perform [_ method args]
    (let [schema (if secure "https" "http")
          url (format "%s://%s:%s/" schema host port)
          auth (when (and user pass) [user pass])
          payload {:jsonrpc version
                   :id id
                   :method method
                   :params args}
          params {:method :post
                  :url url
                  :basic-auth auth
                  :form-params payload
                  :throw-exceptions false
                  :content-type :json
                  :as :json}
          _ (log/debugf "JSON-RPC request: %s %s" url payload)
          response (client/request params)
          {:keys [status body]} response
          body (if (= status 200)
                 body
                 (parse-string body true))]

      (if (and (= status 200)
               (-> body :error empty?))
        (:result body)
        (let [{:keys [code message]} (:error body)
              exc-message (format "JSON-RPC failure: %s %s %s %s %s %s"
                                  status url method args code message)
              _ (log/error exc-message)]
          (throw (Exception. exc-message)))))))

(defn json-rpc
  [port & [{:keys [host user pass secure version timeout id] :as opt}]]
  (map->JSON-RPC
   {:host (or host "127.0.0.1")
    :port port
    :user user
    :pass pass
    :timeout (or timeout 5)
    :secure (or secure false)
    :version (or version "1.0")
    :id (or id "clojure-bitcoin")}))
