(ns dragon-curve.core
  (:require [clojure.string :as s]
            [reagent.core :as reagent]))

(defn fold [coll]
 (loop [coll coll
        folds (cycle [-1 1])
        res []]
   (if (seq coll)
     (recur (next coll)
            (next folds)
            (conj res (first folds) (first coll)))
     (conj res (first folds)))))

(defn navigate [folds]
  (map #(nth (cycle [:n :w :s :e]) %)
       (reduce (fn [m d] (conj m (+ 4 (rem (+ d (last m) 4) 4))))
               [0]
               folds)))

(defn draw [dirs]
  (reduce (fn [m d]
            (let [[x y] (last m)]
              (conj m (case d
                        :n [x (- y 10)]
                        :w [(+ x 10) y]
                        :s [x (+ y 10)]
                        :e [(- x 10) y]))))
          [[0 0]]
          dirs))

(def folds (reagent/atom [1]))

(defn svg-component []
  (let [points (draw (navigate @folds))
        min-x (apply min (map first points))
        min-y (apply min (map last points))
        max-x (apply max (map first points))
        max-y (apply max (map last points))]
    [:svg {:xmlns "http://www.w3.org/2000/svg"
           :version "1.1"
           :width 500
           :height 500
           :viewBox (str "0 0 " (+ 2 (- max-x min-x)) " " (+ 2 (- max-y min-y)))}
     [:g
      [:polyline
       {:fill "none"
        :stroke "blue"
        :stroke-width "1" 
        :points (s/join " " (map (fn [[x y]]
                                   (str (inc (- x min-x)) "," (inc (- y min-y))))
                                 points))}]]]))

(defn main-component []
  [:div
   [svg-component]
   [:button {:type "button"
             :on-click #(swap! folds fold)}
    "Fold!"]])

(defn ^:export main []
  (let [el (.getElementById js/document "app")]
    (reagent/render-component [main-component] el)))
