(ns remind-me.views
  (:require [re-frame.core :as re-frame]
            [goog.dom :as gdom]
            [ajax.core :refer [POST]]
            [timothypratley.reanimated.core :as anim]))

(defn add-form []
  (let [get-reminder-data #(.-value (gdom/getElement "add-reminder-input"))
        clear-input       #(set! (.-value (gdom/getElement "add-reminder-input")) "")
        add-reminder      #(re-frame/dispatch [:add-reminder %1])]
    [:div.row
     [:div.twelve.columns
      [:label
       {:for "add-reminder-input"} "New reminder"]
      [:input.u-full-width
       {:id          "add-reminder-input"
        :type        "text"
        :placeholder "Remember to"}]]
     [:button.button-primary.u-pull-right
      {:on-click #(do (add-reminder
                        (get-reminder-data))
                      (clear-input))} "Add"]]))

(defn reminders-list [loading reminders]
  [anim/pop-when
   (not @loading)
   [:ul#reminder-list
    (for [{:keys [id name]} @reminders]
      ^{:key id}
      [:li {:on-click #(re-frame/dispatch [:remove-reminder id])}
       name])]])

(defn main-panel []
  (let [reminders (re-frame/subscribe [:reminders])
        loading   (re-frame/subscribe [:loading?])]
    (fn []
      [:div
       [:h2#title.center "Tiny Reminder"]
       [add-form]
       [reminders-list loading reminders]])))
