(ns ad-astra.db-test
  (:require [clojure.test :refer :all]
            [ad-astra.db :refer :all]
            [clojure.java.jdbc :as jdbc]))


(defn init-migrations-fixture [f]
        (init-migrations)
        (f))


(use-fixtures :once init-migrations-fixture)

(deftest applying-migrations
  (testing "Applying migrations"
    (testing "valid map"
      (with-redefs
        [ad-astra.migrations/migration-map
          {:1 `(jdbc/create-table-ddl :test
              [:test_col1 "varchar(255)"]
              [:test_col2 :int]
              [:test_col3 :real])
           :2 `(jdbc/create-table-ddl :test_2
              [:test_col_2_1 :int]
              [:test_col_2_2 "varchar(255)"]
              [:test_col_2_3 :int])}]
        (upgrade))
      (is (= (get-last-version) 2)))
    (testing "empty map"
      (with-redefs
        [ad-astra.migrations/migration-map {}]
        (upgrade))
      (is (= (get-last-version) 2)))))
