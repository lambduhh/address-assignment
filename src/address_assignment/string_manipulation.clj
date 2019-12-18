(ns address-assignment.string-manipulation
  (:require [clojure.string :as str]))


(defn split-by-line [txt]
  (clojure.string/split-lines txt))


; build predicates for dispatch function
(defn comma-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\,} chars)))))


(defn pipe-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\|} chars)))))


(defn tab-separated? [txt]
  (let [chars (seq txt)]
    ;str literal "\t" vs. char literal "\tab"
    (= 4 (count (filter #{\tab} chars)))))


(comment
  (def spaces (slurp "resources/tabs.txt"))
  ;test predicate functions on line
  (def txt (first (split-by-line spaces)))
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


(defmethod process-line :pipe [line]
  ;need to escape pipe in regex w brackets bc pipe MEANS SOMETHING in regex
  (let [[last-name first-name gender fcolor dob :as line] (str/split line #"[|]")]
    ; WYSIWYG
    {:type     :pipe
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :comma [line]
  (let [[last-name first-name gender fcolor dob] (str/split line #"[,]")]
    {:type     :comma
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :space [line]
  (let [[last-name first-name gender fcolor dob] (str/split line #"\t")]
    {:type     :space
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :error [line]
  (throw (Exception. (str "Error in data :( check " line))))


(defn list-of-maps
  "Transform data into a list of hash-maps"
  [txt]
  (let [collection-of-lines (split-by-line txt)]
    (map process-line collection-of-lines)))


(defn combine-all [args]
  (apply concat args))


(comment
  (def comma (slurp "resources/comma.txt"))
  (def pipe (slurp "resources/pipe.txt"))
  (def spaces (slurp "resources/tabs.txt"))

  ;finally all the data together in one data structure
  (def commatxt (list-of-maps comma))
  (def pipetxt (list-of-maps pipe))
  (def spacetxt (list-of-maps spaces))
  (def all-together-now
    (combine-all commatxt pipetxt spacetxt))
  all-together-now)

