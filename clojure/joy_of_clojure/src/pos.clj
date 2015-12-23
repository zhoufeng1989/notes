(defn index [coll]
  (cond
    (map? coll) (seq coll)
    (set? coll) (map vector coll coll)
    :else (map vector (iterate inc 0) coll)))
;test
(println (index [:a 1 :b 2 :c 3 :d 4]))
(println (index {:a 1, :b 2, :c 3}))
(println (index #{:a :b :c}))

(defn pos [e coll]
  (for [[i v] (index coll) :when (= e v)] i))
;test
(println (pos 3 [:a 1 :b 2 :c 3 :d 4]))
