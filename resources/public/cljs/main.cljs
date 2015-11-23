(ns crossmix.core
	(:require 
		[crossmix.dom :as dom]
		))

(defn is-youtube-url
  [url]
  (some? (re-find (re-pattern "youtube\\.\\w+\\/watch\\?v\\=(\\w+)") url)))

(enable-console-print!)



(defn debug [message & type]
	(def ltype (if (some? type) type "log"))
	(if (= ltype "log") (-> js/console (.log message)))
	(if (= ltype "info") (.info js/console message))
	(if (= ltype "warn") (.warn js/console message))
	(if (= ltype "error") (.error js/console message)))



(defn get-value [form]
	(aget form "value"))

(defn crossmix-check
	[event]
	(.preventDefault event)
	(.stopPropagation event)
	(def video-source (dom/query-selector "[data-video-source]"))
	(def video-url (get-value video-source))

	(def audio-source (dom/query-selector "[data-audio-source]"))
	(def audio-url (get-value audio-source))

	(if (and (is-youtube-url audio-url) (is-youtube-url video-url)) ((fn []
		(js/alert video-url)
	)) ((fn []
		(js/alert "Keine Youtube URLs :(")
	)))
	
	false)

(.ready (js/$ "document") (fn []
	(debug "Crossmix.js loaded")
	(def inputs (dom/query-selector-all "input[type='text']"))
	(def form (dom/query-selector "form"))
	(.submit (js/$ form) crossmix-check)
	))


