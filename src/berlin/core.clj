(ns berlin.core
  (:require [clojure.string :as s]))


(defmulti transform (fn [x _] x))
(defmethod transform 0 [_ h]
  (let [active-hours (quot h 5);; block of 5 hours
        modulus (mod h 5);; single hours
        a (s/join (map (fn [v]
                         (if (< v active-hours) "R" "O")) 
                       (range 4)))
        b (s/join (map (fn [v]
                         (if (< v modulus) "R" "O"))
                       (range 4)))]
    (s/join  " " [a b])))

(defmethod transform 1 [_ m]
  (let [active-minutes (quot m 5)
        modulus (mod m 5)
        a (s/join (map (fn [v]
                         (cond
                           (and (some #{v} [2 5 8]) (< v active-minutes )) "R"
                           (< v active-minutes)                            "Y"
                           :else                                           "O"
                           ))
                       (range 11)))
        b (s/join (map (fn [x]
                         (if (< x modulus) "Y" "O"))
                       (range 4)))]
    (s/join " " [a b])))
(defmethod transform 2 [_ s]
  (if (even? s) "Y" "O"))

(defn berlin [i]
  (let [token (s/split i #":")
        berlin-tokens (->> token
                (map (fn [x] (Integer/parseInt x)))
                (map-indexed transform))
        secs (last berlin-tokens)
        rest (butlast berlin-tokens)]
    (s/join " " (conj rest secs))))
