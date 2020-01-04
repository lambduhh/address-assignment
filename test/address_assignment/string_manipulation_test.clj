(ns address-assignment.string-manipulation-test
  (:require [clojure.test :refer :all]
            [address-assignment.string-manipulation :as sm :reload true]))


(deftest dispatch-fn
  (let [comma-separated-line "Canto,Susann,Female,Teal,5/8/2019"
        pipe-separated-line "Lambillion|Mavis|Female|Blue|6/11/2019\n"
        space-separated-line "Prue\tScala\tFemale\tPurple\t7/5/2019"]
    ;; matches by delimiter
    (is (= (sm/comma-separated? comma-separated-line)))
    (is (= (sm/pipe-separated? pipe-separated-line)))
    (is (= (sm/tab-separated? space-separated-line)))
    ;dispatch fn logic
    (is (= :comma (sm/line-based-dispatch-fn comma-separated-line)))
    (is (= :pipe (sm/line-based-dispatch-fn pipe-separated-line)))
    (is (= :space (sm/line-based-dispatch-fn space-separated-line)))
    ;; multimethod testing
    (is (= {:type     :comma,
            :last     "Canto",
            :first    "Susann",
            :gender   "Female",
            :favcolor "Teal",
            :dob      "5/8/2019"}
           (sm/process-line comma-separated-line)))
    (is (= {:type     :pipe,
            :last     "Lambillion",
            :first    "Mavis",
            :gender   "Female",
            :favcolor "Blue",
            :dob      "6/11/2019\n"}
           (sm/process-line pipe-separated-line)))
    (is (= {:type     :space,
            :last     "Prue",
            :first    "Scala",
            :gender   "Female",
            :favcolor "Purple",
            :dob      "7/5/2019"}
           (sm/process-line space-separated-line)))
    (is (= true
           (try
             (sm/process-line "string???")
             (catch Exception e
               true))))))


(deftest all-together-now
  (let [comma '({:type :comma, :last "Gianninotti", :first "Flint", :gender "Male", :favcolor "Crimson", :dob "1/12/2019"}
                {:type :comma, :last "Fost", :first "Ariela", :gender "Female", :favcolor "Red", :dob "12/14/2018"})
        pipe '({:type :pipe, :last "Dod", :first "Gwenore", :gender "Female", :favcolor "Turquoise", :dob "1/1/2019"}
               {:type :pipe, :last "Prior", :first "Stephine", :gender "Female", :favcolor "Khaki", :dob "2/27/2019"})
        space '({:type :space, :last "Wilbur", :first "Kabisch", :gender "Male", :favcolor "Purple", :dob "5/17/2019"}
                {:type :space, :last "Evered", :first "Lethlay", :gender "Male", :favcolor "Violet", :dob "3/26/2019"})]
    (is
      (= (apply concat [comma pipe space])
         '({:type :comma, :last "Gianninotti", :first "Flint", :gender "Male", :favcolor "Crimson", :dob "1/12/2019"}
           {:type :comma, :last "Fost", :first "Ariela", :gender "Female", :favcolor "Red", :dob "12/14/2018"}
           {:type :pipe, :last "Dod", :first "Gwenore", :gender "Female", :favcolor "Turquoise", :dob "1/1/2019"}
           {:type :pipe, :last "Prior", :first "Stephine", :gender "Female", :favcolor "Khaki", :dob "2/27/2019"}
           {:type :space, :last "Wilbur", :first "Kabisch", :gender "Male", :favcolor "Purple", :dob "5/17/2019"}
           {:type :space, :last "Evered", :first "Lethlay", :gender "Male", :favcolor "Violet", :dob "3/26/2019"})))))
