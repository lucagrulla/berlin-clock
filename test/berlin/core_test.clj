(ns berlin.core-test
  (:require [clojure.test :refer :all]
            [berlin.core :refer :all]))

(deftest berlin-clock
  (testing "hours"
    (is (= (transform :hours 10) ["RROO" "OOOO"]))
    (is (= (transform :hours 5) ["ROOO" "OOOO"]))
    (is (= (transform :hours 13) ["RROO" "RRRO"]))
    (is (= (transform :hours 4) ["OOOO" "RRRR"])))
  (testing "minutes"
    (is (= (transform :minutes 7) ["YOOOOOOOOOO" "YYOO"]))
    (is (= (transform :minutes 10) ["YYOOOOOOOOO" "OOOO"]))
    (is (= (transform :minutes 15) ["YYROOOOOOOO" "OOOO"]))
    (is (= (transform :minutes 30) ["YYRYYROOOOO" "OOOO"]))
    (is (= (transform :minutes 45) ["YYRYYRYYROO" "OOOO"]))
    (is (= (transform :minutes 52) ["YYRYYRYYRYO" "YYOO"])))
  (testing "seconds"
    (is (= (transform :seconds 10) "Y"))
    (is (= (transform :seconds 0) "Y")))
    (is (= (transform :seconds 11) "O"))
  (testing "clock"
    (is (= (berlin "10:33:10") "Y RROO OOOO YYRYYROOOOO YYYO"))
    (is (= (berlin "12:56:01") "O RROO RROO YYRYYRYYRYY YOOO"))))

