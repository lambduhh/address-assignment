(ns address-assignment.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]
            [address-assignment.outputs :as o]
            [cheshire.core :as json]
            [clojure.pprint]
            [address-assignment.core :as c]))


(defn post-record [req]
  (let []))

(defn gender [req]
  (json/generate-string (o/sort-by-gender (c/process-files ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))

(defn name [req]
  (json/generate-string (o/sort-by-last-ascending (c/process-files ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))

(defn birthdate [req]
  (json/generate-string (o/sort-by-dob (c/process-files ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))

(defn greet [req]
  {:status  200
   :body    "Hello, Welcome"
   :headers {}})



(defroutes app
           (GET "/" [] greet)
           (POST "/records" post-record)
           (GET "/records/gender" [] gender)
           (GET "/records/birthdate" [] birthdate)
           (GET "/records/name" [] name)
           (GET "/echo" req (format "<pre> %s </pre>" (with-out-str (clojure.pprint/pprint req))))
           (GET "/requests" [] handle-dump)
           (not-found "Page not found."))


#_(defn -main [port]
    (jetty/run-jetty greet
                     {:port (Integer. port)}))

(defn -main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
