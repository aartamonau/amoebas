(ns amoebas.utils.seq)

(defn zip [xs ys & rest]
  (let [zipper (fn [& args] (vec args))]
    (apply map (concat [zipper xs ys] rest))))

(defn enumerate [seq]
  (zip (iterate inc 0) seq))

(defn concat-map [f xs & rest]
  (apply concat
         (apply map
                (concat [f xs] rest))))
