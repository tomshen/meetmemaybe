(ns meetmemaybe.models.meeting
  (:require [clojure.java.jdbc :as sql]))

(defn get [id]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from meeting where id = ?" id]
      (into [] results))))

(defn add! [meeting]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :meeting [:name :description] [(meeting :name) (meeting :description)])))