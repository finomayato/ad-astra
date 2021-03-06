(ns ad-astra.db
  (:require [clojure.java.jdbc :as jdbc])
  (:require [ad-astra.migrations :refer [migration-map]]))

(def db-conn {:classname "org.h2.Driver"
              :subprotocol "h2:mem"  ; keeping all data in memory for now
              :subname "test;DB_CLOSE_DELAY=-1"
              :user "sa"
              :password ""})

(defn run-migration [command]
  (println "Running command: " command)
  (jdbc/db-do-commands db-conn command))

; This function initializes table for storing migration version
(defn init-migrations []
  (run-migration (jdbc/create-table-ddl :migrations [:version :int]))
  (jdbc/insert! db-conn :migrations {:version 0}))

(defn get-last-version []
  (->>
    (jdbc/query db-conn ["SELECT version FROM migrations LIMIT 1;"])
    (first)
    (:version)))

(defn upgrade
  ([] (let [last-applied-version (get-last-version)]
        (upgrade last-applied-version)))
  ([last-applied-version]
    (let [next-version (inc last-applied-version)
          commands ((keyword (str next-version)) migration-map)]
      (when-not (nil? commands)
        (do
          (run-migration (eval commands))
          (jdbc/update! db-conn :migrations
            {:version next-version}
            ["version = ?" last-applied-version])
          (println "Applied version:" next-version)
          (upgrade next-version))))))
