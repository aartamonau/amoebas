(ns amoebas.utils.random)

(defn random
  ([] (. Math random))
  ([a b]
     (let [rnd (. Math random)]
       (+ (* rnd (- b a)) a))))

(defn random-int [a b]
  (let [gen (java.util.Random.)]
    (+ a (. gen nextInt (+ 1 (- b a))))))

(defn random-out-of [a b]
  (let [rand (random-int 0 1)]
    (if (= rand 0)
      a
      b)))