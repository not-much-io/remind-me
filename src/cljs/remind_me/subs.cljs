(ns remind-me.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :reminders
 (fn [db]
   (reaction (:reminders @db))))

(re-frame/register-sub
  :loading?
  (fn [db]
    (reaction (:loading? @db))))
