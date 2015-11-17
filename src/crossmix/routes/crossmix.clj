(ns crossmix.routes.crossmix
  (:require [crossmix.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :refer [ok]]
            [selmer.parser :refer [render-file]]
            [conman.core :as con]
            [crossmix.db.core :as db]
            [clojure.java.io :as io]))

(require 'digest)



(defonce ^:dynamic *conn* (atom nil))

(defn make-uuid
	[id]
	(subs (digest/sha-256 id) 0 16)
)

(defn mix-page [audio video]
  (db/create-crossmix! 
  {	:id 1
  	:video video
  	:audio audio 
  	:uuid (make-uuid (db/get-last-id)) })
  (render-file
    "mix.html" {:audio audio :video video}))

(defroutes crossmix-routes
  (GET "/mix" [] (mix-page)))


(defroutes crossmix-routes 
	(POST "/mix/create" [youtube_video youtube_audio] (mix-page youtube_audio youtube_video)))
