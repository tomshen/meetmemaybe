(ns meetmemaybe.models.migration
  (:require [clojure.java.jdbc :as sql]))

(defn create-meeting []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :meeting
      [:id :serial "primary key"]
      [:name "varchar(255)" "not null"]
      [:description "text"])))

(defn create-attendee []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :attendee
      [:id :serial "primary key"]
      [:meeting_id :integer "not null references meeting(id)"]
      [:first_name "varchar(255)" "not null"]
      [:last_name "varchar(255)"])))

(defn create-availability []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :availability
      [:id :serial "primary key"]
      [:attendee_id :integer "not null references attendee(id)"]
      [:datetime_start "timestamp with time zone" "not null"]
      [:datetime_end "timestamp with time zone" "not null"])))

(defn -main []
  (print "Creating database structure...") (flush)
  (create-meeting)
  (create-attendee)
  (create-availability)
  (println " done"))