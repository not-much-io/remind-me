(ns remind-me.views
  (:require [re-frame.core :as re-frame]
            [goog.dom :as gdom]
            [ajax.core :refer [POST]]))

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

(def ctg (reagent.core/adapt-react-class js/React.addons.CSSTransitionGroup))

(defn reminders-list [reminders]
  (let [set-cls-inactive (fn [id]
                           (set! (.-className
                                   (gdom/getElement id))
                                 "removing"))]
    [:div
     [:ul#reminder-list
      [ctg {:transition-name "foo"}
       (for [{:keys [id name]} @reminders]
         (let [el-id (str "liid" id)]
           ^{:key id} [:li {:id       el-id
                            :on-click #(do
                                        (set-cls-inactive el-id)
                                        (re-frame/dispatch [:remove-reminder id]))}
                       name]))]
      ]]))

(defn main-panel []
  (let [reminders (re-frame/subscribe [:reminders])]
    (fn []
      [:div
       [:h2#title.center "Tiny Reminder"]
       [add-form]
       [reminders-list reminders]])))
