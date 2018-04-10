(ns ad-astra.core
  (:require [ring.middleware.json :refer [wrap-json-params
                                          wrap-json-response]])
  (:require [ring.util.response :refer [response]]))


(defn handle-get [request]
  (when (= "/" (:uri request))
    (response "This is a home page!")))

(defn handle-post [request]
  (response "POST"))

(defn handle-put [request]
  (response "PUT"))

(defn handle-del [request]
  (response "DEL"))

(def method-map {:get handle-get
                 :post handle-post
                 :put handle-put
                 :del handle-del})

(defn handler [request]
  (let [request-method (:request-method request)]
    ((get method-map request-method) request)))
  

(def app
  (-> handler
      wrap-json-params
      wrap-json-response))
