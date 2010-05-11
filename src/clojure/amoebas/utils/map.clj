(ns amoebas.utils.map)

(defn update-val [m key f]
  (assoc m
    key (f (m key))))
