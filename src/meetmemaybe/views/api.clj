(ns meetmemaybe.views.api
  (:require [meetmemaybe.models.meeting :as meeting]
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
(defpage meeting "/meetings/:id" {:keys [id]}
  (json (meeting/show (parseInt id))))

(defpage create-meeting [:post "/meetings/new"] meeting
  (json (meeting/create! meeting)))

(defpage update-meeting [:put "/meetings/:id"] {:keys [id] :as meeting}
  (meeting/update! (parseInt id) (dissoc meeting :id)))

(defpage meeting-attendees "/meetings/:id/attendees" {:keys [id]}
  (json (meeting/attendees (parseInt id))))

; Attendees
(defpage attendee "/attendees/:id" {:keys [id]}
  (json (attendee/show (parseInt id))))

(defpage create-attendee [:post "/attendees/new"] attendee
  (json (attendee/create! (assoc attendee :meeting_id (parseInt (attendee :meeting_id))))))

(defpage update-attendee [:put "/attendees/:id"] {:keys [id] :as attendee}
  (attendee/update! (parseInt id) (assoc (dissoc attendee :id) :meeting_id (parseInt (attendee :meeting_id)))))

(defpage attendee-meeting "/attendees/:id/meeting" {:keys [id]}
  (json (attendee/meeting (parseInt id))))

(defpage attendee-availabilities "/attendees/:id/availabilities" {:keys [id]}
  (json (attendee/availabilities (parseInt id))))

; Availabilities
(defpage availability "/availabilities/:id" {:keys [id]}
  (json (availability/show (parseInt id))))

(defpage create-availability [:post "/availabilities/new"] availability
  (json (availability/create! (assoc availability :attendee_id (parseInt (availability :attendee_id))))))

(defpage update-availability [:put "/availabilities/:id"] {:keys [id] :as availability}
  (attendee/update! (parseInt id) (assoc (dissoc availability :id) :attendee_id (parseInt (availability :attendee_id)))))

(defpage availability-attendee "/availabilities/:id/attendee" {:keys [id]}
  (json (availability/attendee (parseInt id))))

(defpage availability-meeting "/availabilities/:id/meeting" {:keys [id]}
  (json (availability/meeting (parseInt id))))