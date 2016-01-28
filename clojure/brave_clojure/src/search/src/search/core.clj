(ns search.core
  (:require [clj-http.client :as client])
  (:gen-class))

(def engine-mapping 
  {"google" (fn [keyword] 
              (str "https://google.com" "/#newwindow=1&q=" keyword))
   "bing" (fn [keyword]
            (str "https://bing.com" "/search?q=" keyword))})

(defn search
  [engine keyword]
  (:body (client/get ((get engine-mapping engine) keyword)))
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (search "bing" "test"))
  (println "Hello, World!"))
