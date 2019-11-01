(ns test-ns
  (:require [clojure.test :refer :all]
            [datomic.api :as d]
            [datomic.ion.testStarter :refer :all]))


;(deftest addition
;  (is (= 4 (+ 2 2)))
;  (is (= 8 (+ 3 4))))

(def ^:dynamic *conn* nil)

(defn fresh-database []
  (let [db-name (gensym)
        db-uri (str "datomic:mem://" db-name)]
    (d/create-database db-uri)
    (let [conn (d/connect db-uri)]
      @(d/transact conn todo-schema)
      conn)))
(defn with-database [f]
  (binding [*conn* (fresh-database)]
    (f)))

(use-fixtures :each with-database)

(defn todo-count
  [db]
  (count (d/q '[:find ?t :where [_ :todo/title ?t]] db)))

(deftest this-test-gets-a-database
  (is (not (nil? *conn*)))
  (is (not (nil? (d/db *conn*))))
  (is (= 0 (todo-count (d/db *conn*)))))

(deftest create-todo
  @(d/transact *conn* (todo-new "This is a test todo"))
  (is (= 1 (todo-count (d/db *conn*)))))
(run-tests)