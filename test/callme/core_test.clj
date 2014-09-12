(ns callme.core-test
  (:use midje.sweet)
  (:use [callme.core])
  (:require [clj-time.core :as t]))

(def today (t/date-time 2011 12 11))

(def test-file
"30
2011/12/01
Recently Contacted

180
2011/11/01
Every Six-Months

60
2008/7/14
Really Overdue")

(def test-chunk
"30
2011/11/01
Test Chunk")

(fact "it splits the file into chunks"
             (count (split-on-empty-line test-file)) => 3)

(fact "it can extract the interval from a chunk"
      (:interval (parse-chunk test-chunk)) => 30)

(fact "it can extract the data from a chunk"
      (:date (parse-chunk test-chunk)) => (t/date-time 2011 11 1))

(fact "it can extract the name from a chunk"
      (:name (parse-chunk test-chunk)) => "Test Chunk")

(facts "about 'chunk-it'"
  (let [chunks (chunk-it test-file)]
    (fact "there should be 3 chunks"
        (count chunks) => 3)
    (fact "this first chunk should be Recently Contacted"
        (first chunks) => {:name "Recently Contacted"
                           :date (t/date-time 2011 12 1)
                           :interval 30})
    (fact "last chunk should be Really Overdue"
        (last chunks) => {:name "Really Overdue"
                          :date (t/date-time 2008 7 14)
                          :interval 60})))

(fact "should provide list of overdue contacts"
      (overdue (chunk-it test-file) today) => [{:name "Really Overdue"
                                                :date (t/date-time 2008 7 14)
                                                :interval 60}])


