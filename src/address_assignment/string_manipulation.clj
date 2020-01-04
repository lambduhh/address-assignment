(ns address-assignment.string-manipulation
  (:require [clojure.string :as str]))

(defn ^:private separated-by? [sep txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{sep} chars)))))


;; build predicates for dispatch function
(defn comma-separated? [txt]
  (separated-by? \, txt))

(defn pipe-separated? [txt]
  (separated-by? \|, txt))

(defn tab-separated? [txt]
  (separated-by? \tab, txt))


(comment
  (def spaces (slurp "resources/tabs.txt"))
  (separated-by? \tab txt)
  ;test predicate functions on line
  (def txt (first (clojure.string/split-lines spaces)))
  (pipe-separated? txt)
  (comma-separated? txt)
  (tab-separated? txt))


(defn line-based-dispatch-fn [line]
  (cond
    (pipe-separated? line) :pipe
    (comma-separated? line) :comma
    (tab-separated? line) :space
    :else :error))


(defmulti process-line line-based-dispatch-fn)

(defn split-line-by [sep line]
  (let [[last-name first-name gender fcolor dob] (str/split line (re-pattern (str "[" sep "]")))]
    {:type     :comma
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :pipe [line]
  (split-line-by \| line))


(defmethod process-line :comma [line]
  (split-line-by \, line))


(defmethod process-line :space [line]
  (split-line-by \tab line))


(defmethod process-line :error [line]
  (throw (Exception. (str "Error in data :( check " line))))


(comment
  (def comma (slurp "resources/comma.txt"))
  (def pipe (slurp "resources/pipe.txt"))
  (def spaces (slurp "resources/tabs.txt"))

  ;finally all the data together in one data structure
  (def commatxt (map process-line (clojure.string/split-lines comma)))
  (def pipetxt (map process-line (clojure.string/split-lines pipe)))
  (def spacetxt (map process-line (clojure.string/split-lines spaces)))
  (def all-together-now
    (concat commatxt pipetxt spacetxt))
  all-together-now

  )
