(ns berlin.core
  (:require [clojure.string :as s]))

(defmulti transform (fn [type _] type))
(defmethod transform :hours [_ h]
  (let [active-hours (quot h 5);; block of 5 hours
        modulus (mod h 5);; single hours
        a (s/join (map (fn [v]
                         (if (< v active-hours) "R" "O")) 
                       (range 4)))
        b (s/join (map (fn [v]
                         (if (< v modulus) "R" "O"))
                       (range 4)))]
    (s/join  " " [a b])))

(defmethod transform :minutes [_ m]
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
(defmethod transform :seconds [_ s]
  (if (even? s) "Y" "O"))

(defn berlin [i]
  ;;TODO input validation
  (let [clock-tokens (->> (s/split i #":")
                          (map (fn [x] (Integer/parseInt x)))
                          (zipmap `(:hours :minutes :seconds))
                          (reduce-kv (fn [acc k v] (assoc acc k (transform k v))) {}))
        {:keys [:seconds :hours :minutes]} clock-tokens]
    (s/join " " [seconds hours minutes])))
