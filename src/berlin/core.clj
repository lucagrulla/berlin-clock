(ns berlin.core
  (:require [clojure.string :as s]))

(defn hour [h]
  (let [active-hours (quot h 5);; block of 5 hours
        modulus (mod h 5);; single hours
        a (reduce (fn [acc v]
                    (str acc (if (< v active-hours) "R" "O"))) 
                  "" (range 4))
        b (reduce (fn [acc v]
                    (str acc (if (< v modulus) "R" "O")))
                  "" (range 4))]

    (s/join  " " [a b])))

(defn minute [m]
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

(defn seconds [s]
  (if (even? s) "Y" "O"))

(defn berlin [i]
  (let [token (s/split i #":")
        dd (map-indexed (fn [idx item]
                          (let [i (Integer/parseInt item)]
                            (case idx 
                              0 (hour i)
                              1 (minute i)
                              2 (seconds i))))
                        token)
        secs (last dd)
        rest (butlast dd)]
    (s/join " " (conj rest secs))
    ))






