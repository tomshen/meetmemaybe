(ns meetmemaybe.models.meeting
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(def db-spec (System/getenv "DATABASE_URL"))

(defn show [id]
  (first (jdbc/query db-spec
    (sql/select * :meeting (sql/where {:id id})))))

(defn create! [meeting]
  (jdbc/insert! db-spec :meeting meeting))

(defn update! [id meeting]
  (jdbc/update! db-spec :meeting meeting (sql/where {:id id})))

(defn destroy! [id]
  (jdbc/delete! db-spec :meeting (sql/where {:id id})))

(defn attendees [id]
  (jdbc/query db-spec
    (sql/select * :attendee (sql/where {:meeting_id id}))))