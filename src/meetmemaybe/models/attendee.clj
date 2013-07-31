(ns meetmemaybe.models.attendee
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [meetmemaybe.models.meeting :as meeting]))

(def db-spec (System/getenv "DATABASE_URL"))

(defn show [id]
  (first (jdbc/query db-spec
    (sql/select * :attendee (sql/where {:id id})))))

(defn create! [attendee]
  (jdbc/insert! db-spec :attendee attendee))

(defn update! [id attendee]
  (jdbc/update! db-spec :attendee attendee (sql/where {:id id})))

(defn destroy! [id]
  (jdbc/delete! db-spec :attendee (sql/where {:id id})))

(defn meeting [id]
  (meeting/show ((show id) :meeting_id)))

(defn availabilities [id]
  (jdbc/query db-spec
    (sql/select * :availability (sql/where {:attendee_id id}))))