(ns address-assignment.core
  (:require [address-assignment.string-manipulation :as sm]
            [address-assignment.outputs :as o]))


(defn process-files [file-names]
  (->> file-names
       (map slurp)
       (mapcat sm/process-line)))

(def which-sort
  {"genderlast" o/sort-by-genderlast
   "dob" o/sort-by-dob
   "last" o/sort-by-last-descending})

(defn process-args
  [[farg sarg & rargs :as args]]
  (if (= farg "-s")
    {:sort-fn (which-sort sarg)
     :file-names rargs}
    {:sort-fn identity
     :file-names rargs}))

(defn show-results
  [results]
  (doseq [result results] (println result)))

(defn -main [& args]
  (let [{:keys [file-names sort-fn] :as argument-map} (process-args args)

        ;; argument-map (process-args args)
        ;; file-names (:file-names argument-map)
        ;; sort-fn (:sort-fn argument-map)

        data (process-files file-names)
        results (sort-fn data)]
    (show-results results)))

