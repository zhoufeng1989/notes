(defmacro macro-or
  ([] nil)
  ([arg] arg)
  ([arg & args] 
   `(let [or# ~arg]
      (if or# or# (macro-or ~@args)))))

(println (macro-or))
(println (macro-or 1))
(println (macro-or 1 2))
(println (macro-or nil 2 3))
