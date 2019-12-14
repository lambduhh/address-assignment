(ns address-assignment.parse-and-sort-test
  (:require [clojure.test :refer :all]
            [address-assignment.parse-and-sort :as ps]))

(deftest test1

  (let [comma-seperated-line "Canto,Susann,Female,Teal,5/8/2019"]
    (is (= :comma (ps/line-based-dispatch-fn comma-seperated-line)))

    )

  )

