(ns meetmemaybe.views.api
  (:require [clojure.string :as string]
            [meetmemaybe.models.meeting :as meeting]
            [meetmemaybe.models.attendee :as attendee]
            [meetmemaybe.models.availability :as availability])
  (:use [noir.core :only [defpage]]
        [noir.response :only [json]]))

(defn parseInt [str]
  (if (number? str)
    str
    (if (re-matches (re-pattern "\\d+") str)
      (read-string str))))

; Meetings
(defn valid-meeting? [meeting]
  (and (contains? meeting :name)
       (not (string/blank? (meeting :name)))))

(defpage meeting "/meetings/:id" {:keys [id]}
  (json (meeting/show (parseInt id))))

(defpage create-meeting [:post "/meetings/new"] meeting
  (if (valid-meeting? meeting)
      (json (meeting/create! meeting))
      {:status 400 :body "Meeting must have a name."}))

(defpage update-meeting [:put "/meetings/:id"] {:keys [id] :as meeting}
  (if (valid-meeting? meeting)
      (meeting/update! (parseInt id) (dissoc meeting :id))
      {:status 400 :body "Meeting must have a name."}))

(defpage meeting-attendees "/meetings/:id/attendees" {:keys [id]}
  (json (meeting/attendees (parseInt id))))

; Attendees
(defn valid-attendee? [attendee]
  (and (contains? attendee :meeting_id)
       (contains? attendee :first_name)
       (number? (parseInt (attendee :meeting_id)))
       (not (string/blank? (attendee :first_name)))))

(defpage attendee "/attendees/:id" {:keys [id]}
  (json (attendee/show (parseInt id))))

(defpage create-attendee [:post "/attendees/new"] attendee
  (if (valid-attendee? attendee)
    (json (attendee/create!
      (assoc attendee :meeting_id (parseInt (attendee :meeting_id)))))
    {:status 400 :body "Attendee must have first name and associated existing meeting."}))

(defpage update-attendee [:put "/attendees/:id"] {:keys [id] :as attendee}
  (if (valid-attendee? attendee)
    (attendee/update! (parseInt id) (assoc (dissoc attendee :id) :meeting_id (parseInt (attendee :meeting_id))))
    {:status 400 :body "Attendee must have first name and associated existing meeting."}))

(defpage attendee-meeting "/attendees/:id/meeting" {:keys [id]}
  (json (attendee/meeting (parseInt id))))

(defpage attendee-availabilities "/attendees/:id/availabilities" {:keys [id]}
  (json (attendee/availabilities (parseInt id))))

; Availabilities
(defn valid-availability? [availability]
  (and (contains? availability :attendee_id)
       (contains? availability :datetime_start)
       (contains? availability :datetime_end)
       (number? (parseInt (availability :attendee_id)))
       (< (compare (availability :datetime_start) (availability :datetime_end)) 0)))

(defpage availability "/availabilities/:id" {:keys [id]}
  (json (availability/show (parseInt id))))

(defpage create-availability [:post "/availabilities/new"] availability
  (if (valid-availability? availability)
    (json (availability/create!
      (assoc availability :attendee_id (parseInt (availability :attendee_id)))))
    {:status 400 :body "Availability must have end time after start time and associated existing attendee."}))

(defpage update-availability [:put "/availabilities/:id"] {:keys [id] :as availability}
  (if (valid-availability? availability)
    (attendee/update! (parseInt id)
      (assoc (dissoc availability :id) :attendee_id (parseInt (availability :attendee_id))))
    {:status 400 :body "Availability must have end time after start time and associated existing attendee."}))

(defpage availability-attendee "/availabilities/:id/attendee" {:keys [id]}
  (json (availability/attendee (parseInt id))))

(defpage availability-meeting "/availabilities/:id/meeting" {:keys [id]}
  (json (availability/meeting (parseInt id))))