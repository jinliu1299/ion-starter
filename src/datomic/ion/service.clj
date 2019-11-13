; Copyright 2013 Relevance, Inc.
; Copyright 2014-2019 Cognitect, Inc.

; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0)
; which can be found in the file epl-v10.html at the root of this distribution.
;
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
;
; You must not remove this notice, or any other, from this software.

(ns datomic.ion.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [datomic.ion.lambda.api-gateway :as apigw]))

(defn path [path] (str "/rcd-ppr" path))

(defn hello-world
  [request]
  (let [name (get-in request [:params :name] "World")]
    {:status 200 :body (str "Hello " name "!\n")}))

;(defroutes routes
;  [[["/rcd-ppr/"
;      ["/rcd-ppr/hello"{:get hello-world}]]]])

(def routes #{[(path "/") :get hello-world]
              })

;(def service {:env                 :prod
;              ::http/routes        routes
;              ::http/resource-path "/public"
;              ::http/type          :jetty
;              ::http/port          8080})

(def routes-ionized
  "Ionization of routes for use with AWS API Gateway lambda proxy integration."
  (apigw/ionize routes))


;(def service {::http/routes          routes
;              ::http/chain-provider  ions/ion-provider
;              ::http/allowed-origins {:creds           true
;                                      :allowed-origins (fn [x]
;                                                         (or (str/includes? x "grantsolutions.gov") (str/includes? x "localhost")))}})
;
;(defn handler
;  [services]
;  (-> services
;      http/default-interceptors
;      http/create-provider))
;
;(def app (apigw/ionize (handler service)))


