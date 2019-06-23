(ns berlin.core-test
  (:require [clojure.test :refer :all]
            [berlin.core :refer :all]))

(deftest berlin-clock
  (testing "hours"
    (is (= ["RROO" "OOOO"] (transform :hours 10)))
    (is (= ["ROOO" "OOOO"] (transform :hours 5)))
    (is (= ["RROO" "RRRO"] (transform :hours 13)))
    (is (= ["OOOO" "RRRR"] (transform :hours 4))))
  (testing "minutes"
    (is (= ["YOOOOOOOOOO" "YYOO"] (transform :minutes 7)))
    (is (= ["YYOOOOOOOOO" "OOOO"] (transform :minutes 10)))
    (is (= ["YYROOOOOOOO" "OOOO"] (transform :minutes 15)))
    (is (= ["YYRYYROOOOO" "OOOO"] (transform :minutes 30)))
    (is (= ["YYRYYRYYROO" "OOOO"] (transform :minutes 45)))
    (is (= ["YYRYYRYYRYO" "YYOO"] (transform :minutes 52))))
  (testing "seconds"
    (is (= "Y" (transform :seconds 10)))
    (is (= "Y" (transform :seconds 0))))
    (is (= "O" (transform :seconds 11)))
  (testing "clock"
    (is (= "Y RROO OOOO YYRYYROOOOO YYYO" (berlin "10:33:10")))
    (is (= "O RROO RROO YYRYYRYYRYY YOOO" (berlin "12:56:01")))))

