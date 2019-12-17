(ns address-assignment.string_manipulation_test
  (:require [clojure.test :refer :all]
            [address-assignment.string-manipulation :as sm :reload true]))

(deftest test1
  (let [comma-separated-line "Canto,Susann,Female,Teal,5/8/2019"
        pipe-separated-line "Lambillion|Mavis|Female|Blue|6/11/2019\n"
        space-separated-line "Prue\tScala\tFemale\tPurple\t7/5/2019"]
    (is (= :comma (sm/line-based-dispatch-fn comma-separated-line)))
    (is (= :pipe (sm/line-based-dispatch-fn pipe-separated-line)))
    (is (= :space (sm/line-based-dispatch-fn space-separated-line)))
    ;; multimethod testing
    (is (= {:type :comma, :last "Canto", :first "Susann", :gender "Female", :favcolor "Teal", :dob "5/8/2019"}
           (sm/process-line comma-separated-line)))
    (is (= {:type :pipe, :last "Lambillion", :first "Mavis", :gender "Female", :favcolor "Blue", :dob "6/11/2019\n"}
           (sm/process-line pipe-separated-line)))
    (is (= {:type :space, :last "Prue", :first "Scala", :gender "Female", :favcolor "Purple", :dob "7/5/2019"}
           (sm/process-line space-separated-line)))
    (is (= true
           (try
             (sm/process-line "string???")
             (catch Exception e
               true))))
    )

  )



