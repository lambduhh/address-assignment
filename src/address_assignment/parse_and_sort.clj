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

(defn comma-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\,} chars)))))

(defn pipe-separated? [txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{\|} chars)))))

(defn confusing? [txt]
  (let [chars (seq txt)]
    (and (comma-separated? txt) (pipe-separated? txt))))

(comment
  (def txt (first (split-by-line pipe)))
  (pipe-separated? txt)
  )

(defn line-based-dispatch-fn [line]
  (cond
    (pipe-separated? line) :pipe
    (comma-separated? line) :comma
    (confusing? line) :error
    :else :space))

(defn split-by-delimiter [string delimiter]
  ;; (str/split string )
  )


(comment
  (line-based-dispatch-fn (first (split-by-line spaces)))



  )