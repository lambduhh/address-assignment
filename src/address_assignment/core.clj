(ns address-assignment.core
  (:require [address-assignment.string-manipulation :as sm]))


(defn s-mani [args]
  (let [maps (map sm/list-of-maps (map slurp args))
        all-together-now (sm/combine-all maps)]
    all-together-now))

(defn -main [& args]
  (doseq [row (s-mani args)]
    (println row)))

(comment


  )

