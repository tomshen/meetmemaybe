(ns meetmemaybe.models.availability
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [meetmemaybe.models.attendee :as attendee]
            [meetmemaybe.models.meeting :as meeting]
            [clj-time.coerce :as timec]))

(def db-spec (System/getenv "DATABASE_URL"))

(defn format-date-entry [m k]
  (if (contains? m k)
    (assoc m k (timec/to-timestamp (timec/from-string (m k))))
    m))

(defn format-availability [availability]
  (format-date-entry (format-date-entry availability :datetime_start) :datetime_end))

(defn show [id]
  (first (jdbc/query db-spec
    (sql/select * :availability (sql/where {:id id})))))

(defn create! [availability]
  (jdbc/insert! db-spec :availability (format-availability availability)))

(defn update! [id availability]
  (jdbc/update! db-spec :availability (format-availability availability) (sql/where {:id id})))

(defn destroy! [id]
  (jdbc/delete! db-spec :availability (sql/where {:id id})))

(defn attendee [id]
  (attendee/show ((show id) :attendee_id)))

(defn meeting [id]
  (meeting/show ((attendee id) :meeting_id)))