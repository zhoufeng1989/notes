(ns fwpd.core)

(def filename "suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))
(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(def validations {:name contains? :glitter-index contains?})

(defn validate
  [validations suspect]
  (reduce (fn [result [key func]]
            (and result (func suspect key)))
          true
          validations
          )
  )

(defn append
  [new-suspect suspect-list]
  (if (validate validations new-suspect)
    (conj suspect-list (vec (vals new-suspect))))
  )

(defn mapify
  [rows]
  (map (fn [row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                   {} 
                 (map vector vamp-keys row)))
    rows))

(defn glitter-filter
  [minimum-glitter records]
  (map #(:name %1) (filter #(> (:glitter-index %) minimum-glitter) records)))

(defn map-csv
  [rows filename]
  (def string 
    (clojure.string/join 
      "\n"
      (map (fn [row]
           (clojure.string/join "," (vals row)))
           rows)))
  (spit filename string)
  )
(defn -main
  [& args]
  (println (glitter-filter 2 (mapify (parse (slurp filename)))))
  (println (validate validations {:name "test" :glitter-index 10}))
  (println (validate validations {:name1 "test" :glitter-index 10}))
  (println (validate validations {:name1 "test" :glitter-index2 10}))
  (println (append {:name "test" :glitter-index 10} [["a" 2] ["b" 3] ["c" 4]]))
  (println (map-csv [{:a 1 :b 2} {:a 2 :b 3} {:a 3 :b 4}] "test.csv"))
  )
