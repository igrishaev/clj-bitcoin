(defproject bitcoin "0.1.0-SNAPSHOT"
  :description "Clojure bindings to the standard Bitcoin client."
  :url "https://github.com/igrishaev/clj-bitcoin"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[clj-http "2.3.0"]
                 [cheshire "5.6.3"]
                 [org.clojure/tools.logging "0.3.1"]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [log4j/log4j "1.2.17"]]}
             :uberjar {:aot :all}})
