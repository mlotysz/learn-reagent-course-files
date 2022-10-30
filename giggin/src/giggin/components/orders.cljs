(ns giggin.components.orders
  (:require
    [giggin.state :as state]
    [giggin.helpers :as h]))

(defn- total
  []
  (->>
    @state/orders
    (map (fn [[id quant]] (* quant (get-in @state/gigs [id :price]))))
    (reduce +)))

(defn orders
  []
  [:aside
   (if (empty? @state/orders)
     [:div.empty
      [:div.title "You don't have orders"]
      [:div.subtitle "Click on a + to add an order"]]
     [:div.order
      [:div.body
       (doall
        (for [[id quant] @state/orders]
          (let [item (get @state/gigs id)
                {:keys [img title price]} item
                total (* price quant)]
            [:div.item {:key id}
             [:div.img
              [:img {:src img :alt title}]]
             [:div.content
              [:p.title (str title " \u00D7 " quant)]]
             [:div.action
              [:div.price (h/format-price total)]
              [:button.btn-btn--link.tooltip
               {:data-tooltip "Remove"
                :on-click #(swap! state/orders dissoc id)}
               [:i.icon.icon--cross]]]])))]
      [:div.total
       [:hr]
       [:div.item
        [:div.content "Total"]
        [:div.action
         [:div.price (h/format-price (total))]]
        [:button.btn.btn--link.tooltip
         {:data-tooltip "Remove all"
          :on-click #(reset! state/orders {})}
         [:i.icon.icon--delete]]]]])])
