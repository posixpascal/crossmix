(ns crossmix.dom
	(:require 
		[goog.dom :as dom]
		[goog.events :as event]
		))


; DOM Wrappers for goog.dom API.
(defn element-by-id
	[id]
	(dom/getElement id))

(defn query-selector
	[query]
	(.querySelector js/document query))

(defn query-selector-all
	[query]
	(.querySelectorAll js/document query))

(defn element-by-class
	[class]
	(dom/getElementByClass class))

(defn attach-event
	[event element action]
	(event/listen event element action)
)

(def event-click	event/EventType.CLICK)
(def event-submit	event/EventType.SUBMIT)
