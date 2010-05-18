(ns amoebas.utils.serialization)

(defn serialize [data-structure]
  (binding [*print-dup* true]
    (print-str data-structure)))

(defn deserialize [data]
  (with-in-str data
    (read)))
