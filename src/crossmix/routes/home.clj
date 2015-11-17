(ns crossmix.routes.home
  (:require [crossmix.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html"))




(defroutes home-routes
  (GET "/" [name] (home-page)))

