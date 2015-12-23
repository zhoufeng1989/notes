(defn neighbors
  ([size pos] (neighbors [[0 1] [0 -1] [1 0] [-1 0]] size pos))
  ([deltas size pos]
   (filter (fn [newpos] 
             (every? #(< -1 % size) newpos))
           (map #(map + pos %) deltas))))
; test
(println (neighbors 4 [1 2]))
