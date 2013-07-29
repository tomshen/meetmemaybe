(ns meetmemaybe.views.home
  (:require [meetmemaybe.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/" []
  (common/layout [:p "Welcome to Meet Me Maybe!"]))
