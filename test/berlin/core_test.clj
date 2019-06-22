(ns berlin.core-test
  (:require [clojure.test :refer :all]
            [berlin.core :refer :all]))

(deftest berlin-hours
  (testing "hours"
    (is (= (hour 10) "RROO OOOO"))
    (is (= (hour 5) "ROOO OOOO"))
    (is (= (hour 13) "RROO RRRO"))
    (is (= (hour 4) "OOOO RRRR"))))

(deftest berlin-minutes
  (testing "minutes"
    (is (= (minute 10) "YYOOOOOOOOO OOOO"))
    (is (= (minute 15) "YYROOOOOOOO OOOO"))
    (is (= (minute 30) "YYRYYROOOOO OOOO"))
    (is (= (minute 45) "YYRYYRYYROO OOOO"))
    (is (= (minute 52) "YYRYYRYYRYO RROO"))))

(deftest berlin-seconds
  (testing "seconds"
    (is (= (seconds 10) "Y"))
    (is (= (seconds 11) "O"))
    (is (= (seconds 0) "Y"))))

(deftest berlin-clock
  (testing "clock"
    (is (= (berlin "10:33:10") "Y RROO OOOO YYRYYROOOOO YYYO"))
    (is (= (berlin "12:56:01") "O RROO RROO YYRYYRYYRYY YOOO"))))

