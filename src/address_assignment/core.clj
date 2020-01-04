(ns address-assignment.core
  (:require [address-assignment.string-manipulation :as sm]
            [address-assignment.outputs :as o]))


(defn process-files [args]
  (->> args
       (map slurp)
       (map sm/process-line)
       (apply concat)))


(comment
  (def args (range 10))
  (map inc xs)
  (->> xs
       (map inc)
       (filter even?)
       (map str))
  (let [a (map inc xs)
        b (filter even? a)
        c (map str b)]
    c)
  )



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
