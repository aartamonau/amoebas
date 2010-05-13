(ns amoebas.utils.misc)

(defn const [x]
  (fn [y] x))

(defn assoc-modify [coll & rest]
  (loop [c coll
         [acc f & rest :as args] rest]
    (if (empty? args)
      c
      (recur (assoc c
               acc (f (c acc)))
             rest))))
