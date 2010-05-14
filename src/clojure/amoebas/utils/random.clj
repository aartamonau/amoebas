(ns amoebas.utils.random)

(defn random
  ([] (. Math random))
  ([a b]
     (let [rnd (. Math random)]
       (+ (* rnd (- b a)) a))))

(defn random-int [a b]
  (let [gen (java.util.Random.)]
    (+ a (. gen nextInt (+ 1 (- b a))))))

(defn random-out-of [& args]
  (let [number (count args)
        dice   (random-int 0 (dec number))]
    (nth args dice)))
