(let [[a b c & d :as all] (vec (range 10))]
  (println "a b c are: " a b c)
  (println "d are: " d)
  (println "all are: " all)
  )

(let [{a :a,  b :b} {:a "A" :b "B"}]
  (println "a b are" a b)
  )
