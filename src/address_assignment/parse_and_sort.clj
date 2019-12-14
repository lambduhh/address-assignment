(ns address-assignment.parse-and-sort
  (:require [clojure.string :as str]))

(def comma
  (slurp "/home/lorilynjmiller/IdeaProjects/address-assignment/resources/comma.txt"))

(def pipe
  (slurp "/home/lorilynjmiller/IdeaProjects/address-assignment/resources/pipe.txt"))

(def spaces
  (slurp "/home/lorilynjmiller/IdeaProjects/address-assignment/resources/tabs.txt"))

(defn split-by-line [txt]
  (clojure.string/split-lines txt))

; build predicates for dispatch function
(defn comma-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\,} chars)))))

(defn pipe-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\|} chars)))))

(defn confusing? [txt]
  (let [chars (seq txt)]
    (and (comma-separated? txt) (pipe-separated? txt))))

;test predicate functions on line
(comment
  (def txt (first (split-by-line comma)))
  (pipe-separated? txt)
  (comma-separated? txt)
  )

(defn line-based-dispatch-fn [line]
  (cond
    (pipe-separated? line) :pipe
    (comma-separated? line) :comma
    (confusing? line) :error
    :else :space))

(defmulti process-line line-based-dispatch-fn)

(defmethod process-line :pipe [line]
  (let [[last-name first-name gender fcolor dob :as line] (str/split line #"[|]")] ;need to escape pipe in regex w brackets bc pipe MEANS SOMETHING in regex
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

(defmethod process-line :error [line]
  (throw (Exception. "Error in data :( check "
                     line)))

(defmethod process-line :space [line]
  (let [[last-name first-name gender fcolor dob] (str/split line #"\t")]
    {:type     :space
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))

; fn to transform data into a list of hash-maps
(defn list-of-maps [txt]
  (let [collection-of-lines (split-by-line txt)]
    (map process-line collection-of-lines)))


(def commatxt (list-of-maps comma))
(def pipetxt (list-of-maps pipe))
(def spacetxt (list-of-maps spaces))

(defn combine-all [commatxt pipetxt spacetxt]
  (concat commatxt pipetxt spacetxt))

(def all-together-now
  (combine-all commatxt pipetxt spacetxt))

;finally all the data together in one data structure
all-together-now

(comment
  (process-line spaces)
  (list-of-maps txt)


  )

