(defproject meetmemaybe "0.0.1"
  :description "A webapp to coordinate meeting with other people."
  :url "https://github.com/tomshen/meetmemaybe"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta3"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [postgresql/postgresql "9.1-901.jdbc4"]]
  :main meetmemaybe.server)

