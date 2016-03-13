(ns remind-me.db
  (:require [ajax.core :refer [GET]]))

(def db (atom {}))
