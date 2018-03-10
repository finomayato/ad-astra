(ns ad-astra.db
  (require '[clojure.java.jdbc :as j]))

(def db-conn {:classname "org.h2.Driver"
              :subprotocol "h2:mem"  ; keeping all data in memory for now
              :subname "ad-astra-demo;DB_CLOSE_DELAY=-1"
              :user "sa"
              :password ""})
