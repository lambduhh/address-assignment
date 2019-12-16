(ns address-assignment.outputs
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def testdata [{:type :pipe, :last "Chantrell", :first "Daryn", :gender "Female", :favcolor "Aquamarine", :dob "11/9/2019"}
               {:type :pipe, :last "Pabst", :first "Yasmin", :gender "Female", :favcolor "Aquamarine", :dob "2/8/2019"}
               {:type :pipe, :last "McCowen", :first "Rori", :gender "Female", :favcolor "Fuscia", :dob "3/16/2019"}
               {:type :pipe, :last "Caherny", :first "Valaree", :gender "Female", :favcolor "Pink", :dob "5/8/2019"}
               {:type :pipe, :last "Hedderly", :first "Sally", :gender "Female", :favcolor "Orange", :dob "2/24/2019"}
               {:type :pipe, :last "Houtby", :first "Jock", :gender "Male", :favcolor "Mauv", :dob "5/28/2019"}
               {:type :pipe, :last "D'Antonio", :first "Oralle", :gender "Female", :favcolor "Indigo", :dob "1/21/2019"}
               {:type :pipe, :last "Gilliat", :first "Ellary", :gender "Male", :favcolor "Yellow", :dob "10/22/2019"}
               {:type :pipe, :last "Rucklesse", :first "Magdalena", :gender "Female", :favcolor "Puce", :dob "5/8/2019"}
               {:type :pipe, :last "Clotworthy", :first "Patrick", :gender "Male", :favcolor "Aquamarine", :dob "4/6/2019"}
               {:type :pipe, :last "Abramchik", :first "Gibb", :gender "Male", :favcolor "Pink", :dob "2/16/2019"}
               {:type :pipe, :last "Appleford", :first "Standford", :gender "Male", :favcolor "Fuscia", :dob "1/26/2019"}
               {:type :pipe, :last "Streak", :first "Elnore", :gender "Female", :favcolor "Mauv", :dob "2/23/2019"}
               {:type :pipe, :last "Tullis", :first "Annemarie", :gender "Female", :favcolor "Orange", :dob "7/25/2019"}])

(defn sort-by-gender [data]
  (sort-by :gender data))

(defn sort-by-last-ascending [data]
  (sort-by :last data))

(defn sort-by-last-descending [data]
  (reverse (sort-by-last-ascending data)))

(defn sort-by-genderlast [data]
  (sort-by-gender (sort-by-last-ascending data)))

(def line (first testdata))
(def date (get-in testdata [0 :dob]))
date

(f/formatter date)

(def custom-formatter (f/formatter "MM/dd/yyyy"))

(defn formatdate [date]
  (f/parse custom-formatter date))


(defn add-new-fdate-obj [data]
  (let [newdate (formatdate (get data :dob))]
    (assoc data :newdate newdate)))

(defn add-new-fdate-all [data]
  (map add-new-fdate-obj data))

(defn convert-time [row]
  (let [dob (get row :dob)]
    (formatdate dob)))

(defn sort-by-dob [data]
  (sort-by convert-time data))


(comment
  (def newtestdata (add-new-fdate-all testdata))
  (formatdate date)
  newtestdata
  (sort-by-dob newtestdata)
  now
  (f/unparse basic-date now)
  (get newtestdata :dob)

  )