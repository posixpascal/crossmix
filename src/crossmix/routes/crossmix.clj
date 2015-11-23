(ns crossmix.routes.crossmix
  (:require [crossmix.layout :as layout]
            [crossmix.helper :as helper]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :refer [ok]]
            [ring.util.response :as res]
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
  (if-not (or (helper/is-youtube-url audio) (helper/is-youtube-url video)) (res/redirect "/"))
  (if (and (helper/is-youtube-url video) (helper/is-youtube-url audio)) (do
    (def uuid 
      (make-uuid (str (System/currentTimeMillis))))

    (str uuid)
    (db/create-crossmix! {
      :video video
      :audio audio
      :uuid uuid
      })
    (res/redirect uuid))))
  
(defn view-mix-page
 	[uuid]
  (def crossmix (first (db/get-crossmix {
    :uuid uuid
  })))


	(render-file "mix/view.html", {
    :audio (get crossmix :audio) 
    :video (get crossmix :video)
  }))


(defn create-mix-page
  []

  (layout/render "mix.html"))


(defroutes crossmix-routes
  ;(GET "/mix" [] (mix-))
  (GET "/mix", [uuid] (create-mix-page))
  (GET "/mix/:uuid" [uuid] (view-mix-page uuid))
  (POST "/mix/create" [youtube_video youtube_audio] (mix-page youtube_audio youtube_video)))
