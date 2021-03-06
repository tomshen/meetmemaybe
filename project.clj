(defproject meetmemaybe "0.0.1"
  :description "A webapp to coordinate meetings with other people."
  :url "https://github.com/tomshen/meetmemaybe"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [noir "1.3.0-beta3"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [postgresql/postgresql "9.1-901.jdbc4"]
                 [clj-time "0.5.1"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :cljsbuild {
    :builds [{:source-paths ["src/meetmemaybe/cljs"]
              :compiler {:output-to "resources/public/js/main.js"
                         :optimizations :advanced
                         :pretty-print false}}]}
  :main meetmemaybe.server)

