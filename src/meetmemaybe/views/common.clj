(ns meetmemaybe.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css include-js html5]]))

(defpartial layout [& content]
            (html5
              [:head
                [:meta {:charset "utf-8"}]
                [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
                [:title "Meet Me Maybe"]
                (include-css "/css/normalize.css")]
              [:body
                [:header [:h1 "Meet Me Maybe"]]
                [:div#container content]
                (include-js "/js/main.js")]))
