(ns address-assignment.parse-and-sort-test
  (:require [clojure.test :refer :all]
            [address-assignment.string-manipulation :as sm]))

(deftest test1

  (let [comma-seperated-line "Canto,Susann,Female,Teal,5/8/2019"]
    (is (= :comma (sm/line-based-dispatch-fn comma-seperated-line)))

    )

  )

