(ns address-assignment.core
  (:require [libpython-clj.python :as py]
            ))
#_(py/initialize! :python-home "/usr/local/bin/python3.7"
                :library-path "")

;; (py/initialize!)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))



