(ns remind-me.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found]]
            [ring.util.response :refer [file-response]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [remind-me.db :refer [get-reminders
                                  insert-reminder
                                  delete-reminder-with-id
                                  db-spec]]
            [clojure.data.json :as json]))

(defroutes app-routes
           (GET "/" []
             (file-response "index.html" {:root "resources/public"}))
           (GET "/get-reminders" []
             {:status   200
              :headers  {"Content-Type" "application/json"}
              :body     (json/write-str (get-reminders db-spec))})
           (POST "/add-reminder"  {params :params}
             (insert-reminder db-spec params)
             {:status   200})
           (POST "/remove-reminder" {params :params}
             (delete-reminder-with-id db-spec params)
             {:status   200})
           (not-found "Ups.."))

(def app (->
           #'app-routes
           wrap-reload
           wrap-json-response
           wrap-keyword-params
           wrap-json-params))
