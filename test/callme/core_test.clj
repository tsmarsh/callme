(ns callme.core-test
  (:use midje.sweet)
  (:use [callme.core]))

(def today "2011/12/11")

(def test-file
"30
2011/11/01
Recently Contacted

180
2011/11/01
Every Six-Months

60
2008/7/14
Really Overdue")


(facts "about `parsing a file`"
       (fact "it splits the file into chunks"
             (count (split-on-empty-line test-file)) => 3))
