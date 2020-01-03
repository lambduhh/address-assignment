(ns address-assignment.string-manipulation
  (:require [clojure.string :as str]))



(defn split-by-line [txt]
  ;; TODO: inline this function
  ;; meaning clojure.string/split-lines does the exact same
  ;; thing as split-by-line -- so there's no need for split-by-lone
  (clojure.string/split-lines txt))




;; TODO:
;; comma-seperated?, pipe-seperated?, and tab-seperated?
;; are exactly the same except for one symbols
;;
;; make a generic predicate called seperated-by? that takes
;; two args, sep and txt and use it to redefine
;; those three predicates


(defn ^:private seperated-by? [sep txt]
  (let [chars (seq txt)]
    (= 4 (count (filter #{sep} chars)))))

;; build predicates for dispatch function
(defn comma-separated? [txt]
  (seperated-by? \, txt))


(defn pipe-separated? [txt]
  ;; TODO -- redefine this using seperated-by? like we did for comma-seperated?
  (let [chars (seq txt)]
    (= 4 (count (filter #{\|} chars)))))


(defn tab-separated? [txt]
  ;; TODO -- redefine this using seperated-by?
  (let [chars (seq txt)]
    ;; str literal "\t" vs. char literal "\tab"
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


;; TODO -- these next three functions also have code duplication
;; refactor the inner part into an inner function and redefine
;; the methods below in terms of that

;; TODO -- finish tis (defn processed-line ... )

(defmethod process-line :pipe [line]
  ;; TODO -- redefine this in terms of processed-line
  ;; like seperated-by? above
  
  ;; need to escape pipe in regex w brackets bc pipe MEANS SOMETHING in regex
  (let [[last-name first-name gender fcolor dob :as line] (str/split line #"[|]")]
    ;; WYSIWYG
    {:type     :pipe
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :comma [line]
  ;; TODO -- redefine this in terms of processed-line
  ;; like seperated-by? above
  (let [[last-name first-name gender fcolor dob] (str/split line #"[,]")]
    {:type     :comma
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :space [line]
  ;; TODO -- redefine this in terms of processed-line
  ;; like seperated-by? above

  (let [[last-name first-name gender fcolor dob] (str/split line #"\t")]
    {:type     :space
     :last     last-name
     :first    first-name
     :gender   gender
     :favcolor fcolor
     :dob      dob}))


(defmethod process-line :error [line]
  ;; TODO -- leave this as is since it's different
  ;; than the other three
  (throw (Exception. (str "Error in data :( check " line))))


(defn list-of-maps ;; TODO -- inline this function, it's not necessary
  "Transform data into a list of hash-maps"
  [txt]
  (let [collection-of-lines (split-by-line txt)]
    (map process-line collection-of-lines)))


(defn combine-all [args] ;; inline this function
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

