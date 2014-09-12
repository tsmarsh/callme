(ns callme.core
  (:require [clojure.string :as s]
            [clj-time.format :as f]
            [clj-time.core :as t]))

(def date-formater (f/formatter "yyyy/MM/dd"))

(defn split-on-empty-line [string]
  (s/split string #"\n\s*\n"))

(defn parse-chunk [chunk]
  (let [[interval date name & others] (s/split chunk #"\n")]
    {:interval (read-string interval)
     :date (f/parse date-formater date)
     :name name}))

(defn chunk-it [string]
  (map parse-chunk (split-on-empty-line string)))

(defn is-overdue [today chunk]
  (t/before? (t/plus (:date chunk) (t/days (:interval chunk))) today))

(defn overdue [chunks today]
  (filter (partial is-overdue today) chunks))
