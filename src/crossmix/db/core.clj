
(ns crossmix.db.core
  (:require
    [clojure.java.jdbc :as jdbc]
    [conman.core :as con]
    [environ.core :refer [env]])
  (:import [java.sql
            BatchUpdateException
            PreparedStatement]))



            
(defonce ^:dynamic *conn* (atom nil))

(con/bind-connection *conn* "sql/create-crossmix.sql")
(con/bind-connection *conn* "sql/get-last-id.sql")
(con/bind-connection *conn* "sql/get-crossmix.sql")

(def pool-spec
  {:adapter    :mysql
   :init-size  1
   :min-idle   1
   :max-idle   4
   :max-active 32})

(defn connect! []
  (con/connect!
    *conn*
   (assoc
     pool-spec
     :jdbc-url (env :database-url))))

(defn disconnect! []
  (con/disconnect! *conn*))

(defn to-date [sql-date]
  (-> sql-date (.getTime) (java.util.Date.)))

(extend-protocol jdbc/IResultSetReadColumn
  java.sql.Date
  (result-set-read-column [v _ _] (to-date v))

  java.sql.Timestamp
  (result-set-read-column [v _ _] (to-date v)))

(extend-type java.util.Date
  jdbc/ISQLParameter
  (set-parameter [v ^PreparedStatement stmt idx]
    (.setTimestamp stmt idx (java.sql.Timestamp. (.getTime v)))))

