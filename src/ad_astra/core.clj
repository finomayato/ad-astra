(ns ad-astra.core
  (:require [ring.middleware.json :refer [wrap-json-params
                                          wrap-json-response]])
  (:require [ring.util.response :refer [response]]))

(defn handler [request]
  (prn request)
  (when (= :get (:request-method request))
    (response {"name" "Vadim"})))

(def app
  (-> handler
      wrap-json-params
      wrap-json-response))
