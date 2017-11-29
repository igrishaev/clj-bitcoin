(ns bitcoin.backend)

(defprotocol IBackend

  (perform [this method args]))
