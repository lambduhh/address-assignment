(ns address-assignment.server
  (:require [clojure.test :refer :all]
            [address-assignment.server :as s]))


(deftest handlers
  (is (= (greet {}) {:status  200
                     :body    "Hello, Welcome. Please specify an endpoint"
                     :headers {}}))
  (is (= (last (json/parse-string (s/gender {})))
         {"type"     "space",
          "last"     "Maxim",
          "first"    "Ansteys",
          "gender"   "Male",
          "favcolor" "Crimson",
          "dob"      "6/30/2019"}))
  (is (= (last (json/parse-string (s/birthdate {})))
         {"type"     "space",
          "last"     "Tildie",
          "first"    "Simion",
          "gender"   "Female",
          "favcolor" "Blue",
          "dob"      "12/11/2019"}))
  (is (= (last (json/parse-string (s/name {})))
         {"type"     "comma",
          "last"     "cornhill",
          "first"    "Konstantin",
          "gender"   "Male",
          "favcolor" "Red",
          "dob"      "10/28/2019"}))
  (is (= (s/post-records {:json-params {"data" "Canto,Susann,Female,Teal,5/8/2019"}})
         {:status  200,
          :headers {},
          :body    {:type     :comma,
                    :last     "Canto",
                    :first    "Susann",
                    :gender   "Female",
                    :favcolor "Teal",
                    :dob      "5/8/2019"}})))
