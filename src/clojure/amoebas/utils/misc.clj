(ns amoebas.utils.misc)

(defn const [x]
  (fn [y] x))