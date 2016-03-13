(ns remind-me.handlers
  (:require [re-frame.core :refer [register-handler dispatch]]
            [remind-me.ajax-requests :as requests]))

(register-handler
  :handle-reminders
  (fn [db [_ resp]]
    (-> db
        (assoc :reminders resp)
        (assoc :loading?  false))))

(register-handler
  :handle-reminders-error
  (fn [db _]
    (-> db
        (assoc :loading? false)
        (assoc :error "Aww shit!"))))

(register-handler
  :sync-db
  (fn [db _]
    (requests/get-reminders-request)
    (assoc db :loading? true)))

(register-handler
  :add-reminder
  (fn [db [_ name]]
    (requests/add-reminder-request name)
    (requests/get-reminders-request)
    (comment
      "handle in cljs?"
      (update-in db [:reminders] #(conj % {:id (random-uuid) :name name})))
    db))

(register-handler
  :remove-reminder
  (fn [db [_ id]]
    (requests/remove-reminder-request id)
    (requests/get-reminders-request)
    (comment
      "handle in cljs?"
      (update-in db [:reminders] (fn [reminders]
                                   (filter #(not= id (:id %))
                                           reminders))))
    db))