(ns address-assignment.string-manipulation
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

(defn tab-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\tab} chars)))))                  ;str literal "\t" vs. char literal "\tab"


;test predicate functions on line
(comment
  (def txt (first (split-by-line spaces)))
  (pipe-separated? txt)
  (comma-separated? txt)
  (tab-separated? txt)

  )

(defn line-based-dispatch-fn [line]
  (cond
    (pipe-separated? line) :pipe
    (comma-separated? line) :comma
    (tab-separated? line) :space
    :else :error))

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


(defmethod process-line :space [line]
  (let [[last-name first-name gender fcolor dob] (str/split line #"\t")]
    {:type     :space
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :error [line]
  (throw (Exception. (str "Error in data :( check "
                          line))))


; fn to transform data into a list of hash-maps
(defn list-of-maps [txt]
  (let [collection-of-lines (split-by-line txt)]
    (map process-line collection-of-lines)))


(defn combine-all [args]
  (apply concat args))


;finally all the data together in one data structure

(comment


  (def commatxt (list-of-maps comma))
  (def pipetxt (list-of-maps pipe))
  (def spacetxt (list-of-maps spaces))

  (def all-together-now
    (combine-all commatxt pipetxt spacetxt))

  all-together-now

  )

