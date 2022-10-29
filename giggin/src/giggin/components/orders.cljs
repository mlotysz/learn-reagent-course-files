(ns giggin.components.orders
  (:require
    [giggin.state :as state]))

(defn orders
  []
  [:aside
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
            [:div.price total]]])))]]])
