(ns remind-me.views
  (:require [re-frame.core :as re-frame]
            [goog.dom :as gdom]
            [ajax.core :refer [POST]]
            [remind-me.motion :as motion]))

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

(defn reminders-list [reminders]
  (let [set-cls-inactive (fn [id]
                           (set! (.-className
                                   (gdom/getElement id))
                                 "removing"))]
    [:div
     [:ul#reminder-list
      (for [{:keys [id name]} @reminders]
        (let [el-id (str "li-id" id)]
          ^{:key id} [:li {:id       el-id
                           :on-click #(do
                                       (set-cls-inactive el-id)
                                       (re-frame/dispatch [:remove-reminder id]))}
                      name]))]]))

(def reminders-list-comp
  (reagent.core/reactify-component reminders-list))

(defn- will-leave []
  (js-obj "height"  (motion/spring 0)
          "opacity" (motion/spring 0)))

(defn- will-enter []
  (js-obj "height"  (motion/spring 0)
          "opacity" (motion/spring 1)))

(defn reminders-list-motion [reminders]
  [motion/TransitionMotion {:willLeave will-leave
                            :styles    (map (fn [{:keys [id name]}]
                                              (js-obj "key" id
                                                      "style" (js-obj "height" 10))) @reminders)}
   (fn [configs]
     (reagent.core/create-element reminders-list-comp
                                  #js {}
                                  [configs reminders]))])

(defn main-panel []
  (let [reminders (re-frame/subscribe [:reminders])]
    (fn []
      [:div
       [:h2#title.center "Tiny Reminder"]
       [add-form]
       [reminders-list reminders]])))
