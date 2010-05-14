(ns amoebas.utils.seq)

(defn zip [xs ys & rest]
  (apply map
         vector
         xs ys rest))

(defn enumerate [seq]
  (zip (iterate inc 0) seq))

(defn concat-map [f xs & rest]
  (apply concat
         (apply map
                f
                xs rest)))


;; (defn partition-all
;;   ([n coll]
;;      (partition-all n n coll))
;;   ([n step coll]
;;      (lazy-seq
;;       (when-let [s (seq coll)]
;;         (cons (take n s) (partition-all n step (drop step s)))))))