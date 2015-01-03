(ns dragon-curve.core
  (:require [clojure.string :as s]
            [reagent.core :as r]))

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
  (loop [folds folds
         curr 0
         res [0]]
    (if (seq folds)
      (let [curr (mod (+ (first folds) curr) 4)]
        (recur (next folds)
               curr
               (conj res curr)))
      res)))

(defn draw [dirs]
  (loop [dirs dirs
         curr [0 0]
         res [[0 0]]]
    (if (seq dirs)
      (let [[x y] curr
            curr (case (first dirs)
                   0 [x (- y 10)]
                   1 [(+ x 10) y]
                   2 [x (+ y 10)]
                   3 [(- x 10) y])]
        (recur (next dirs)
               curr
               (conj res curr)))
      res)))

(def folds (r/atom [1]))

(defn svg-component []
  (let [points (draw (navigate @folds))
        min-x (apply min (map first points))
        min-y (apply min (map last points))
        max-x (apply max (map first points))
        max-y (apply max (map last points))]
    [:svg {:xmlns "http://www.w3.org/2000/svg"
           :version "1.1"
           :width 400
           :height 400
           :viewBox (str "0 0 " (+ 2 (- max-x min-x)) " " (+ 2 (- max-y min-y)))}
     [:g
      [:polyline
       {:fill "none"
        :stroke "blue"
        :stroke-width "1"
        :points (s/join " " (map (fn [[x y]]
                                   (str (inc (- x min-x)) "," (inc (- y min-y))))
                                 points))}]]]))

(defn figure-component []
  [:figure
   [svg-component]
   (let [n (count @folds)]
     [:figcaption (str n " fold" (when (> n 1) "s"))])])

(defn main-component []
  [:div
   [figure-component]
   [:button {:type "button"
             :on-click #(swap! folds fold)}
    "Fold!"]])

(defn ^:export main []
  (let [el (.getElementById js/document "app")]
    (r/render-component [main-component] el)))
