(ns ad-astra.db-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [ad-astra.db :refer :all]))


(defn check-if-all-tables-exist [desired-table-names-set]
  (let [table-names
          (->>
            (jdbc/with-db-metadata [md db-conn]
              (jdbc/metadata-result
                (.getTables md nil nil nil (into-array ["TABLE"]))))
            (map :table_name)
            (set))]
      (is (= table-names desired-table-names-set))))

(defn init-migrations-fixture [f]
  (jdbc/with-db-connection [db-conn db-conn]
    (do (init-migrations)
        (f))))


(use-fixtures :once init-migrations-fixture)


(def real-table-names-set
  (set (map clojure.string/upper-case ["goals"
                                       "users"
                                       "migrations"])))

(deftest applying-migrations
  (testing "Applying real migrations"
    (upgrade)
    (println "hello1")
    (check-if-all-tables-exist real-table-names-set)))
