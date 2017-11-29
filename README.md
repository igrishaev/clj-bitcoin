# Bitcoin

[api-wiki]:https://en.bitcoin.it/wiki/Original_Bitcoin_client/API_calls_list

A Clojure wrapper upon the original C++ Bitcoin client. Implements [JSON-RPC
APIs][api-wiki] for the server.

## Installation

Add `[bitcoin "0.1.0-SNAPSHOT"]` to your dependencies.

## Usage

```clojure
(use 'bitcoin.api)

(def server (json-rpc "127.0.0.1" 8332 "ivan" "123"))

(getbalance server "ivan")
0.0

(getaccountaddress server "ivan")
1DiYHeMaZaX2aJpJ92J19gk3uar1KVDzGh

(getaddressesbyaccount server "ivan")
["1DiYHeMaZaX2aJpJ92J19gk3uar1KVDzGh"]

```

## License

[homepage]:http://grishaev.me/

Copyright © 2017 [Ivan Grishaev][homepage].

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
