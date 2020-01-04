(ns address-assignment.string-manipulation
  (:require [clojure.string :as str]))

(defn ^:private separated-by? [sep txt]
  (>= 4 (count (filter #{sep} txt))))


(defn separator-type [line]
  (condp separated-by? line
    \| #"\|"
    \, #","
    \tab #"\t"
    (throw (ex-info "Separator not found" {}))))


(defn split-line-by [sep line]
  (let [[last-name first-name gender fcolor dob & extra] (str/split line sep)
        result {:last     last-name
                :first    first-name
                :gender   gender
                :favcolor fcolor
                :dob      dob}]
    (if (seq extra)
      (assoc result :extra extra)
      result)))


(defn process-file
  [file-name]
  (try
    (let [text (slurp file-name)
          lines (str/split-lines text)
          separator (separator-type (first lines))]
      (map (partial split-line-by separator) lines))
    (catch Exception _
      (println "Error: Unable to determine file type for: " file-name))))


(comment
  (def spaces (slurp "resources/tabs.txt"))
  (separated-by? \tab txt)
                                        ;test predicate functions on line
  (def txt (first (clojure.string/split-lines spaces)))

  (def comma (slurp "resources/comma.txt"))
  (def pipe (slurp "resources/pipe.txt"))
  (def spaces (slurp "resources/tabs.txt"))

  )
