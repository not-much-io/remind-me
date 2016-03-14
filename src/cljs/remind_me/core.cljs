(ns remind-me.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [remind-me.handlers]
              [remind-me.subs]
              [remind-me.views :as views]
              [remind-me.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:sync-reminders])
  (mount-root))
