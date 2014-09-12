(ns callme.core
  (:require [clojure.string :as s]))

;;; This is an incorrect implementation, such as might be written by
;;; someone who was used to a Lisp in which an empty list is equal to
;;; nil.
(defn split-on-empty-line [string]
  (s/split string #"\n\s*\n"))
