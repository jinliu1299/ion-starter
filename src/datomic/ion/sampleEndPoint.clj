(ns datomic.ion.sampleEndPoint
  (:require
    [clojure.data.json :as json]
    [clojure.edn :as edn]
    [clojure.java.io :as io]
    [clojure.pprint :as pp]
    [datomic.client.api :as d]
    [datomic.ion.lambda.api-gateway :as apigw]))

(defn read-edn
  [input-stream]
  (some-> input-stream io/reader (java.io.PushbackReader.) edn/read))

(defn get-by-type
  "HTTP handler that returns self-describing info about items matching type."
  [{:keys [headers body]}]
  (let [type (some-> body read-edn)]
    (if 1
      {:status 200
       :headers {"Content-Type" "application/edn"}
       :body "200 -- Jin says"}
      {:status 400
       :headers {}
       :body "Expected a request body keyword naming a type"})))


(def get-by-type-ionized
  "Ionization of items-by-type for use with AWS API Gateway lambda proxy integration."
  (apigw/ionize get-by-type))