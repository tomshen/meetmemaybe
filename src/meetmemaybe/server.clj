(ns meetmemaybe.server
  (:require [noir.server :as server]))

(server/load-views-ns 'meetmemaybe.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "5000"))]
    (server/start port {:mode mode
                        :ns 'meetmemaybe})))

