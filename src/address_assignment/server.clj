(ns address-assignment.server
  (:refer-clojure :exclude [name])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]
            [address-assignment.outputs :as o]
            [cheshire.core :as json]
            [clojure.pprint]
            [address-assignment.core :as c]
            [address-assignment.string-manipulation :as sm]
            [ring.middleware.defaults :as rd]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]))


(defn greet [req]
  {:status  200
   :body    "Hello, Welcome. Please specify an endpoint"
   :headers {}})


(defn gender [req]
  (json/generate-string
    (o/sort-by-gender
      (c/process-files
        ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))


(defn name [req]
  (json/generate-string
    (o/sort-by-last-ascending
      (c/process-files
        ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))


(defn birthdate [req]
  (json/generate-string
    (o/sort-by-dob
      (c/process-files
        ["resources/comma.txt" "resources/pipe.txt" "resources/tabs.txt"]))))


(defn post-records [req]
  (let [text (get-in req [:json-params "data"])]
    ;; text will be something like "Canto,Susann,Female,Teal,5/8/2019"
    (response
      (sm/process-line text))))


(defroutes app
           (GET "/" [] greet)
           (POST "/records" req post-records)
           (GET "/records/gender" [] gender)
           (GET "/records/birthdate" [] birthdate)
           (GET "/records/name" [] name)
           (GET "/echo" req (format "<pre> %s </pre>" (with-out-str (clojure.pprint/pprint req))))
           (GET "/requests" [] handle-dump)
           (not-found "Page not found."))


(defn -main [port]
  (jetty/run-jetty (rd/wrap-defaults
                     (wrap-json-params
                       (wrap-json-response
                         (wrap-reload #'app)
                         {:keywords? true}))
                     rd/api-defaults)
                   {:port (Integer. port)}))
