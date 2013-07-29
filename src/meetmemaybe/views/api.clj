(ns meetmemaybe.views.api
  (:require [meetmemaybe.models.meeting :as meeting]
            [meetmemaybe.models.attendee :as attendee]
            [meetmemaybe.models.availability :as availability])
  (:use [noir.core :only [defpage]]
        [noir.response :only [json]]))

(defn str->int [str] (if (re-matches (re-pattern "\\d+") str) (read-string str)))

(defpage "/meetings/:id" {:keys [id]}
  (json (meeting/get (str->int id))))

(defpage [:post "/meetings/new"] {:keys [name description]}
  (meeting/add! {:name name :description description}))
