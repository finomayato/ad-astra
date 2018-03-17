(ns ad-astra.migrations
  (:require [clojure.java.jdbc :as jdbc]))


(def users-table-ddl
  (jdbc/create-table-ddl :users
                         [:id :serial "PRIMARY KEY"]
                         [:name "varchar(255)" "NOT NULL"]
                         [:email "varchar(255)" "NOT NULL"]))

(def goals-table-ddl
  (jdbc/create-table-ddl :goals
                         [:id :serial "PRIMARY KEY"]
                         [:name "varchar(255)" "NOT NULL"]
                         [:start_date "DATE"]
                         [:end_date "DATE"]
                         [:parent_goal_id :int "references goals (id)"]
                         [:user_id :int "references users (id)"]
                         [:description :text]
                         [:is_private :bool "default true"]))

(def migration-map
  {:1 users-table-ddl
   :2 goals-table-ddl})
