(ns bitcoin.api)

(defmacro defapi
  [api doc args]
  `(defn ~api ~doc
     [~'rpc & [{:keys ~args :as ~'params}]] ;; too fix empty args
     (perform ~'rpc ~(name api) (or ~'params {}))))
