(ns amoebas.utils.force-rec)

(defmulti force-rec class)

(defmethod force-rec nil
  [x] x)