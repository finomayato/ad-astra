(ns ad-astra.core
  (:require [ring.middleware.params :refer [wrap-params]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(def app
  (-> handler wrap-params))
