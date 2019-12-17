(ns address-assignment.core
  (:require [address-assignment.string-manipulation :as sm]
            [address-assignment.outputs :as o]))


(defn s-mani [args]
  (let [maps (map sm/list-of-maps (map slurp args))
        all-together-now (sm/combine-all maps)]
    all-together-now))


(defn -main [& args]
  (doseq [row (s-mani args)]
    (println row)))

(comment
  (o/sort-by-dob (s-mani ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))

  )

