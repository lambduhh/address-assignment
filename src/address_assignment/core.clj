(ns address-assignment.core
  (:require [address-assignment.string-manipulation :as sm]
            [address-assignment.outputs :as o]))


(defn process-files [args]
  ;; refactor this using a single threadlast macro
  (let [maps (map sm/list-of-maps (map slurp args))
        all-together-now (sm/combine-all maps)]
    all-together-now))

(defn sorted-app
  [[sort-type & fnames]]
  (let [data (process-files fnames)]
    (cond
      (= sort-type "genderlast") (o/sort-by-genderlast data)
      (= sort-type "dob") (o/sort-by-dob data)
      (= sort-type "last") (o/sort-by-last-descending data))))

(defn app [args]
  (let [results (if (= (first args) "-s")
                  (sorted-app (vec (rest args)))
                  (process-files args))]
    (doseq [result results]
      (println result))))


(defn -main [& args]
  (app args))
