(ns berlin.core
  (:require [clojure.string :as s]))

(defmulti transform (fn [type _] type))
(defmethod transform :hours [_ h]
  (let [number-of-fields 4
        active-hours (quot h 5)
        modulus (mod h 5)
        h1 (reduce (fn [acc v]
                     (str acc (if (< v active-hours) "R" "O"))) ""
                      (range number-of-fields))
        h2 (reduce (fn [acc v]
                     (str acc (if (< v modulus) "R" "O"))) ""
                  (range number-of-fields))]
    [h1 h2]))

(defmethod transform :minutes [_ m]
  (let [number-of-minute-fields 11
        number-of-minute-fields-2 4
        active-minutes (quot m 5)
        modulus (mod m 5)
        m1 (reduce (fn [acc v]
                     (str acc (cond
                                    (and (some #{v} [2 5 8]) (< v active-minutes )) "R"
                                    (< v active-minutes)                            "Y"
                                    :else                                           "O"
                                    ))) ""
                  (range number-of-minute-fields))
        m2 (reduce (fn [acc x]
                     (str acc (if (< x modulus) "Y" "O")))
                  "" (range number-of-minute-fields-2))]
    [m1 m2]))

(defmethod transform :seconds [_ s]
  (if (even? s) "Y" "O"))

(defn berlin [i]
  ;;TODO input validation
  ;;TODO make it a CLI
  (let [clock-order [:seconds :hours :minutes]
        clock-tokens (->> (s/split i #":")
                          (zipmap `(:hours :minutes :seconds)))]

    (->> clock-order
         (map (fn [k]
                (->> k
                    (get clock-tokens)
                    ((fn [x] (Integer/parseInt x)))
                    (transform k))))
         flatten
         (s/join " "))))
