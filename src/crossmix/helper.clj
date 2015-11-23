(ns crossmix.helper)

(defn is-youtube-url
  [url]
  (some? (re-find (re-pattern "youtube\\.\\w+\\/watch\\?v\\=(\\w+)") url)))