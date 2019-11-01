(ns datomic.ion.testStarter
  (:require [datomic.api :as d]))


(def todo-schema
  [{:db/id #db/id[db.part/db]
    :db/ident :todo/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "This is the title of the todo item"
    :db.install/_attribute  :db.part/db}
   {:db/id  #db/id[db.part/db]
    :db/ident  :todo/completed?
    :db/valueType :db.type/boolean
    :db/cardinality :db.cardinality/one
    :db/doc "If true, this item is done"
    :db.install/_attribute  :db.part/db
    }
   ])

(defn todo-new
  [title]
  [{:db/id  (d/tempid :db.part/user)
    :todo/title title
    :todo/completed?  false}])
