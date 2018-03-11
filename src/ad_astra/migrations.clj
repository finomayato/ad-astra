(ns ad-astra.migrations
  (:require [clojure.java.jdbc :as jdbc]))

(def migration-map
  {:1 `(jdbc/create-table-ddl :test
        [:test_col1 "varchar(255)"]
        [:test_col2 :int]
        [:test_col3 :real])
   :2 `(jdbc/create-table-ddl :test_2
        [:test_col_2_1 :int]
        [:test_col_2_2 "varchar(255)"]
        [:test_col_2_3 :int])})
